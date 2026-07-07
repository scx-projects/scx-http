package dev.scx.http.exception;

import static dev.scx.http.status_code.HttpStatusCode.FORBIDDEN;

/// ForbiddenException
///
/// @author scx567888
public class ForbiddenException extends HttpException {

    public ForbiddenException() {
        super(FORBIDDEN);
    }

    public ForbiddenException(String message) {
        super(FORBIDDEN, message);
    }

    public ForbiddenException(Throwable cause) {
        super(FORBIDDEN, cause);
    }

    public ForbiddenException(String message, Throwable cause) {
        super(FORBIDDEN, message, cause);
    }

}
