package dev.scx.http.headers.content_length;

/// ContentLengthHelper
///
/// @author scx567888
/// @version 0.0.1
public final class ContentLengthHelper {

    public static long parse(String c) throws IllegalContentLengthException {
        try {
            return Long.parseLong(c);
        } catch (NumberFormatException e) {
            throw new IllegalContentLengthException("Invalid Content-Length: " + c);
        }
    }

}
