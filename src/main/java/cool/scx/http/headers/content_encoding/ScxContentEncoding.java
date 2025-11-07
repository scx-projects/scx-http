package cool.scx.http.headers.content_encoding;

/// ScxContentEncoding
///
/// @author scx567888
/// @version 0.0.1
public sealed interface ScxContentEncoding permits ContentEncoding, ScxContentEncodingImpl {

    static ScxContentEncoding of(String v) {
        // 优先使用 ContentEncoding
        var m = ContentEncoding.find(v);
        return m != null ? m : new ScxContentEncodingImpl(v);
    }

    String value();

}
