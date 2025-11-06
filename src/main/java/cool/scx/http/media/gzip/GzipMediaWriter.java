package cool.scx.http.media.gzip;

import cool.scx.http.headers.ScxHttpHeaders;
import cool.scx.http.headers.ScxHttpHeadersWritable;
import cool.scx.http.media.MediaWriter;
import cool.scx.io.ByteOutput;
import cool.scx.io.ScxIO;
import cool.scx.io.exception.AlreadyClosedException;
import cool.scx.io.exception.ScxIOException;

import static cool.scx.http.headers.content_encoding.ContentEncoding.GZIP;

/// GzipMediaWriter
///
/// @author scx567888
/// @version 0.0.1
public record GzipMediaWriter(MediaWriter mediaWriter) implements MediaWriter {

    @Override
    public long beforeWrite(ScxHttpHeadersWritable responseHeaders, ScxHttpHeaders requestHeaders) {
        responseHeaders.contentEncoding(GZIP);
        mediaWriter.beforeWrite(responseHeaders, requestHeaders);
        // 也可以更改为 先计算压缩后 返回压缩后的长度 然后发送字节. 但是因为 性能可能无法保证 这里我们为了简化复杂度, 仍然采取 -1
        return -1;
    }

    @Override
    public void write(ByteOutput byteOutput) throws ScxIOException, AlreadyClosedException {
        var gzipByteOutput = ScxIO.gzipByteOutput(byteOutput);
        mediaWriter.write(gzipByteOutput);
    }

}
