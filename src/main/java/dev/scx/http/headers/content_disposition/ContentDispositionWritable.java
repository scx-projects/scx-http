package dev.scx.http.headers.content_disposition;

import dev.scx.http.parameters.Parameters;
import dev.scx.http.parameters.ParametersWritable;

/// ContentDispositionWritable
///
/// @author scx567888
/// @version 0.0.1
public interface ContentDispositionWritable extends ContentDisposition {

    ParametersWritable<String, String> params();

    default ContentDispositionWritable params(Parameters<String, String> otherParams) {
        params().clear();
        for (var e : otherParams) {
            params().set(e.name(), e.values());
        }
        return this;
    }

    default ContentDispositionWritable name(String name) {
        params().set("name", name);
        return this;
    }

    default ContentDispositionWritable filename(String filename) {
        params().set("filename", filename);
        return this;
    }

    default ContentDispositionWritable size(long size) {
        params().set("size", size + "");
        return this;
    }

}
