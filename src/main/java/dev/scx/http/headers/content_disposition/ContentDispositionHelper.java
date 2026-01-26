package dev.scx.http.headers.content_disposition;

import dev.scx.http.parameters.Parameters;
import dev.scx.http.parameters.ParametersWritable;

import static dev.scx.http.media_type.ScxMediaTypeHelper.SEMICOLON_PATTERN;
import static dev.scx.http.media_type.ScxMediaTypeHelper.encodeParams;

/// ContentDispositionHelper
///
/// @author scx567888
/// @version 0.0.1
public class ContentDispositionHelper {

    public static ContentDispositionWritable parseContentDisposition(String contentDispositionStr) {
        if (contentDispositionStr == null) {
            return null;
        }
        var parts = SEMICOLON_PATTERN.split(contentDispositionStr);
        if (parts.length == 0) {
            return null;
        }
        var type = parts[0];
        ParametersWritable<String, String> params = Parameters.of();
        for (var i = 1; i < parts.length; i = i + 1) {
            var s = parts[i].split("=", 2);
            if (s.length == 2) {
                //移除两端的引号
                params.add(s[0], removeQuotes(s[1]));
            }
        }
        return new ContentDispositionImpl(type).params(params);
    }

    public static String encodeContentDisposition(ContentDisposition contentDisposition) {
        var type = contentDisposition.type();
        var params = contentDisposition.params();
        var sb = new StringBuilder(type);
        if (params != null) {
            encodeParams(sb, params);
        }
        return sb.toString();
    }

    private static String removeQuotes(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        int start = 0;
        int end = str.length();

        if (str.charAt(0) == '\"') {
            start = start + 1;
        }

        if (str.charAt(end - 1) == '\"') {
            end = end - 1;
        }

        return str.substring(start, end);
    }

}
