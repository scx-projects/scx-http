package dev.scx.http.media;

import dev.scx.http.media_type.ScxMediaType;
import dev.scx.io.ByteInput;
import dev.scx.io.exception.AlreadyClosedException;
import dev.scx.io.exception.ScxIOException;

/// 媒体 读取器 一般用于 [dev.scx.http.received.ScxHttpMediaReceived]
///
/// @param <T>
/// @author scx567888
/// @version 0.0.1
public interface MediaReader<T> {

    /// 读取内容
    ///
    /// @param byteInput 输入流
    /// @param mediaType 媒体类型
    T read(ByteInput byteInput, ScxMediaType mediaType) throws ScxIOException, AlreadyClosedException;

}
