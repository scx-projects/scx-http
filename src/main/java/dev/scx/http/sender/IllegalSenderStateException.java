package dev.scx.http.sender;

/// IllegalSenderStateException
///
/// Sender 状态异常
///
/// @author scx567888
/// @version 0.0.1
public final class IllegalSenderStateException extends IllegalStateException {

    public IllegalSenderStateException(ScxHttpSenderStatus status) {
        super("Cannot send: sender is in " + status + " state.");
    }

}
