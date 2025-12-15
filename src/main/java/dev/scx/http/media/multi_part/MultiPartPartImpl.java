package dev.scx.http.media.multi_part;

import dev.scx.function.Function0;
import dev.scx.http.headers.ScxHttpHeaders;
import dev.scx.http.headers.ScxHttpHeadersWritable;
import dev.scx.io.ByteInput;

/// MultiPartPartImpl
///
/// @author scx567888
/// @version 0.0.1
public final class MultiPartPartImpl implements MultiPartPartWritable {

    private ScxHttpHeadersWritable headers;
    private Function0<ByteInput, ?> body;

    public MultiPartPartImpl() {
        this.headers = ScxHttpHeaders.of();
    }

    @Override
    public MultiPartPartWritable headers(ScxHttpHeaders headers) {
        this.headers = ScxHttpHeaders.of(headers);
        return this;
    }

    @Override
    public MultiPartPartWritable body(Function0<ByteInput, ?> os) {
        body = os;
        return this;
    }

    @Override
    public ScxHttpHeadersWritable headers() {
        return headers;
    }

    @Override
    public Function0<ByteInput, ?> body() {
        return body;
    }

}
