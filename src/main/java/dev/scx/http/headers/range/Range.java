package dev.scx.http.headers.range;

import static java.lang.Long.min;

/// HttpHeader Range
///
/// @param start 可以为空
/// @param end   可以为空
/// @author scx567888
/// @version 0.0.1
public record Range(Long start, Long end) {

    public static Range parse(String rangeStr) throws IllegalRangeException {
        return RangeHelper.parseRange(rangeStr);
    }

    public long getStart() {
        return start != null ? start : 0L;
    }

    public long getEnd(long fileLength) {
        return end != null ? min(end, fileLength - 1) : fileLength - 1;
    }

}
