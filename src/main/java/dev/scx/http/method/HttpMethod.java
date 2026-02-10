package dev.scx.http.method;

/// HttpMethod
///
/// @author scx567888
/// @version 0.0.1
/// @see <a href="https://www.rfc-editor.org/rfc/rfc9110#name-method-definitions">https://www.rfc-editor.org/rfc/rfc9110#name-method-definitions</a>
public enum HttpMethod implements ScxHttpMethod {

    CONNECT("CONNECT"),
    DELETE("DELETE"),
    GET("GET"),
    HEAD("HEAD"),
    OPTIONS("OPTIONS"),
    PATCH("PATCH"),
    POST("POST"),
    PUT("PUT"),
    TRACE("TRACE");

    private final String value;

    HttpMethod(String value) {
        this.value = value;
    }

    /// @param method method
    /// @return 未找到抛出异常
    public static HttpMethod of(String method) throws IllegalArgumentException {
        //数量较少时 switch 性能要高于 Map
        var h = find(method);
        if (h == null) {
            throw new IllegalArgumentException("Unknown Http method: " + method);
        }
        return h;
    }

    /// @param method method
    /// @return 未找到返回 null
    public static HttpMethod find(String method) {
        // 数量较少时 switch 性能要高于 Map
        return switch (method) {
            case "CONNECT" -> CONNECT;
            case "DELETE" -> DELETE;
            case "GET" -> GET;
            case "HEAD" -> HEAD;
            case "OPTIONS" -> OPTIONS;
            case "PATCH" -> PATCH;
            case "POST" -> POST;
            case "PUT" -> PUT;
            case "TRACE" -> TRACE;
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
