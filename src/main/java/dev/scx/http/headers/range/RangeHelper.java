package dev.scx.http.headers.range;

import static java.lang.Long.parseLong;

/// RangeHelper
///
/// @author scx567888
/// @version 0.0.1
public final class RangeHelper {

    public static Range parseRange(String rangeStr) throws IllegalRangeException {
        if (!rangeStr.startsWith("bytes=")) {
            throw new IllegalRangeException("Invalid range header: " + rangeStr);
        }

        var parts = rangeStr.substring(6).split(",");
        // 我们只取头一个.
        if (parts.length < 1) {
            throw new IllegalRangeException("Invalid range header: " + rangeStr);
        }
        var firstPart = parts[0];

        var range = firstPart.split("-", 2);
        if (range.length != 2) {
            throw new IllegalRangeException("Invalid range header: " + rangeStr);
        }
        var startStr = range[0];
        var endStr = range[1];
        var start = !startStr.isEmpty() ? parseLong(startStr) : null;
        var end = !endStr.isEmpty() ? parseLong(endStr) : null;
        return new Range(start, end);
    }

}
