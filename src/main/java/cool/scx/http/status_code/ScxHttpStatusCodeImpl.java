package cool.scx.http.status_code;

/// ScxHttpStatusCodeImpl
///
/// @author scx567888
/// @version 0.0.1
record ScxHttpStatusCodeImpl(int value) implements ScxHttpStatusCode {

    @Override
    public String toString() {
        return value + "";
    }

}
