package dev.scx.http.method;

/// ScxHttpMethodImpl (不允许继承 和 外部 new 创建)
///
/// @author scx567888
/// @version 0.0.1
record ScxHttpMethodImpl(String value) implements ScxHttpMethod {

    @Override
    public String toString() {
        return value;
    }

}
