package cool.scx.http.media.multi_part;

import cool.scx.http.headers.ScxHttpHeaders;
import cool.scx.http.headers.ScxHttpHeadersWritable;
import cool.scx.http.media.MediaWriter;
import cool.scx.http.media_type.ScxMediaType;
import cool.scx.io.ByteChunk;
import cool.scx.io.ByteOutput;
import cool.scx.io.exception.AlreadyClosedException;
import cool.scx.io.exception.ScxIOException;

import static cool.scx.http.media_type.MediaType.MULTIPART_FORM_DATA;

/// MultiPartMediaWriter
///
/// @author scx567888
/// @version 0.0.1
public final class MultiPartMediaWriter implements MediaWriter {

    private final MultiPart multiPart;

    public MultiPartMediaWriter(MultiPart multiPart) {
        this.multiPart = multiPart;
    }

    @Override
    public long beforeWrite(ScxHttpHeadersWritable responseHeaders, ScxHttpHeaders requestHeaders) {
        if (responseHeaders.contentType() == null) {
            // MULTIPART 有很多类型 这里暂时只当成 MULTIPART_FORM_DATA
            responseHeaders.contentType(ScxMediaType.of(MULTIPART_FORM_DATA).boundary(this.multiPart.boundary()));
        }
        // 也可以更改为 先计算 MultiPart 的总长度 然后发送字节.  但是因为 MultiPartPart 的长度比较难计算, 这里我们为了简化复杂度, 仍然采取 -1
        return -1;
    }

    @Override
    public void write(ByteOutput byteOutput) throws ScxIOException, AlreadyClosedException {
        //头
        var h = ByteChunk.of("--" + multiPart.boundary() + "\r\n");
        //尾
        var f = ByteChunk.of("--" + multiPart.boundary() + "--\r\n");
        //换行符
        var l = ByteChunk.of("\r\n");
        try (byteOutput) {
            //发送每个内容
            for (var multiPartPart : multiPart) {
                //发送头
                byteOutput.write(h);
                var headers = multiPartPart.headers().encode();
                //写入头
                byteOutput.write(ByteChunk.of(headers));
                //写入换行符
                byteOutput.write(l);
                //写入内容
                try (var i = multiPartPart.byteInput()) {
                    i.transferToAll(byteOutput);
                }
                //写入换行符
                byteOutput.write(l);
            }
            byteOutput.write(f);
        }
    }

}
