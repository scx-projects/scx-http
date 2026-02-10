package dev.scx.http.headers;

import dev.scx.http.headers.accept.Accept;
import dev.scx.http.headers.accept.IllegalMediaRangeException;
import dev.scx.http.headers.content_disposition.ContentDisposition;
import dev.scx.http.headers.content_encoding.ScxContentEncoding;
import dev.scx.http.headers.content_length.ContentLengthHelper;
import dev.scx.http.headers.content_length.IllegalContentLengthException;
import dev.scx.http.headers.cookie.Cookie;
import dev.scx.http.headers.cookie.Cookies;
import dev.scx.http.headers.range.IllegalRangeException;
import dev.scx.http.headers.range.Range;
import dev.scx.http.media_type.ScxMediaType;
import dev.scx.http.parameters.Parameters;

import java.util.List;

import static dev.scx.http.headers.HttpHeaderName.*;
import static dev.scx.http.headers.ScxHttpHeadersHelper.parseHeaders;

/// 只读的 Headers 可用于 ServerRequest 和 ClientResponse
/// 在 Parameters 的基础上实现了一些 方便操作 Http 头协议的方法
///
/// @author scx567888
/// @version 0.0.1
public interface ScxHttpHeaders extends Parameters<ScxHttpHeaderName, String> {

    static ScxHttpHeadersWritable of() {
        return new ScxHttpHeadersImpl();
    }

    static ScxHttpHeadersWritable of(ScxHttpHeaders oldHeaders) {
        return new ScxHttpHeadersImpl(oldHeaders);
    }

    /// 通过 Http1 格式文本直接构建 headers
    /// 在某些情况下很好用 比如调试
    /// 分隔符同时支持 '\r\n' 和 '\n'
    ///
    /// @param headersStr s
    /// @return s
    static ScxHttpHeadersWritable parse(String headersStr) {
        return parseHeaders(new ScxHttpHeadersImpl(), headersStr, false);
    }

    /// 通过 Http1 格式文本直接构建 headers
    /// 在某些情况下很好用 比如调试
    /// 分隔符仅支持 '\r\n'
    ///
    /// @param headersStr s
    /// @return s
    static ScxHttpHeadersWritable parseStrict(String headersStr) {
        return parseHeaders(new ScxHttpHeadersImpl(), headersStr, true);
    }

    default String get(String name) {
        return get(ScxHttpHeaderName.of(name));
    }

    default List<String> getAll(String name) {
        return getAll(ScxHttpHeaderName.of(name));
    }

    default boolean contains(String name) {
        return contains(ScxHttpHeaderName.of(name));
    }

    default Cookies cookies() {
        var c = get(COOKIE);
        // cookies 表示一个 cookie 的集合 所以这里 返回 Cookies 而不是 null
        return c != null ? Cookies.parse(c) : Cookies.of();
    }

    default Cookies setCookies() {
        var c = getAll(SET_COOKIE);
        // cookies 表示一个 cookie 的集合 所以这里 返回 Cookies 而不是 null
        return !c.isEmpty() ? Cookies.parseSetCookie(c) : Cookies.of();
    }

    default ScxMediaType contentType() {
        var v = get(CONTENT_TYPE);
        return v != null ? ScxMediaType.parse(v) : null;
    }

    default ContentDisposition contentDisposition() {
        var c = get(CONTENT_DISPOSITION);
        return c != null ? ContentDisposition.parse(c) : null;
    }

    default Long contentLength() throws IllegalContentLengthException {
        var c = get(CONTENT_LENGTH);
        return c != null ? ContentLengthHelper.parse(c) : null;
    }

    default ScxContentEncoding contentEncoding() {
        var c = get(CONTENT_ENCODING);
        return c != null ? ScxContentEncoding.of(c) : null;
    }

    default Cookie getCookie(String name) {
        return cookies().get(name);
    }

    default Cookie getSetCookie(String name) {
        return setCookies().get(name);
    }

    default Accept accept() throws IllegalMediaRangeException {
        var c = get(ACCEPT);
        return c != null ? Accept.parse(c) : null;
    }

    default Range range() throws IllegalRangeException {
        var c = get(RANGE);
        return c != null ? Range.parse(c) : null;
    }

    /// 转换成标准的 Http1 格式, 人类可读, 可用于传输或调试
    default String encode() {
        return ScxHttpHeadersHelper.encodeHeaders(this);
    }

}
