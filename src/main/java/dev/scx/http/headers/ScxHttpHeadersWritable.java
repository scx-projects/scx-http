package dev.scx.http.headers;

import dev.scx.http.headers.content_disposition.ContentDisposition;
import dev.scx.http.headers.content_encoding.ScxContentEncoding;
import dev.scx.http.headers.cookie.Cookie;
import dev.scx.http.headers.cookie.Cookies;
import dev.scx.http.headers.cookie.CookiesWritable;
import dev.scx.http.media_type.ScxMediaType;
import dev.scx.http.parameters.ParametersWritable;

import java.util.Collection;

import static dev.scx.http.headers.HttpHeaderName.*;

/// 可写的 Headers 可用于 ServerResponse 和 ClientRequest
/// 在 Parameters 的基础上实现了一些 方便操作 Http 头协议的方法
///
/// @author scx567888
/// @version 0.0.1
public interface ScxHttpHeadersWritable extends ScxHttpHeaders, ParametersWritable<ScxHttpHeaderName, String> {

    @Override
    ScxHttpHeadersWritable set(ScxHttpHeaderName name, String... values);

    @Override
    ScxHttpHeadersWritable set(ScxHttpHeaderName name, Collection<? extends String> values);

    @Override
    ScxHttpHeadersWritable add(ScxHttpHeaderName name, String... values);

    @Override
    ScxHttpHeadersWritable add(ScxHttpHeaderName name, Collection<? extends String> values);

    @Override
    ScxHttpHeadersWritable remove(ScxHttpHeaderName name);

    @Override
    ScxHttpHeadersWritable clear();

    default ScxHttpHeadersWritable set(String name, String... values) {
        return set(ScxHttpHeaderName.of(name), values);
    }

    default ScxHttpHeadersWritable add(String name, String... values) {
        return add(ScxHttpHeaderName.of(name), values);
    }

    default ScxHttpHeadersWritable remove(String name) {
        return remove(ScxHttpHeaderName.of(name));
    }

    default ScxHttpHeadersWritable cookies(Cookies cookies) {
        return set(COOKIE, cookies.encodeCookie());
    }

    default ScxHttpHeadersWritable setCookies(Cookies cookies) {
        return set(SET_COOKIE, cookies.encodeSetCookie());
    }

    default ScxHttpHeadersWritable contentType(ScxMediaType mediaType) {
        return set(CONTENT_TYPE, mediaType.encode());
    }

    default ScxHttpHeadersWritable contentDisposition(ContentDisposition contentDisposition) {
        return set(CONTENT_DISPOSITION, contentDisposition.encode());
    }

    default ScxHttpHeadersWritable contentLength(long contentLength) {
        return set(CONTENT_LENGTH, String.valueOf(contentLength));
    }

    default ScxHttpHeadersWritable contentEncoding(ScxContentEncoding contentEncoding) {
        return set(CONTENT_ENCODING, contentEncoding.value());
    }

    default ScxHttpHeadersWritable addCookie(Cookie... cookies) {
        // 这里强转是安全的
        var newCookies = (CookiesWritable) cookies();
        for (var c : cookies) {
            newCookies.add(c);
        }
        return cookies(newCookies);
    }

    default ScxHttpHeadersWritable removeCookie(String name) {
        // 这里强转是安全的
        var newCookies = (CookiesWritable) cookies();
        newCookies.remove(name);
        return cookies(newCookies);
    }

    default ScxHttpHeadersWritable addSetCookie(Cookie... cookies) {
        // 这里强转是安全的
        var newSetCookies = (CookiesWritable) setCookies();
        for (var c : cookies) {
            newSetCookies.add(c);
        }
        return setCookies(newSetCookies);
    }

    default ScxHttpHeadersWritable removeSetCookie(String name) {
        // 这里强转是安全的
        var newSetCookies = (CookiesWritable) setCookies();
        newSetCookies.remove(name);
        return setCookies(newSetCookies);
    }

}
