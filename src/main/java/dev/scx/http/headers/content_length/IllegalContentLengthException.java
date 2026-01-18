package dev.scx.http.headers.content_length;

import dev.scx.http.exception.ScxHttpException;
import dev.scx.http.status_code.ScxHttpStatusCode;

import static dev.scx.http.status_code.HttpStatusCode.BAD_REQUEST;

/// IllegalContentLengthException
///
/// @author scx567888
/// @version 0.0.1
public final class IllegalContentLengthException extends RuntimeException implements ScxHttpException {

    public IllegalContentLengthException(String message) {
        super(message);
    }

    @Override
    public ScxHttpStatusCode statusCode() {
        return BAD_REQUEST;
    }

}
