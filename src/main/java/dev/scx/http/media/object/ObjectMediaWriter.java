package dev.scx.http.media.object;

import dev.scx.format.NodeToFormatException;
import dev.scx.http.headers.ScxHttpHeaders;
import dev.scx.http.headers.ScxHttpHeadersWritable;
import dev.scx.http.media.MediaWriter;
import dev.scx.http.media.node.NodeMediaWriter;
import dev.scx.io.ByteOutput;
import dev.scx.io.exception.AlreadyClosedException;
import dev.scx.io.exception.ScxIOException;
import dev.scx.object.ObjectNodeConvertException;
import dev.scx.serialize.ScxSerialize;

import static dev.scx.http.media.node.NodeMediaWriter.trySetContentType;
import static dev.scx.http.media_type.MediaType.APPLICATION_JSON;
import static dev.scx.http.media_type.MediaType.APPLICATION_XML;
import static java.nio.charset.StandardCharsets.UTF_8;

/// ObjectMediaWriter  逻辑参考 {@link NodeMediaWriter}
///
/// @author scx567888
/// @version 0.0.1
public final class ObjectMediaWriter implements MediaWriter {

    private final Object object;
    private byte[] data;

    public ObjectMediaWriter(Object object) {
        this.object = object;
        this.data = null;
    }

    @Override
    public long beforeWrite(ScxHttpHeadersWritable responseHeaders, ScxHttpHeaders requestHeaders) {
        var contentType = trySetContentType(responseHeaders, requestHeaders);
        //根据类型确定内容长度
        try {
            if (APPLICATION_JSON.equalsIgnoreParams(contentType)) {
                // 这里直接 writeValueAsBytes 的话会导致 emoji 表情符被 转义 所以这里转换成 字符串 然后在处理
                data = ScxSerialize.toJson(object).getBytes(UTF_8);
            } else if (APPLICATION_XML.equalsIgnoreParams(contentType)) {
                data = ScxSerialize.toXml(object).getBytes(UTF_8);
            } else {
                //这里 表示用户设置的 类型 既不是 JSON 也不是 XML 我们无法处理 抛出异常
                throw new IllegalArgumentException("Unsupported media type: " + contentType);
            }
        } catch (ObjectNodeConvertException | NodeToFormatException e) {
            //这里表示用户的 jsonNode 无法被转换为字符串 (比如递归引用) 这里抛出异常
            throw new IllegalArgumentException(e);
        }
        return data.length;
    }

    @Override
    public void write(ByteOutput byteOutput) throws ScxIOException, AlreadyClosedException {
        try (byteOutput) {
            byteOutput.write(data);
        }
    }

}
