package dev.scx.http.received;

import dev.scx.http.media.MediaReader;
import dev.scx.http.media.event_stream.ClientEventStream;
import dev.scx.http.media.file.FileMediaReader;
import dev.scx.http.media.form_params.FormParams;
import dev.scx.http.media.multi_part.MultiPartStream;
import dev.scx.http.media.string.StringMediaReader;
import dev.scx.io.exception.AlreadyClosedException;
import dev.scx.io.exception.ScxIOException;

import java.io.File;
import java.nio.charset.Charset;

import static dev.scx.http.media.byte_array.ByteArrayMediaReader.BYTE_ARRAY_MEDIA_READER;
import static dev.scx.http.media.event_stream.ClientEventStreamMediaReader.CLIENT_EVENT_STREAM_MEDIA_READER;
import static dev.scx.http.media.form_params.FormParamsMediaReader.FORM_PARAMS_MEDIA_READER;
import static dev.scx.http.media.multi_part.MultiPartStreamMediaReader.MULTI_PART_STREAM_MEDIA_READER;
import static dev.scx.http.media.string.StringMediaReader.STRING_MEDIA_READER;

/// 相较于 ScxHttpReceived, 提供了大量便捷的 media 读取与一些默认的转换能力(装饰器).
///
/// 该接口仅通过 default 方法对 ScxHttpReceived 进行功能增强,
/// 不引入新的协议语义, 不改变既有的接收事实模型.
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

    default <T> T as(MediaReader<T> mediaReader) throws ScxIOException, AlreadyClosedException {
        return mediaReader.read(body(), headers().contentType());
    }

    //******************** as 操作 *******************

    default byte[] asBytes() throws ScxIOException, AlreadyClosedException {
        return as(BYTE_ARRAY_MEDIA_READER);
    }

    default String asString() throws ScxIOException, AlreadyClosedException {
        return as(STRING_MEDIA_READER);
    }

    default String asString(Charset charset) throws ScxIOException, AlreadyClosedException {
        return as(new StringMediaReader(charset));
    }

    default File asFile(File file) throws ScxIOException, AlreadyClosedException {
        return as(new FileMediaReader(file));
    }

    default FormParams asFormParams() throws ScxIOException, AlreadyClosedException {
        return as(FORM_PARAMS_MEDIA_READER);
    }

    default MultiPartStream asMultiPart() throws ScxIOException, AlreadyClosedException {
        return as(MULTI_PART_STREAM_MEDIA_READER);
    }

    default ClientEventStream asEventStream() throws ScxIOException, AlreadyClosedException {
        return as(CLIENT_EVENT_STREAM_MEDIA_READER);
    }

    //******************** 装饰器 操作 *******************

    default GzipHttpReceived gzip() throws ScxIOException, AlreadyClosedException {
        return new GzipHttpReceived(this);
    }

    default CacheHttpReceived cache() throws ScxIOException, AlreadyClosedException {
        return new CacheHttpReceived(this);
    }

}
