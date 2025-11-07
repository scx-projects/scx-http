package cool.scx.http.headers.accept;

/// IllegalMediaRangeException
///
/// @author scx567888
/// @version 0.0.1
public class IllegalMediaRangeException extends Exception {

    public final String mediaRangeStr;

    public IllegalMediaRangeException(String mediaRangeStr) {
        this.mediaRangeStr = mediaRangeStr;
    }

}
