package cool.scx.http.sender;

/// HttpSendException
///
/// 发送异常 一般表示底层异常
///
/// @author scx567888
/// @version 0.0.1
public class HttpSendException extends RuntimeException {

    public HttpSendException(Throwable cause) {
        super(cause);
    }

    public HttpSendException(String message, Throwable cause) {
        super(message, cause);
    }

}
