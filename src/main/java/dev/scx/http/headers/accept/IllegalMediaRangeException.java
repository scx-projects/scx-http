package dev.scx.http.headers.accept;

import dev.scx.http.exception.ScxHttpException;
import dev.scx.http.status_code.ScxHttpStatusCode;

import static dev.scx.http.status_code.HttpStatusCode.BAD_REQUEST;

/// IllegalMediaRangeException
///
/// @author scx567888
/// @version 0.0.1
public final class IllegalMediaRangeException extends RuntimeException implements ScxHttpException {

    public IllegalMediaRangeException(String message) {
        super(message);
    }

    @Override
    public ScxHttpStatusCode statusCode() {
        return BAD_REQUEST;
    }

}
