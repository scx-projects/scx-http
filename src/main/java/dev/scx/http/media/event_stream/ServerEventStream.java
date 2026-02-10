package dev.scx.http.media.event_stream;

import dev.scx.io.ByteOutput;
import dev.scx.io.exception.OutputAlreadyClosedException;
import dev.scx.io.exception.ScxOutputException;

import static java.nio.charset.StandardCharsets.UTF_8;

/// ServerEventStream
///
/// @author scx567888
/// @version 0.0.1
public final class ServerEventStream implements AutoCloseable {

    private final ByteOutput out;

    public ServerEventStream(ByteOutput out) {
        this.out = out;
    }

    public static String[] getData(String data) {
        // 1, data 是空串             "" ->  [ "" ]
        // 2, data 是单行             "abc" ->  [ "abc" ]
        // 3, data 是多行             "abc\n" [ "abc", "" ]
        // 4, data 是多行同时尾随 \r    "abc\r\n" [ "abc\r", "" ]
        return data.split("\n");
    }

    public static void send0(SseEvent sseEvent, ByteOutput out) throws ScxOutputException, OutputAlreadyClosedException {
        // 获取事件的各个部分
        var event = sseEvent.event();
        var data = sseEvent.data();
        var id = sseEvent.id();
        var retry = sseEvent.retry();
        var comment = sseEvent.comment();

        // 使用 StringBuilder 拼接事件内容
        var sb = new StringBuilder();

        // 可选评论 (如果有的话)
        if (comment != null) {
            var c = getData(comment);
            for (var s : c) {
                sb.append(":").append(s).append("\n");
            }
        }

        // 事件 ID
        if (id != null) {
            sb.append("id: ").append(id).append("\n");
        }

        // 事件类型
        if (event != null) {
            sb.append("event: ").append(event).append("\n");
        }

        // 重试时间
        if (retry != null) {
            sb.append("retry: ").append(retry).append("\n");
        }

        // 事件数据
        if (data != null) {
            var d = getData(data);
            for (var s : d) {
                sb.append("data: ").append(s).append("\n");
            }
        }

        // 事件结束, 追加空行 (用于分隔事件)
        sb.append("\n");

        // 写入输出流
        out.write(sb.toString().getBytes(UTF_8)); // 使用 UTF-8 编码
        out.flush();  // 确保数据已发送
    }

    /// 发送一个 SSE 事件
    ///
    /// @param sseEvent 要发送的 SSE 事件
    public void send(SseEvent sseEvent) throws ScxOutputException, OutputAlreadyClosedException {
        send0(sseEvent, out);
    }

    @Override
    public void close() throws ScxOutputException, OutputAlreadyClosedException {
        out.close();  // 关闭输出流
    }

}
