package cool.scx.http.body;

/// 请求体已经被消费异常
///
/// @author scx567888
/// @version 0.0.1
public final class BodyAlreadyConsumedException extends IllegalStateException {

    public BodyAlreadyConsumedException() {
        super("The body has already been consumed.");
    }

}
