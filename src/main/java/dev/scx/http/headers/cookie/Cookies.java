package dev.scx.http.headers.cookie;

import java.util.List;

/// Cookies
///
/// @author scx567888
/// @version 0.0.1
public interface Cookies extends Iterable<Cookie> {

    static CookiesWritable of() {
        return new CookiesImpl();
    }

    static CookiesWritable of(Cookies oldCookies) {
        var c = new CookiesImpl();
        oldCookies.forEach(c::add);
        return c;
    }

    /// cookie 头
    static CookiesWritable parse(String cookieStr) {
        return CookieHelper.parseCookies(cookieStr);
    }

    /// set-cookie 头
    static CookiesWritable parseSetCookie(List<String> setCookieStrList) {
        return CookieHelper.parseSetCookie(setCookieStrList);
    }

    long size();

    Cookie get(String name);

    default String encodeCookie() {
        return CookieHelper.encodeCookie(this);
    }

    default String[] encodeSetCookie() {
        return CookieHelper.encodeSetCookie(this);
    }

}
