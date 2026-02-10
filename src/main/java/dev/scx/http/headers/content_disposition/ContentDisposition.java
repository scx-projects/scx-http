package dev.scx.http.headers.content_disposition;

import dev.scx.http.parameters.Parameters;

import static dev.scx.http.headers.content_disposition.ContentDispositionHelper.parseContentDisposition;

/// ContentDisposition
///
/// @author scx567888
/// @version 0.0.1
public interface ContentDisposition {

    static ContentDispositionWritable of(String type) {
        return new ContentDispositionImpl(type);
    }

    static ContentDispositionWritable of(ContentDisposition oldContentDisposition) {
        return new ContentDispositionImpl(oldContentDisposition.type()).params(oldContentDisposition.params());
    }

    static ContentDispositionWritable parse(String contentDispositionStr) {
        return parseContentDisposition(contentDispositionStr);
    }

    String type();

    Parameters<String, String> params();

    default String name() {
        return params().get("name");
    }

    default String filename() {
        return params().get("filename");
    }

    default String creationDate() {
        return params().get("creation-date");
    }

    default String modificationDate() {
        return params().get("modification-date");
    }

    default String readDate() {
        return params().get("read-date");
    }

    default Long size() {
        var size = params().get("size");
        return size != null ? Long.valueOf(size) : null;
    }

    default String encode() {
        return ContentDispositionHelper.encodeContentDisposition(this);
    }

}
