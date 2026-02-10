package dev.scx.http.media.byte_array;

import dev.scx.http.media.MediaReader;
import dev.scx.http.media_type.ScxMediaType;
import dev.scx.io.ByteInput;
import dev.scx.io.exception.InputAlreadyClosedException;
import dev.scx.io.exception.ScxInputException;

/// ByteArrayMediaReader
///
/// 保持单例模式
///
/// @author scx567888
/// @version 0.0.1
public final class ByteArrayMediaReader implements MediaReader<byte[], RuntimeException> {

    public static final ByteArrayMediaReader BYTE_ARRAY_MEDIA_READER = new ByteArrayMediaReader();

    private ByteArrayMediaReader() {

    }

    @Override
    public byte[] read(ByteInput byteInput, ScxMediaType mediaType) throws ScxInputException, InputAlreadyClosedException {
        try (byteInput) {
            return byteInput.readAll();
        }
    }

}
