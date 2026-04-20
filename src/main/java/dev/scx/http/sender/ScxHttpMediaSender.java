package dev.scx.http.sender;

import dev.scx.exception.ScxWrappedException;
import dev.scx.http.media.MediaWriter;
import dev.scx.http.media.byte_array.ByteArrayMediaWriter;
import dev.scx.http.media.byte_input.ByteInputMediaWriter;
import dev.scx.http.media.event_stream.ServerEventStream;
import dev.scx.http.media.event_stream.ServerEventStreamMediaWriter;
import dev.scx.http.media.file.FileMediaWriter;
import dev.scx.http.media.form_params.FormParams;
import dev.scx.http.media.form_params.FormParamsMediaWriter;
import dev.scx.http.media.input_stream.InputStreamMediaWriter;
import dev.scx.http.media.multi_part.MultiPart;
import dev.scx.http.media.multi_part.MultiPartMediaWriter;
import dev.scx.http.media.string.StringMediaWriter;
import dev.scx.io.ByteInput;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;

import static dev.scx.http.media.empty.EmptyMediaWriter.EMPTY_MEDIA_WRITER;

/// 相较于 ScxHttpSender, 提供了大量便捷的 media 写入与一些默认的转换能力(装饰器).
///
/// 该接口仅通过 default 方法对 ScxHttpSender 进行功能增强,
/// 不引入新的协议语义, 不改变既有的 ScxHttpSender 模型.
///
/// 使用者无需实现任何额外方法, 直接 implements 即可获得这些能力.
/// 因此这是一次无损的 API 增强, 而非装饰器或包装实现.
///
/// 命名说明:
/// 本类型属于 "HTTP 发送抽象的能力增强(mixin)", 而不是对发送抽象的包装或重构.
/// 因此采用 ScxHttpMediaSender 的命名形式 而不是 ScxMediaHttpSender,
/// 以明确其仍然是 HTTP 领域的 Sender 抽象,
/// 而非一个以 Media 为主语的装饰器类型.
///
/// @author scx567888
/// @version 0.0.1
public interface ScxHttpMediaSender<T> extends ScxHttpSender<T> {

    default T send(MediaWriter mediaWriter) throws IllegalSenderStateException, ScxHttpSendException, ScxWrappedException, ScxHttpReceiveException {
        // 在没有 contentType 的时候 根据 mediaWriter 设置.
        if (headers().contentType() == null) {
            var mediaType = mediaWriter.mediaType();
            if (mediaType != null) {
                headers().contentType(mediaType);
            }
        }
        return send((BodyWriter) mediaWriter);
    }

    //******************** send 操作 *******************

    default T send() throws IllegalSenderStateException, ScxHttpSendException, ScxWrappedException, ScxHttpReceiveException {
        return send(EMPTY_MEDIA_WRITER);
    }

    default T send(ByteInput byteInput) throws IllegalSenderStateException, ScxHttpSendException, ScxWrappedException, ScxHttpReceiveException {
        return send(new ByteInputMediaWriter(byteInput));
    }

    default T send(InputStream inputStream) throws IllegalSenderStateException, ScxHttpSendException, ScxWrappedException, ScxHttpReceiveException {
        return send(new InputStreamMediaWriter(inputStream));
    }

    default T send(byte[] bytes) throws IllegalSenderStateException, ScxHttpSendException, ScxWrappedException, ScxHttpReceiveException {
        return send(new ByteArrayMediaWriter(bytes));
    }

    default T send(String str) throws IllegalSenderStateException, ScxHttpSendException, ScxWrappedException, ScxHttpReceiveException {
        return send(new StringMediaWriter(str));
    }

    default T send(String str, Charset charset) throws IllegalSenderStateException, ScxHttpSendException, ScxWrappedException, ScxHttpReceiveException {
        return send(new StringMediaWriter(str, charset));
    }

    default T send(File file) throws IllegalSenderStateException, ScxHttpSendException, ScxWrappedException, ScxHttpReceiveException {
        return send(new FileMediaWriter(file));
    }

    default T send(File file, long offset, long length) throws IllegalSenderStateException, ScxHttpSendException, ScxWrappedException, ScxHttpReceiveException {
        return send(new FileMediaWriter(file, offset, length));
    }

    default T send(FormParams formParams) throws IllegalSenderStateException, ScxHttpSendException, ScxWrappedException, ScxHttpReceiveException {
        return send(new FormParamsMediaWriter(formParams));
    }

    default T send(MultiPart multiPart) throws IllegalSenderStateException, ScxHttpSendException, ScxWrappedException, ScxHttpReceiveException {
        return send(new MultiPartMediaWriter(multiPart));
    }

    /// 理论上只有 服务器才支持发送这种格式
    default ServerEventStream sendEventStream() throws IllegalSenderStateException, ScxHttpSendException, ScxWrappedException, ScxHttpReceiveException {
        var writer = new ServerEventStreamMediaWriter();
        send(writer);
        return writer.eventStream();
    }

    //******************** 装饰器 操作 *******************

    /// 同层幂等: 对 GzipHttpSender 重复调用 gzip() 不会产生新的包装.
    /// 发送端 gzip 会设置 Content-Encoding: gzip.
    /// 重复调用通常没有意义且可能造成头体语义不一致, 因此不重复包装.
    default GzipHttpSender<T> gzip() {
        // 保证同层幂等
        if (this instanceof GzipHttpSender<T> gzipHttpSender) {
            return gzipHttpSender;
        }
        return new GzipHttpSender<>(this);
    }

}
