package dev.scx.http.received;

import dev.scx.exception.ScxWrappedException;
import dev.scx.http.exception.UnsupportedMediaTypeException;
import dev.scx.http.media.MediaReader;
import dev.scx.http.media.event_stream.ClientEventStream;
import dev.scx.http.media.file.FileMediaReader;
import dev.scx.http.media.form_params.FormParams;
import dev.scx.http.media.multi_part.IllegalBoundaryException;
import dev.scx.http.media.multi_part.MultiPartStream;
import dev.scx.http.media.string.StringMediaReader;
import dev.scx.io.exception.InputAlreadyClosedException;
import dev.scx.io.exception.ScxInputException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import static dev.scx.http.headers.content_encoding.ContentEncoding.GZIP;
import static dev.scx.http.media.byte_array.ByteArrayMediaReader.BYTE_ARRAY_MEDIA_READER;
import static dev.scx.http.media.event_stream.ClientEventStreamMediaReader.CLIENT_EVENT_STREAM_MEDIA_READER;
import static dev.scx.http.media.form_params.FormParamsMediaReader.FORM_PARAMS_MEDIA_READER;
import static dev.scx.http.media.multi_part.MultiPartStreamMediaReader.MULTI_PART_STREAM_MEDIA_READER;
import static dev.scx.http.media.string.StringMediaReader.STRING_MEDIA_READER;

/// 相较于 ScxHttpReceived, 提供了大量便捷的 media 读取与一些默认的转换能力(装饰器).
///
/// 该接口仅通过 default 方法对 ScxHttpReceived 进行功能增强,
/// 不引入新的协议语义, 不改变既有的 ScxHttpReceived 模型.
///
/// 使用者无需实现任何额外方法, 直接 implements 即可获得这些能力.
/// 因此这是一次无损的 API 增强, 而非装饰器或包装实现.
///
/// 命名说明:
/// 本类型属于 "HTTP 接收结果的能力增强(mixin)", 而不是对接收结果的包装或重构.
/// 因此采用 ScxHttpMediaReceived 的命名形式 而不是 ScxMediaHttpReceived,
/// 以明确其仍然是 HTTP 领域的 Received 抽象,
/// 而非一个以 Media 为主语的装饰器类型.
///
/// @author scx567888
/// @version 0.0.1
public interface ScxHttpMediaReceived extends ScxHttpReceived {

    /// - X (泛型异常) 完全等价于 MediaReader.read(...) 的异常语义, 本方法对异常做透明转发.
    /// - 此处不使用 [ScxWrappedException], 因为 as(...) 仅为透明转发, 不存在需要进行异常域隔离的宿主逻辑.
    default <T, X extends Throwable> T as(MediaReader<T, X> mediaReader) throws X {
        return mediaReader.read(body(), headers().contentType());
    }

    //******************** as 操作 *******************

    default byte[] asBytes() throws ScxInputException, InputAlreadyClosedException {
        return as(BYTE_ARRAY_MEDIA_READER);
    }

    default String asString() throws ScxInputException, InputAlreadyClosedException {
        return as(STRING_MEDIA_READER);
    }

    default String asString(Charset charset) throws ScxInputException, InputAlreadyClosedException {
        return as(new StringMediaReader(charset));
    }

    default File asFile(File file) throws ScxInputException, InputAlreadyClosedException, IOException {
        return as(new FileMediaReader(file));
    }

    default FormParams asFormParams() throws ScxInputException, InputAlreadyClosedException {
        return as(FORM_PARAMS_MEDIA_READER);
    }

    default MultiPartStream asMultiPart() throws IllegalArgumentException, IllegalBoundaryException {
        return as(MULTI_PART_STREAM_MEDIA_READER);
    }

    default ClientEventStream asEventStream() {
        return as(CLIENT_EVENT_STREAM_MEDIA_READER);
    }

    //******************** 装饰器 操作 *******************

    /// 强制按 gzip 格式解码 body.
    ///
    /// 同层幂等:
    /// - 若当前已是 GzipHttpReceived, 再次调用 gzip() 将直接返回自身.
    /// - 不会引入新的 gzip 解码层.
    ///
    /// 该方法不校验 Content-Encoding,
    /// 表达的是“调用者已确认 body 为 gzip 格式”的确定性语义.
    default GzipHttpReceived gzip() throws ScxInputException {
        // 保证同层幂等
        if (this instanceof GzipHttpReceived gzipHttpReceived) {
            return gzipHttpReceived;
        }
        return new GzipHttpReceived(this);
    }

    /// 同层幂等: 对 CacheHttpReceived 重复调用 cache() 不会产生新的缓存视图.
    /// 但在其他装饰器之后再次调用 cache() , 如 received.cache().gzip().cache() 仍会缓存新的阶段数据.
    default CacheHttpReceived cache() {
        // 保证同层幂等
        if (this instanceof CacheHttpReceived cacheHttpReceived) {
            return cacheHttpReceived;
        }
        return new CacheHttpReceived(this);
    }

    /// 自动根据 Content-Encoding 进行解码.
    /// 与 gzip() 不同, 注意 此方法 应当是幂等的, 避免重复解码.
    default ScxHttpMediaReceived autoDecode() throws ScxInputException, UnsupportedMediaTypeException {
        // 保证幂等: 如果当前已经是某种自动解码后的视图, 则直接返回.
        if (this instanceof GzipHttpReceived) {
            return this;
        }

        var contentEncoding = this.headers().contentEncoding();
        // 没有 contentEncoding, 直接返回 this
        if (contentEncoding == null) {
            return this;
        }
        // 目前只支持 gzip
        if (contentEncoding == GZIP) {
            // 这里使用 new 而不是 直接调用 gzip(), 因为 策略层应该直接构造具体解码视图, 避免依赖 gzip() 的组合规则.
            return new GzipHttpReceived(this);
        } else {// 否则我们不支持这种类型 抛出异常
            throw new UnsupportedMediaTypeException("Unsupported Content-Encoding: " + contentEncoding);
        }
    }

}
