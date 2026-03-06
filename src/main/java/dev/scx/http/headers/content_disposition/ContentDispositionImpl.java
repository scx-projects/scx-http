package dev.scx.http.headers.content_disposition;

import dev.scx.http.parameters.Parameters;
import dev.scx.http.parameters.ParametersWritable;

/// ContentDispositionImpl
///
/// @author scx567888
/// @version 0.0.1
final class ContentDispositionImpl implements ContentDispositionWritable {

    private final String type;
    private final ParametersWritable<String, String> params;

    public ContentDispositionImpl(String type) {
        this.type = type;
        this.params = Parameters.of();
    }

    @Override
    public String type() {
        return type;
    }

    @Override
    public ParametersWritable<String, String> params() {
        return params;
    }

    @Override
    public String toString() {
        return encode();
    }

}
