package dev.scx.http.media_type;

import dev.scx.http.exception.ScxHttpException;
import dev.scx.http.status_code.ScxHttpStatusCode;

import static dev.scx.http.status_code.HttpStatusCode.UNSUPPORTED_MEDIA_TYPE;

/// IllegalMediaTypeException
///
/// @author scx567888
/// @version 0.0.1
public class IllegalMediaTypeException extends RuntimeException implements ScxHttpException {

    public IllegalMediaTypeException(String message) {
        super(message);
    }

    @Override
    public ScxHttpStatusCode statusCode() {
        return UNSUPPORTED_MEDIA_TYPE;
    }

}
