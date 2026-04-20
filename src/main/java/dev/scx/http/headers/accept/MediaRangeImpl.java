package dev.scx.http.headers.accept;

import dev.scx.http.parameters.Parameters;
import dev.scx.http.parameters.ParametersWritable;

/// MediaRangeImpl
///
/// @author scx567888
/// @version 0.0.1
final class MediaRangeImpl implements MediaRangeWritable {

    private final String type;
    private final String subtype;
    private final ParametersWritable<String, String> params;

    public MediaRangeImpl(String type, String subtype) {
        this.type = type;
        this.subtype = subtype;
        this.params = Parameters.of();
    }

    @Override
    public String type() {
        return type;
    }

    @Override
    public String subtype() {
        return subtype;
    }

    @Override
    public ParametersWritable<String, String> params() {
        return params;
    }

}
