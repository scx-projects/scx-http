package dev.scx.http.sender;

/// ScxHttpReceiveException
///
/// @author scx567888
public class ScxHttpReceiveException extends RuntimeException {

    public ScxHttpReceiveException(String message) {
        super(message);
    }

    public ScxHttpReceiveException(Throwable cause) {
        super(cause);
    }

    public ScxHttpReceiveException(String message, Throwable cause) {
        super(message, cause);
    }

}
