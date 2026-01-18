package dev.scx.http.received;

import dev.scx.http.exception.UnsupportedMediaTypeException;
import dev.scx.http.headers.ScxHttpHeaders;
import dev.scx.http.headers.content_encoding.ScxContentEncoding;
import dev.scx.io.ByteInput;
import dev.scx.io.ScxIO;

import static dev.scx.http.headers.content_encoding.ContentEncoding.GZIP;

/// GzipHttpReceived
///
/// @author scx567888
/// @version 0.0.1
public final class GzipHttpReceived implements ScxHttpMediaReceived {

    private final ScxHttpReceived httpReceived;
    private final ByteInput gzipBody;

    public GzipHttpReceived(ScxHttpReceived httpReceived) throws UnsupportedMediaTypeException {
        this.httpReceived = httpReceived;
        this.gzipBody = intGZIPByteInput(this.httpReceived.body(), this.httpReceived.headers().contentEncoding());
    }

    public static ByteInput intGZIPByteInput(ByteInput byteInput, ScxContentEncoding contentEncoding) throws UnsupportedMediaTypeException {
        //没有 contentEncoding 直接返回原始流
        if (contentEncoding == null) {
            return byteInput;
        }
        //等于 GZIP 我们尝试包装
        if (contentEncoding == GZIP) {
            return ScxIO.gzipByteInput(byteInput);
        } else {// 否则我们不支持这种类型 抛出异常
            throw new UnsupportedMediaTypeException("Unsupported Content-Encoding: " + contentEncoding);
        }
    }

    @Override
    public ScxHttpHeaders headers() {
        return httpReceived.headers();
    }

    @Override
    public Long bodyLength() {
        // 这里因为我们不知道 gzip 的最终长度 所以返回 null
        return null;
    }

    @Override
    public ByteInput body() {
        return gzipBody;
    }

    @Override
    public GzipHttpReceived gzip() {
        // 我们不再重复包装
        return this;
    }

}
