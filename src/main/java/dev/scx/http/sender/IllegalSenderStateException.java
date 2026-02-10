package dev.scx.http.sender;

/// IllegalSenderStateException
///
/// 表示 [ScxHttpSender] 状态异常
///
/// @author scx567888
/// @version 0.0.1
public final class IllegalSenderStateException extends IllegalStateException {

    public IllegalSenderStateException(String message) {
        super(message);
    }

}
