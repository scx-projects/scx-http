package dev.scx.http.headers;

import dev.scx.http.headers.content_disposition.ContentDisposition;
import dev.scx.http.headers.content_encoding.ScxContentEncoding;
import dev.scx.http.headers.cookie.Cookie;
import dev.scx.http.media_type.ScxMediaType;

/// 这只是一个帮助接口 提供一些快捷方法 用于简化 header 的写入
///
/// @author scx567888
/// @version 0.0.1
@SuppressWarnings("unchecked")
public interface EasyHttpHeadersWriter<T extends EasyHttpHeadersWriter<T>> extends EasyHttpHeadersReader {

    @Override
    ScxHttpHeadersWritable headers();

    default T headers(ScxHttpHeaders otherHeaders) {
        headers().clear();
        for (var e : otherHeaders) {
            headers().set(e.name(), e.values());
        }
        return (T) this;
    }

    default T setHeader(ScxHttpHeaderName headerName, String... values) {
        this.headers().set(headerName, values);
        return (T) this;
    }

    default T addHeader(ScxHttpHeaderName headerName, String... values) {
        this.headers().add(headerName, values);
        return (T) this;
    }

    default T removeHeader(ScxHttpHeaderName headerName) {
        this.headers().remove(headerName);
        return (T) this;
    }

    default T setHeader(String headerName, String... values) {
        this.headers().set(headerName, values);
        return (T) this;
    }

    default T addHeader(String headerName, String... values) {
        this.headers().add(headerName, values);
        return (T) this;
    }

    default T removeHeader(String headerName) {
        this.headers().remove(headerName);
        return (T) this;
    }

    default T contentType(ScxMediaType contentType) {
        headers().contentType(contentType);
        return (T) this;
    }

    default T contentLength(long contentLength) {
        headers().contentLength(contentLength);
        return (T) this;
    }

    default T contentEncoding(ScxContentEncoding contentEncoding) {
        headers().contentEncoding(contentEncoding);
        return (T) this;
    }

    default T contentDisposition(ContentDisposition contentDisposition) {
        headers().contentDisposition(contentDisposition);
        return (T) this;
    }

    default T addCookie(Cookie... cookies) {
        headers().addCookie(cookies);
        return (T) this;
    }

    default T removeCookie(String name) {
        headers().removeCookie(name);
        return (T) this;
    }

    default T addSetCookie(Cookie... cookies) {
        headers().addSetCookie(cookies);
        return (T) this;
    }

    default T removeSetCookie(String name) {
        headers().removeSetCookie(name);
        return (T) this;
    }

}
