package cool.scx.http.media.event_stream;

import cool.scx.io.ByteInput;
import cool.scx.io.exception.NoMatchFoundException;
import cool.scx.io.exception.NoMoreDataException;

import java.nio.charset.Charset;

import static cool.scx.http.media.event_stream.EventStreamHelper.LF_BYTES;

/// ClientEventStream
///
/// @author scx567888
/// @version 0.0.1
public final class ClientEventStream implements AutoCloseable {

    private final ByteInput byteInput;
    private final Charset charset;

    public ClientEventStream(ByteInput byteInput, Charset charset) {
        this.byteInput = byteInput;
        this.charset = charset;
    }

    /// 读取事件
    public SseEvent readEvent() {
        var event = SseEvent.of();

        // 解析事件
        while (true) {

            // todo 这里应该细化处理
            byte[] bytes = null;
            try {
                bytes = byteInput.readUntil(LF_BYTES);
            } catch (NoMatchFoundException e) {
                throw new RuntimeException(e);
            } catch (NoMoreDataException e) {
                throw new RuntimeException(e);
            }

            var line = new String(bytes, charset);

            if (line.isEmpty()) {
                // 事件结束, 返回 SseEvent
                return event;
            }

            if (line.startsWith("event: ")) {
                event.event(line.substring(7).trim());
            } else if (line.startsWith("data: ")) {
                event.data(line.substring(6).trim());
            } else if (line.startsWith("id: ")) {
                event.id(line.substring(4).trim());
            } else if (line.startsWith("retry: ")) {
                try {
                    event.retry(Long.parseLong(line.substring(7).trim()));
                } catch (NumberFormatException e) {
                    // 如果格式错误, 可以忽略
                }
            } else if (line.startsWith(": ")) {
                event.comment(line.substring(2).trim());
            }
        }
    }

    @Override
    public void close() {
        this.byteInput.close();
    }

}
