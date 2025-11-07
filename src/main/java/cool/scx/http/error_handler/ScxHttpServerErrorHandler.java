package cool.scx.http.error_handler;

import cool.scx.http.ScxHttpServerRequest;

/// HTTP 服务器 错误处理器
///
/// @author scx567888
/// @version 0.0.1
public interface ScxHttpServerErrorHandler {

    void accept(Throwable throwable, ScxHttpServerRequest request, ErrorPhase errorPhase);

}
