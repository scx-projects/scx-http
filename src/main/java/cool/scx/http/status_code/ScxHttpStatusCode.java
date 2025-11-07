package cool.scx.http.status_code;

/// ScxHttpStatusCode
///
/// @author scx567888
/// @version 0.0.1
public sealed interface ScxHttpStatusCode permits HttpStatusCode, ScxHttpStatusCodeImpl {

    static ScxHttpStatusCode of(int statusCode) {
        // 优先使用 HttpStatus
        var s = HttpStatusCode.find(statusCode);
        return s != null ? s : new ScxHttpStatusCodeImpl(statusCode);
    }

    int value();

}
