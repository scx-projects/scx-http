package cool.scx.http.media.empty;

import cool.scx.http.headers.ScxHttpHeaders;
import cool.scx.http.headers.ScxHttpHeadersWritable;
import cool.scx.http.media.MediaWriter;
import cool.scx.io.ByteOutput;
import cool.scx.io.exception.AlreadyClosedException;
import cool.scx.io.exception.ScxIOException;

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
