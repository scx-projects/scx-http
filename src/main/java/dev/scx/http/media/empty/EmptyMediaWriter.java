package dev.scx.http.media.empty;

import dev.scx.http.media.MediaWriter;
import dev.scx.http.media_type.ScxMediaType;
import dev.scx.io.ByteOutput;
import dev.scx.io.exception.OutputAlreadyClosedException;
import dev.scx.io.exception.ScxOutputException;

/// EmptyMediaWriter
///
/// @author scx567888
/// @version 0.0.1
public final class EmptyMediaWriter implements MediaWriter {

    public static final EmptyMediaWriter EMPTY_MEDIA_WRITER = new EmptyMediaWriter();

    private EmptyMediaWriter() {

    }

    @Override
    public ScxMediaType mediaType() {
        return null;
    }

    @Override
    public Long bodyLength() {
        return 0L;
    }

    @Override
    public void write(ByteOutput byteOutput) throws ScxOutputException, OutputAlreadyClosedException {
        try (byteOutput) {

        }
    }

}
