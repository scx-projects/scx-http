package dev.scx.http.headers.range;

import static java.lang.Long.min;
import static java.lang.Long.parseLong;

/// HttpHeader Range
///
/// @param start 可以为空
/// @param end   可以为空
/// @author scx567888
/// @version 0.0.1
public record Range(Long start, Long end) {

    public static Range of(String rangeHeader) throws IllegalRangeException {
        if (!rangeHeader.startsWith("bytes=")) {
            throw new IllegalRangeException("Invalid range header: " + rangeHeader);
        }

        var parts = rangeHeader.substring(6).split(",");
        // 我们只取头一个.
        if (parts.length < 1) {
            throw new IllegalRangeException("Invalid range header: " + rangeHeader);
        }
        var firstPart = parts[0];

        var range = firstPart.split("-", 2);
        if (range.length != 2) {
            throw new IllegalRangeException("Invalid range header: " + rangeHeader);
        }
        var startStr = range[0];
        var endStr = range[1];
        var start = !startStr.isEmpty() ? parseLong(startStr) : null;
        var end = !endStr.isEmpty() ? parseLong(endStr) : null;
        return new Range(start, end);
    }

    public long getStart() {
        return start != null ? start : 0L;
    }

    public long getEnd(long fileLength) {
        return end != null ? min(end, fileLength - 1) : fileLength - 1;
    }

}
