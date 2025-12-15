package dev.scx.http.media.node;

import dev.scx.format.FormatToNodeException;
import dev.scx.http.exception.BadRequestException;
import dev.scx.http.headers.ScxHttpHeaders;
import dev.scx.http.media.MediaReader;
import dev.scx.io.ByteInput;
import dev.scx.io.exception.AlreadyClosedException;
import dev.scx.io.exception.ScxIOException;
import dev.scx.node.Node;
import dev.scx.serialize.ScxSerialize;

import static dev.scx.http.media.string.StringMediaReader.STRING_MEDIA_READER;
import static dev.scx.http.media_type.MediaType.APPLICATION_JSON;
import static dev.scx.http.media_type.MediaType.APPLICATION_XML;

/// NodeMediaReader
/// 此处之所以 先将请求体读取为字符串, 然后解析为 JsonNode.  参考 JsonReader 和 XmlReader
///
/// @author scx567888
/// @version 0.0.1
public final class NodeMediaReader implements MediaReader<Node> {

    public static final NodeMediaReader NODE_MEDIA_READER = new NodeMediaReader();

    private NodeMediaReader() {

    }

    @Override
    public Node read(ByteInput byteInput, ScxHttpHeaders requestHeaders) throws ScxIOException, AlreadyClosedException {
        // 1, 先读取为字符串
        var str = STRING_MEDIA_READER.read(byteInput, requestHeaders);
        // 2, 根据不同 contentType 进行处理
        var contentType = requestHeaders.contentType();
        // 尝试 JSON
        if (APPLICATION_JSON.equalsIgnoreParams(contentType)) {
            try {
                return ScxSerialize.fromJson(str);
            } catch (FormatToNodeException e) {
                // 这里既然客户端已经 指定了 contentType 为 JSON 我们却无法转换 说明 客户端发送的 内容格式可能有误
                // 所以这里 抛出 客户端错误 BadRequestException
                throw new BadRequestException("JSON 格式不正确 !!!", e);
            }
        }
        // 尝试 XML
        if (APPLICATION_XML.equalsIgnoreParams(contentType)) {
            try {
                return ScxSerialize.fromXml(str);
            } catch (FormatToNodeException e) {
                // 这里既然客户端已经 指定了 contentType 为 XML 我们却无法转换 说明 客户端发送的 内容格式可能有误
                // 所以这里 抛出 客户端错误 BadRequestException
                throw new BadRequestException("XML 格式不正确 !!!", e);
            }
        }

        //JSON 和 XML 均不匹配 进行猜测
        try { //先尝试以 JSON 格式进行尝试转换
            return ScxSerialize.fromJson(str);
        } catch (FormatToNodeException exception) {
            try {//再尝试以 XML 的格式进行转换
                return ScxSerialize.fromXml(str);
            } catch (FormatToNodeException e) {
                // JSON 和 XML 均转换失败 直接报错
                // 这里因为客户端没有指定格式 所以不能抛出 BadRequestException 这种客户端错误 而是应该抛出内部错误
                throw new IllegalArgumentException("无法转换为 JsonNode !!! : " + str);
            }
        }
    }

}
