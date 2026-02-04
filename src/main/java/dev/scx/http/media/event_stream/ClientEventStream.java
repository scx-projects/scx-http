package dev.scx.http.media.event_stream;

import dev.scx.io.ByteInput;
import dev.scx.io.exception.InputAlreadyClosedException;
import dev.scx.io.exception.NoMatchFoundException;
import dev.scx.io.exception.NoMoreDataException;
import dev.scx.io.exception.ScxInputException;

import java.nio.charset.Charset;

/// ClientEventStream
///
/// @author scx567888
/// @version 0.0.1
public final class ClientEventStream implements AutoCloseable {

    private static final byte[] LF_BYTES = "\n".getBytes();

    private final ByteInput byteInput;
    private final Charset charset;
    private final int maxLineLength;

    public ClientEventStream(ByteInput byteInput, Charset charset) {
        // 默认 64 KB
        this(byteInput, charset, 64 * 1024);
    }

    public ClientEventStream(ByteInput byteInput, Charset charset, int maxLineLength) {
        this.byteInput = byteInput;
        this.charset = charset;
        this.maxLineLength = maxLineLength;
    }

    /// 读取事件
    public SseEvent readEvent() throws EventStreamParseException {

        // 获取事件的各个部分
        String event = null;
        StringBuilder data = null;
        String id = null;
        Long retry = null;
        StringBuilder comment = null;

        // 解析事件
        while (true) {

            byte[] bytes;
            try {
                bytes = byteInput.readUntil(LF_BYTES, maxLineLength);
            } catch (NoMatchFoundException e) {
                throw new EventStreamParseException("SSE 行 过长!!! 最大长度 : " + maxLineLength);
            } catch (NoMoreDataException e) {
                throw new EventStreamParseException("SSE 行 提前终止 !!!");
            } catch (ScxInputException e) {
                throw new EventStreamParseException("SSE 行 读取异常 !!!", e);
            }

            var line = new String(bytes, charset);

            if (line.isEmpty()) {
                // 事件结束, 返回 SseEvent
                break;
            }

            if (line.startsWith(":")) {
                if (comment == null) {
                    comment = new StringBuilder(line.substring(1));
                } else {
                    comment.append('\n').append(line.substring(1));
                }
                continue;
            }

            if (line.startsWith("event: ")) {
                if (event == null) {
                    event = line.substring(7);
                } else {
                    // 出现重复的 event:
                    throw new EventStreamParseException("SSE 行 重复的 event");
                }
                continue;
            }

            if (line.startsWith("data: ")) {
                if (data == null) {
                    data = new StringBuilder(line.substring(6));
                } else {
                    data.append('\n').append(line.substring(6));
                }
                continue;
            }

            if (line.startsWith("id: ")) {
                if (id == null) {
                    id = line.substring(4);
                } else {
                    // 出现重复 id:
                    throw new EventStreamParseException("SSE 行 重复的 id");
                }
                continue;
            }

            if (line.startsWith("retry: ")) {
                if (retry == null) {
                    try {
                        retry = Long.parseLong(line.substring(7));
                    } catch (NumberFormatException e) {
                        throw new EventStreamParseException("SSE 行 retry 格式错误");
                    }
                } else {
                    // 出现重复 retry
                    throw new EventStreamParseException("SSE 行 重复的 retry");
                }
                continue;
            }

            throw new EventStreamParseException("SSE 非法行");

        }

        return SseEvent.of()
            .comment(comment == null ? null : comment.toString())
            .id(id)
            .retry(retry)
            .data(data == null ? null : data.toString())
            .event(event);

    }

    @Override
    public void close() throws ScxInputException, InputAlreadyClosedException {
        this.byteInput.close();
    }

}
