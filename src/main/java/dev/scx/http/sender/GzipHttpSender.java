package dev.scx.http.sender;

import dev.scx.exception.ScxWrappedException;
import dev.scx.http.headers.ScxHttpHeadersWritable;
import dev.scx.io.ByteOutput;
import dev.scx.io.ScxIO;

import static dev.scx.http.headers.content_encoding.ContentEncoding.GZIP;

/// GzipHttpSender
///
/// @author scx567888
/// @version 0.0.1
public final class GzipHttpSender<T> implements ScxHttpMediaSender<T> {

    private final ScxHttpSender<T> httpSender;

    public GzipHttpSender(ScxHttpSender<T> httpSender) {
        this.httpSender = httpSender;
    }

    @Override
    public ScxHttpHeadersWritable headers() {
        return httpSender.headers();
    }

    @Override
    public T send(BodyWriter bodyWriter) throws IllegalSenderStateException, ScxHttpSendException, ScxWrappedException, ScxHttpReceiveException {
        // 设置 Content-Encoding : gzip.
        headers().contentEncoding(GZIP);
        return httpSender.send(new GzipBodyWriter(bodyWriter));
    }

    private record GzipBodyWriter(BodyWriter bodyWriter) implements BodyWriter {

        @Override
        public Long bodyLength() {
            // 也可以更改为 先计算压缩后 返回压缩后的长度 然后发送字节. 但是因为 性能可能无法保证 这里我们为了简化复杂度, 仍然采取 null (未知)
            return null;
        }

        @Override
        public void write(ByteOutput byteOutput) throws Throwable {
            // 这里不能擅自关闭 byteOutput, 因为 真正的关闭流程应该发生在 bodyWriter 中. 当前的 GzipBodyWriter 只是一个包装器.
            var gzipByteOutput = ScxIO.gzipByteOutput(byteOutput);
            bodyWriter.write(gzipByteOutput);
        }

    }

}
