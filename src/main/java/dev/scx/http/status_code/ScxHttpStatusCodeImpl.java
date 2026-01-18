package dev.scx.http.status_code;

/// ScxHttpStatusCodeImpl (不允许继承 和 外部 new 创建)
///
/// @author scx567888
/// @version 0.0.1
record ScxHttpStatusCodeImpl(int value) implements ScxHttpStatusCode {

    @Override
    public String toString() {
        return value + "";
    }

}
