package dev.scx.http.media.multi_part;

import dev.scx.http.media.MediaWriter;
import dev.scx.http.media_type.ScxMediaType;
import dev.scx.io.ByteOutput;
import dev.scx.io.ScxIO;
import dev.scx.io.exception.InputAlreadyClosedException;
import dev.scx.io.exception.OutputAlreadyClosedException;
import dev.scx.io.exception.ScxInputException;
import dev.scx.io.exception.ScxOutputException;

import static dev.scx.http.media_type.MediaType.MULTIPART_FORM_DATA;

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
    public ScxMediaType mediaType() {
        // MULTIPART 有很多类型 这里暂时只当成 MULTIPART_FORM_DATA
        return ScxMediaType.of(MULTIPART_FORM_DATA).boundary(this.multiPart.boundary());
    }

    @Override
    public Long bodyLength() {
        // 也可以更改为 先计算 MultiPart 的总长度 然后发送字节.  但是因为 MultiPartPart 的长度比较难计算, 这里我们为了简化复杂度, 仍然采取 null
        return null;
    }

    @Override
    public void write(ByteOutput byteOutput) throws ScxOutputException, OutputAlreadyClosedException, ScxInputException, InputAlreadyClosedException {
        //头
        var h = ("--" + multiPart.boundary() + "\r\n").getBytes();
        //尾
        var f = ("--" + multiPart.boundary() + "--\r\n").getBytes();
        //换行符
        var l = "\r\n".getBytes();
        try (byteOutput) {
            //发送每个内容
            for (var multiPartPart : multiPart) {
                //发送头
                byteOutput.write(h);
                var headers = multiPartPart.headers().encode();
                //写入头
                byteOutput.write(headers.getBytes());
                //写入换行符
                byteOutput.write(l);
                //写入内容
                try (var i = multiPartPart.body()) {
                    // 这里发生的 ScxInputException, InputAlreadyClosedException 错误我们直接穿透.
                    ScxIO.transferToAll(i, byteOutput);
                }
                //写入换行符
                byteOutput.write(l);
            }
            byteOutput.write(f);
        }
    }

}
