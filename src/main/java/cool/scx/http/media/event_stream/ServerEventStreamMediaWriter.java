package cool.scx.http.media.event_stream;

import cool.scx.http.headers.ScxHttpHeaders;
import cool.scx.http.headers.ScxHttpHeadersWritable;
import cool.scx.http.media.MediaWriter;
import cool.scx.http.media_type.ScxMediaType;
import cool.scx.io.ByteOutput;

import static cool.scx.http.media_type.MediaType.TEXT_EVENT_STREAM;
import static java.nio.charset.StandardCharsets.UTF_8;

/// ServerEventStreamMediaWriter
///
/// @author scx567888
/// @version 0.0.1
public final class ServerEventStreamMediaWriter implements MediaWriter {

    private ServerEventStream eventStream;

    public ServerEventStreamMediaWriter() {

    }

    @Override
    public long beforeWrite(ScxHttpHeadersWritable responseHeaders, ScxHttpHeaders requestHeaders) {
        if (responseHeaders.contentType() == null) {
            responseHeaders.contentType(ScxMediaType.of(TEXT_EVENT_STREAM).charset(UTF_8));
        }
        return -1;// 我们无法确定长度 因为 ServerEventStream 是由用户动态写入的
    }

    @Override
    public void write(ByteOutput byteOutput) {
        eventStream = new ServerEventStream(byteOutput);
    }

    public ServerEventStream eventStream() {
        return eventStream;
    }

}
