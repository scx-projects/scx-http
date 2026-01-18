package dev.scx.http.media.byte_array;

import dev.scx.http.media.MediaWriter;
import dev.scx.io.ByteOutput;
import dev.scx.io.exception.AlreadyClosedException;
import dev.scx.io.exception.ScxIOException;

/// ByteArrayMediaWriter
///
/// @author scx567888
/// @version 0.0.1
public final class ByteArrayMediaWriter implements MediaWriter {

    private final byte[] bytes;

    public ByteArrayMediaWriter(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public Long bodyLength() {
        return (long) bytes.length;
    }

    @Override
    public void write(ByteOutput byteOutput) throws ScxIOException, AlreadyClosedException {
        try (byteOutput) {
            byteOutput.write(bytes);
        }
    }

}
