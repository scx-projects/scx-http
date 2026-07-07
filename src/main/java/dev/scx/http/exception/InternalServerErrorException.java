package dev.scx.http.exception;

import static dev.scx.http.status_code.HttpStatusCode.INTERNAL_SERVER_ERROR;

/// InternalServerErrorException
///
/// @author scx567888
public class InternalServerErrorException extends HttpException {

    public InternalServerErrorException() {
        super(INTERNAL_SERVER_ERROR);
    }

    public InternalServerErrorException(String message) {
        super(INTERNAL_SERVER_ERROR, message);
    }

    public InternalServerErrorException(Throwable cause) {
        super(INTERNAL_SERVER_ERROR, cause);
    }

    public InternalServerErrorException(String message, Throwable cause) {
        super(INTERNAL_SERVER_ERROR, message, cause);
    }

}
