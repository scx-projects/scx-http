package dev.scx.http.headers;

/// ScxHttpHeaderName 是不区分大小写的, 我们这里全部按照小写处理
///
/// 该接口被设计为 sealed, 原因参考 [dev.scx.http.method.ScxHttpMethod]
///
/// @author scx567888
/// @version 0.0.1
public sealed interface ScxHttpHeaderName permits HttpHeaderName, ScxHttpHeaderNameImpl {

    static ScxHttpHeaderName of(String name) {
        // 优先使用 HttpHeaderName
        var n = HttpHeaderName.find(name);
        return n != null ? n : new ScxHttpHeaderNameImpl(name.toLowerCase());
    }

    String value();

}
