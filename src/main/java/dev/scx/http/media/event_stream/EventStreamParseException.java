package dev.scx.http.media.event_stream;

/// EventStreamParseException
///
/// @author scx567888
/// @version 0.0.1
public final class EventStreamParseException extends RuntimeException {

    public EventStreamParseException(String message) {
        super(message);
    }

    public EventStreamParseException(Throwable cause) {
        super(cause);
    }

    public EventStreamParseException(String message, Throwable cause) {
        super(message, cause);
    }

}
