package cool.scx.http.media.byte_array;

import cool.scx.http.headers.ScxHttpHeaders;
import cool.scx.http.media.MediaReader;
import cool.scx.io.ByteInput;
import cool.scx.io.exception.AlreadyClosedException;
import cool.scx.io.exception.ScxIOException;

/// ByteArrayMediaReader
///
/// 保持单例模式
///
/// @author scx567888
/// @version 0.0.1
public final class ByteArrayMediaReader implements MediaReader<byte[]> {

    public static final ByteArrayMediaReader BYTE_ARRAY_MEDIA_READER = new ByteArrayMediaReader();

    private ByteArrayMediaReader() {

    }

    @Override
    public byte[] read(ByteInput byteInput, ScxHttpHeaders headers) throws ScxIOException, AlreadyClosedException {
        try (byteInput) {
            return byteInput.readAll();
        }
    }

}
