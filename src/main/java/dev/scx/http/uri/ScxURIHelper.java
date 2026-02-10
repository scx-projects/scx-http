package dev.scx.http.uri;

import dev.scx.http.parameters.Parameters;
import dev.scx.http.parameters.ParametersWritable;

import java.net.URLDecoder;
import java.util.ArrayList;

import static dev.scx.http.uri.URIEncoder.encodeURIComponent;
import static java.nio.charset.StandardCharsets.UTF_8;

/// ScxURIHelper
///
/// @author scx567888
/// @version 0.0.1
public final class ScxURIHelper {

    /// 注意参数是 编码后的 rawQuery
    public static <T extends ParametersWritable<String, String>> T decodeQuery(T query, String rawQuery) {
        if (rawQuery == null) {
            return query;
        }
        var parts = rawQuery.split("&");
        for (var s : parts) {
            var split = s.split("=", 2);
            if (split.length == 2) {
                var rawKey = split[0];
                var rawValue = split[1];
                // 解码
                var key = URLDecoder.decode(rawKey, UTF_8);
                var value = URLDecoder.decode(rawValue, UTF_8);
                query.add(key, value);
            }
        }
        return query;
    }

    public static String encodeQuery(Parameters<String, String> query, boolean uriEncoding) {
        var l = new ArrayList<String>();
        for (var v : query) {
            var key = v.name();
            var value = v.values();
            for (var s : value) {
                if (uriEncoding) {
                    var kk = encodeURIComponent(key);
                    var vv = encodeURIComponent(s);
                    l.add(kk + "=" + vv);
                } else {
                    l.add(key + "=" + s);
                }
            }
        }
        return String.join("&", l);
    }

    public static String encodeScxURI(ScxURI uri, boolean uriEncoding) {
        var scheme = uri.scheme();
        var host = uri.host();
        var port = uri.port();
        var path = uri.path();
        var query = uri.query();
        var fragment = uri.fragment();

        //拼接 scheme
        var sb = new StringBuilder();
        if (scheme != null) {
            sb.append(scheme);
            sb.append(':');
        }

        //拼接 host
        if (host != null) {
            // "//" 应该只在 scheme 和 host 同时存在时才拼接
            if (scheme != null) {
                sb.append("//");
            }

            sb.append(host);

            //拼接 端口号
            if (port != null) {
                sb.append(':');
                sb.append(port);
            }
        }

        //拼接 path
        if (path != null) {
            //是否需要进行 uri 编码
            if (uriEncoding) {
                //我们不编码 "/"
                sb.append(URIEncoder.encodeURI(path));
            } else {
                sb.append(path);
            }
        }

        //拼接查询参数
        if (query != null && !query.isEmpty()) {
            sb.append('?');
            sb.append(encodeQuery(query, uriEncoding));
        }

        if (fragment != null) {
            sb.append('#');
            //是否需要进行 uri 编码
            if (uriEncoding) {
                sb.append(URIEncoder.encodeURI(fragment));
            } else {
                sb.append(fragment);
            }
        }
        return sb.toString();
    }

}
