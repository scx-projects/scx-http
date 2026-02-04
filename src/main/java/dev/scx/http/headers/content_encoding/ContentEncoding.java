package dev.scx.http.headers.content_encoding;

/// ContentEncoding
///
/// @author scx567888
/// @version 0.0.1
public enum ContentEncoding implements ScxContentEncoding {

    GZIP("gzip");

    private final String value;

    ContentEncoding(String value) {
        this.value = value;
    }

    /// @param contentEncoding contentEncoding
    /// @return 未找到抛出异常
    public static ContentEncoding of(String contentEncoding) {
        //数量较少时 switch 性能要高于 Map
        var h = find(contentEncoding);
        if (h == null) {
            throw new IllegalArgumentException("Unknown content-encoding: " + contentEncoding);
        }
        return h;
    }

    /// @param contentEncoding contentEncoding
    /// @return 未找到返回 null
    public static ContentEncoding find(String contentEncoding) {
        var lv = contentEncoding.toLowerCase();
        //数量较少时 switch 性能要高于 Map
        return switch (lv) {
            case "gzip" -> GZIP;
            default -> null;
        };
    }

    @Override
    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

}
