package dev.scx.http.headers.content_length;

/// ScxContentLength
///
/// @author scx567888
/// @version 0.0.1
public final class ContentLength {

    public static long of(String c) throws IllegalContentLengthException {
        try {
            return Long.parseLong(c);
        } catch (NumberFormatException e) {
            throw new IllegalContentLengthException("Invalid Content-Length: " + c);
        }
    }

}
