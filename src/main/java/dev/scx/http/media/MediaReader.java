package dev.scx.http.media;

import dev.scx.http.headers.ScxHttpHeaders;
import dev.scx.io.ByteInput;
import dev.scx.io.exception.AlreadyClosedException;
import dev.scx.io.exception.ScxIOException;

/// 读取器 可用于 ServerRequest 和 ClientResponse
///
/// @param <T>
/// @author scx567888
/// @version 0.0.1
public interface MediaReader<T> {

    /// 读取内容
    ///
    /// @param byteInput      输入流
    /// @param requestHeaders 请求头 (在客户端状态下是 responseHeaders)
    T read(ByteInput byteInput, ScxHttpHeaders requestHeaders) throws ScxIOException, AlreadyClosedException;

}
