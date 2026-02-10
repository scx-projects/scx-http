package dev.scx.http.media.event_stream;

import dev.scx.http.media.MediaReader;
import dev.scx.http.media_type.ScxMediaType;
import dev.scx.io.ByteInput;

import static dev.scx.http.media.string.StringMediaReader.getCharsetOrUTF8;

/// ClientEventStreamMediaReader
///
/// @author scx567888
/// @version 0.0.1
public final class ClientEventStreamMediaReader implements MediaReader<ClientEventStream, RuntimeException> {

    public static final ClientEventStreamMediaReader CLIENT_EVENT_STREAM_MEDIA_READER = new ClientEventStreamMediaReader();

    private ClientEventStreamMediaReader() {

    }

    @Override
    public ClientEventStream read(ByteInput byteInput, ScxMediaType mediaType) {
        // EventStream 是有字符集的概念的 所以这里需要读取字符集
        var charset = getCharsetOrUTF8(mediaType);
        return new ClientEventStream(byteInput, charset);
    }

}
