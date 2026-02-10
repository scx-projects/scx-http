package dev.scx.http.headers.content_encoding;

/// ScxContentEncodingImpl (不允许继承 和 外部 new 创建)
///
/// @author scx567888
/// @version 0.0.1
record ScxContentEncodingImpl(String value) implements ScxContentEncoding {

    ScxContentEncodingImpl {
        value = value.toLowerCase();
    }

    @Override
    public String toString() {
        return value;
    }

}
