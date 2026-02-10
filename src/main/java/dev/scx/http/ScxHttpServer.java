package dev.scx.http;

import dev.scx.function.Function1Void;
import dev.scx.http.error_handler.ScxHttpServerErrorHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

/// ScxHttpServer
///
/// ### 关于 onError
///
/// 当接收请求或执行用户处理器时发生错误, 应调用 调用错误处理器 (errorHandler) 进行错误处理.
///
///  - 若 错误处理器 在处理过程中 也 抛出异常, 不得再次调用 错误处理器 (避免递归).
///  - 应该将记录该异常, 并进行最小化兜底 (如关闭连接, 若响应尚未提交可尝试写入 500).
///
/// @author scx567888
/// @version 0.0.1
public interface ScxHttpServer {

    ScxHttpServer onRequest(Function1Void<ScxHttpServerRequest, ?> requestHandler);

    ScxHttpServer onError(ScxHttpServerErrorHandler errorHandler);

    void start(SocketAddress localAddress) throws IOException;

    void stop();

    InetSocketAddress localAddress();

    default void start(int port) throws IOException {
        start(new InetSocketAddress(port));
    }

}
