package dev.scx.http.headers.accept;

import dev.scx.http.parameters.Parameters;
import dev.scx.http.parameters.ParametersWritable;

/// MediaRangeWritable
///
/// @author scx567888
/// @version 0.0.1
public interface MediaRangeWritable extends MediaRange {

    @Override
    ParametersWritable<String, String> params();

    default MediaRangeWritable params(Parameters<String, String> otherParams) {
        params().clear();
        for (var e : otherParams) {
            params().set(e.name(), e.values());
        }
        return this;
    }

    default MediaRangeWritable q(Double q) {
        if (q == 1.0) {
            params().remove("q");
        } else {
            params().set("q", String.valueOf(q));
        }
        return this;
    }

}
