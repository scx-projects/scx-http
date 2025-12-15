package dev.scx.http.exception;

import static dev.scx.http.status_code.HttpStatusCode.UNAUTHORIZED;

/// UnauthorizedException
///
/// @author scx567888
/// @version 0.0.1
public class UnauthorizedException extends HttpException {

    public UnauthorizedException() {
        super(UNAUTHORIZED);
    }

    public UnauthorizedException(String message) {
        super(UNAUTHORIZED, message);
    }

    public UnauthorizedException(Throwable cause) {
        super(UNAUTHORIZED, cause);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(UNAUTHORIZED, message, cause);
    }

}
