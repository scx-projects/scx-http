package cool.scx.http.media.event_stream;

import cool.scx.http.sender.HttpSendException;
import cool.scx.io.ByteOutput;
import cool.scx.io.exception.AlreadyClosedException;
import cool.scx.io.exception.ScxIOException;

import static cool.scx.http.media.event_stream.EventStreamHelper.writeToOutputStream;

/// ServerEventStream
///
/// @author scx567888
/// @version 0.0.1
public class ServerEventStream implements AutoCloseable {

    private final ByteOutput out;

    public ServerEventStream(ByteOutput out) {
        this.out = out;
    }

    /// 发送一个 SSE 事件
    ///
    /// @param sseEvent 要发送的 SSE 事件
    public void send(SseEvent sseEvent) throws HttpSendException {
        try {
            writeToOutputStream(sseEvent, out);  // 使用 SseEvent 的 writeToOutputStream 方法发送事件
        } catch (ScxIOException | AlreadyClosedException e) {
            throw new HttpSendException("发送 EventStream 时发生异常 !!!", e);
        }
    }

    @Override
    public void close() throws ScxIOException, AlreadyClosedException {
        out.close();  // 关闭输出流
    }

}
