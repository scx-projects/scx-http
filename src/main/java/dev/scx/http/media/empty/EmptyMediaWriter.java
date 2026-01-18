package dev.scx.http.media.empty;

import dev.scx.http.media.MediaWriter;
import dev.scx.io.ByteOutput;
import dev.scx.io.exception.AlreadyClosedException;
import dev.scx.io.exception.ScxIOException;

/// EmptyMediaWriter
///
/// @author scx567888
/// @version 0.0.1
public final class EmptyMediaWriter implements MediaWriter {

    public static final EmptyMediaWriter EMPTY_MEDIA_WRITER = new EmptyMediaWriter();

    private EmptyMediaWriter() {

    }

    @Override
    public Long bodyLength() {
        return 0L;
    }

    @Override
    public void write(ByteOutput byteOutput) throws ScxIOException, AlreadyClosedException {
        try (byteOutput) {

        }
    }

}
