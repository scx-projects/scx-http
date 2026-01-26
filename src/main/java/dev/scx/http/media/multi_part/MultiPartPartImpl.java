package dev.scx.http.media.multi_part;

import dev.scx.http.headers.ScxHttpHeaders;
import dev.scx.http.headers.ScxHttpHeadersWritable;
import dev.scx.io.ByteInput;

/// MultiPartPartImpl
///
/// @author scx567888
/// @version 0.0.1
public final class MultiPartPartImpl implements MultiPartPartWritable {

    private final ScxHttpHeadersWritable headers;
    private ByteInput body;

    public MultiPartPartImpl() {
        this.headers = ScxHttpHeaders.of();
    }

    @Override
    public ScxHttpHeadersWritable headers() {
        return headers;
    }

    @Override
    public ByteInput body() {
        return body;
    }

    @Override
    public MultiPartPartWritable body(ByteInput os) {
        body = os;
        return this;
    }

}
