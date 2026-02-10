package dev.scx.http.media;

import dev.scx.http.media_type.ScxMediaType;
import dev.scx.io.ByteInput;

/// 媒体 读取器 一般用于 [dev.scx.http.received.ScxHttpMediaReceived]
///
/// @param <T>
/// @author scx567888
/// @version 0.0.1
public interface MediaReader<T, X extends Throwable> {

    /// 读取内容
    /// 约定:
    /// - 本方法会消费 ByteInput, 并在读取完成后关闭它.
    /// - mediaType 可能为 null, 表示未知或缺失 Content-Type.
    ///
    /// @throws X read 读取过程中的发生的任何异常.
    T read(ByteInput byteInput, ScxMediaType mediaType) throws X;

}
