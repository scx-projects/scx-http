package dev.scx.http.sender;

/// ScxHttpSendException
///
/// @author scx567888
/// @version 0.0.1
public class ScxHttpSendException extends RuntimeException {

    public ScxHttpSendException(String message) {
        super(message);
    }

    public ScxHttpSendException(Throwable cause) {
        super(cause);
    }

    public ScxHttpSendException(String message, Throwable cause) {
        super(message, cause);
    }

}
