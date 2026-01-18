package dev.scx.http;

import dev.scx.function.Function1Void;
import dev.scx.http.error_handler.ScxHttpServerErrorHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

/// ScxHttpServer
///
/// @author scx567888
/// @version 0.0.1
public interface ScxHttpServer {

    ScxHttpServer onRequest(Function1Void<ScxHttpServerRequest, ?> requestHandler);

    /// 只在响应可能可用时触发, 若远端断开连接, 网络中断 或 响应中途发生异常 等 则不会触发
    ScxHttpServer onError(ScxHttpServerErrorHandler errorHandler);

    void start(SocketAddress localAddress) throws IOException;

    void stop();

    InetSocketAddress localAddress();

    default void start(int port) throws IOException {
        start(new InetSocketAddress(port));
    }

}
