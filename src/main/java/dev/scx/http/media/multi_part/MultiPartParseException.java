package dev.scx.http.media.multi_part;

import dev.scx.http.exception.ScxHttpException;
import dev.scx.http.status_code.ScxHttpStatusCode;

import static dev.scx.http.status_code.HttpStatusCode.BAD_REQUEST;

/// MultiPartParseException
///
/// @author scx567888
/// @version 0.0.1
public final class MultiPartParseException extends RuntimeException implements ScxHttpException {

    public MultiPartParseException(String message) {
        super(message);
    }

    @Override
    public ScxHttpStatusCode statusCode() {
        return BAD_REQUEST;
    }

}
