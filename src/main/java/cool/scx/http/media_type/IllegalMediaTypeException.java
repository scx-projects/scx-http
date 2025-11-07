package cool.scx.http.media_type;

/// IllegalMediaTypeException
///
/// @author scx567888
/// @version 0.0.1
public class IllegalMediaTypeException extends Exception {

    public final String mediaTypeStr;

    public IllegalMediaTypeException(String mediaTypeStr) {
        this.mediaTypeStr = mediaTypeStr;
    }

}
