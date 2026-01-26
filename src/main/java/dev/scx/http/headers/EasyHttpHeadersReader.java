package dev.scx.http.headers;

import dev.scx.http.headers.content_disposition.ContentDisposition;
import dev.scx.http.headers.content_encoding.ScxContentEncoding;
import dev.scx.http.headers.content_length.IllegalContentLengthException;
import dev.scx.http.headers.cookie.Cookie;
import dev.scx.http.headers.cookie.Cookies;
import dev.scx.http.media_type.ScxMediaType;

/// 这只是一个帮助接口 提供一些快捷方法 用于简化 header 的读取
///
/// @author scx567888
/// @version 0.0.1
public interface EasyHttpHeadersReader {

    ScxHttpHeaders headers();

    default String getHeader(ScxHttpHeaderName name) {
        return headers().get(name);
    }

    default String getHeader(String name) {
        return headers().get(name);
    }

    default ScxMediaType contentType() {
        return headers().contentType();
    }

    default Long contentLength() throws IllegalContentLengthException {
        return headers().contentLength();
    }

    default ScxContentEncoding contentEncoding() {
        return headers().contentEncoding();
    }

    default ContentDisposition contentDisposition() {
        return headers().contentDisposition();
    }

    default Cookies cookies() {
        return headers().cookies();
    }

    default Cookie getCookie(String name) {
        return headers().getCookie(name);
    }

    default Cookies setCookies() {
        return headers().setCookies();
    }

    default Cookie getSetCookie(String name) {
        return headers().getSetCookie(name);
    }

}
