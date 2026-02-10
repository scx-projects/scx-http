package dev.scx.http.error_handler;

import dev.scx.http.ScxHttpServerRequest;
import dev.scx.http.exception.ScxHttpException;

/// HTTP 服务器 错误处理器.
///
/// 实现必须识别 [ScxHttpException]:
///
/// - 若 `throwable instanceof ScxHttpException`, 则应使用其 [ScxHttpException#statusCode()] 生成 HTTP 响应.
/// - 否则应 生成 500 响应.
///
/// 允许抛出异常:
/// - handle(...) 的目标是 "尽力生成并发送错误响应".
/// - 仅当无法生成/发送响应(例如底层 IO 失败, 连接已断, 响应已提交)时, 才应抛出异常.
///
/// 注意:
/// 虽然接口允许抛出异常, 但抛出异常应被视为错误处理器的最后退路, 而非常规控制路径.
/// 若可以生成并发送响应, 实现不应通过抛异常来逃避错误处理责任.
///
/// @author scx567888
/// @version 0.0.1
public interface ScxHttpServerErrorHandler {

    void handle(Throwable throwable, ScxHttpServerRequest request, ErrorPhase errorPhase) throws Throwable;

}
