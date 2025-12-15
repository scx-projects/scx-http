package dev.scx.http.media.event_stream;

import dev.scx.http.headers.ScxHttpHeaders;
import dev.scx.http.media.MediaReader;
import dev.scx.io.ByteInput;

import static dev.scx.http.media.string.StringMediaReader.getContentTypeCharsetOrUTF8;

/// ClientEventStreamMediaReader
///
/// @author scx567888
/// @version 0.0.1
public final class ClientEventStreamMediaReader implements MediaReader<ClientEventStream> {

    public static final ClientEventStreamMediaReader CLIENT_EVENT_STREAM_MEDIA_READER = new ClientEventStreamMediaReader();

    private ClientEventStreamMediaReader() {

    }

    @Override
    public ClientEventStream read(ByteInput byteInput, ScxHttpHeaders requestHeaders) {
        // EventStream 是有字符集的概念的 所以这里需要读取字符集
        var charset = getContentTypeCharsetOrUTF8(requestHeaders);
        return new ClientEventStream(byteInput, charset);
    }

}
