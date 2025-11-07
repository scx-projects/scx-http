package cool.scx.http.body;

/// 读取异常 一般表示底层异常
///
/// @author scx567888
/// @version 0.0.1
public class BodyReadException extends RuntimeException {

    public BodyReadException(Throwable cause) {
        super(cause);
    }

}
