package dev.scx.http.exception;

import static dev.scx.http.status_code.HttpStatusCode.UNSUPPORTED_MEDIA_TYPE;

/// UnsupportedMediaTypeException
///
/// @author scx567888
public class UnsupportedMediaTypeException extends HttpException {

    public UnsupportedMediaTypeException() {
        super(UNSUPPORTED_MEDIA_TYPE);
    }

    public UnsupportedMediaTypeException(String message) {
        super(UNSUPPORTED_MEDIA_TYPE, message);
    }

    public UnsupportedMediaTypeException(Throwable cause) {
        super(UNSUPPORTED_MEDIA_TYPE, cause);
    }

    public UnsupportedMediaTypeException(String message, Throwable cause) {
        super(UNSUPPORTED_MEDIA_TYPE, message, cause);
    }

}
