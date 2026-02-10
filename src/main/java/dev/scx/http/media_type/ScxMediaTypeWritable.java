package dev.scx.http.media_type;

import dev.scx.http.parameters.Parameters;
import dev.scx.http.parameters.ParametersWritable;

import java.nio.charset.Charset;

/// ScxMediaTypeWritable
///
/// @author scx567888
/// @version 0.0.1
public interface ScxMediaTypeWritable extends ScxMediaType {

    @Override
    ParametersWritable<String, String> params();

    default ScxMediaTypeWritable params(Parameters<String, String> otherParams) {
        params().clear();
        for (var e : otherParams) {
            params().set(e.name(), e.values());
        }
        return this;
    }

    default ScxMediaTypeWritable charset(Charset c) {
        params().set("charset", c.name());
        return this;
    }

    default ScxMediaTypeWritable boundary(String boundary) {
        params().set("boundary", boundary);
        return this;
    }

}
