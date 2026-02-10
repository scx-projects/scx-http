package dev.scx.http.headers;

/// ScxHttpHeaderNameImpl (不允许继承 和 外部 new 创建)
///
/// @author scx567888
/// @version 0.0.1
record ScxHttpHeaderNameImpl(String value) implements ScxHttpHeaderName {

    @Override
    public String toString() {
        return value;
    }

}
