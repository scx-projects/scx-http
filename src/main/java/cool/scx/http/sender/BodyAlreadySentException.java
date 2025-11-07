package cool.scx.http.sender;

/// BodyAlreadySentException
///
/// 内容已经发送过异常
///
/// @author scx567888
/// @version 0.0.1
public final class BodyAlreadySentException extends IllegalStateException {

    public BodyAlreadySentException() {
        super("The body has already been sent.");
    }

}
