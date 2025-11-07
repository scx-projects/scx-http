package cool.scx.http.body;

import cool.scx.http.exception.UnsupportedMediaTypeException;
import cool.scx.http.headers.ScxHttpHeaders;
import cool.scx.http.headers.content_encoding.ScxContentEncoding;
import cool.scx.http.media.MediaReader;
import cool.scx.io.ByteInput;
import cool.scx.io.ScxIO;
import cool.scx.io.exception.AlreadyClosedException;
import cool.scx.io.exception.ScxIOException;

import static cool.scx.http.headers.content_encoding.ContentEncoding.GZIP;

/// GzipHttpBody
///
/// @author scx567888
/// @version 0.0.1
public final class GzipHttpBody implements ScxHttpBody {

    private final ScxHttpHeaders headers;
    private final ByteInput gzipByteInput;

    public GzipHttpBody(ByteInput byteInput, ScxHttpHeaders headers) {
        this.headers = headers;
        this.gzipByteInput = intGZIPByteInput(byteInput, this.headers.contentEncoding());
    }

    public static ByteInput intGZIPByteInput(ByteInput byteInput, ScxContentEncoding contentEncoding) {
        //没有 contentEncoding 直接返回原始流
        if (contentEncoding == null) {
            return byteInput;
        }
        //等于 GZIP 我们尝试包装
        if (contentEncoding == GZIP) {
            try {
                return ScxIO.gzipByteInput(byteInput);
            } catch (ScxIOException e) {
                //原始流有可能并不是一个 合法的 gzip 流 我们抛出异常
                throw new UnsupportedMediaTypeException(e.getCause());
            }
        } else {// 否则我们不支持这种类型 抛出异常
            throw new UnsupportedMediaTypeException("Unsupported Content-Encoding: " + contentEncoding);
        }
    }

    @Override
    public ByteInput byteInput() {
        return gzipByteInput;
    }

    @Override
    public <T> T as(MediaReader<T> mediaReader) throws BodyReadException, BodyAlreadyConsumedException {
        try {
            return mediaReader.read(gzipByteInput, headers);
        } catch (ScxIOException e) {
            throw new BodyReadException(e);
        } catch (AlreadyClosedException e) {
            throw new BodyAlreadyConsumedException();
        }
    }

    @Override
    public GzipHttpBody asGzipBody() {
        return this;
    }

}
