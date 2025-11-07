package cool.scx.http.headers.content_encoding;

/// ScxContentEncodingImpl
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
