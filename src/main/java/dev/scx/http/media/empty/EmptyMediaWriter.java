package dev.scx.http.media.empty;

import dev.scx.http.headers.ScxHttpHeaders;
import dev.scx.http.headers.ScxHttpHeadersWritable;
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
    public long beforeWrite(ScxHttpHeadersWritable responseHeaders, ScxHttpHeaders requestHeaders) {
        return 0;
    }

    @Override
    public void write(ByteOutput byteOutput) throws ScxIOException, AlreadyClosedException {
        try (byteOutput) {

        }
    }

}
