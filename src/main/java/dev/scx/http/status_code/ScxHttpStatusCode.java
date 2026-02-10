package dev.scx.http.status_code;

/// ScxHttpStatusCode
///
/// 该接口被设计为 sealed, 原因参考 [dev.scx.http.method.ScxHttpMethod]
///
/// @author scx567888
/// @version 0.0.1
public sealed interface ScxHttpStatusCode permits HttpStatusCode, ScxHttpStatusCodeImpl {

    static ScxHttpStatusCode of(int statusCode) {
        // 优先使用 HttpStatusCode
        var s = HttpStatusCode.find(statusCode);
        return s != null ? s : new ScxHttpStatusCodeImpl(statusCode);
    }

    int value();

}
