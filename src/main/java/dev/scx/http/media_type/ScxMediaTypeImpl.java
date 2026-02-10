package dev.scx.http.media_type;

import dev.scx.http.parameters.Parameters;
import dev.scx.http.parameters.ParametersWritable;

/// ScxMediaTypeImpl (不允许继承 和 外部 new 创建)
///
/// @author scx567888
/// @version 0.0.1
final class ScxMediaTypeImpl implements ScxMediaTypeWritable {

    private final String type;
    private final String subtype;
    private final ParametersWritable<String, String> params;

    public ScxMediaTypeImpl(String type, String subtype) {
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

    @Override
    public String toString() {
        return encode();
    }

}
