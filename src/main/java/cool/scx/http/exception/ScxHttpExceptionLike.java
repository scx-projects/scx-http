package cool.scx.http.exception;

import cool.scx.http.status_code.ScxHttpStatusCode;

/// 有时候一些异常因为某些限制, 无法直接继承自 ScxHttpException,
/// 但希望同样可以当作 一个 ScxHttpException 来看待 以便被 比如 统一的错误处理器所处理.
/// 可以实现此接口.
///
/// - 注意: 虽然接口无法限制实现是否真是一个异常, 但非异常类继承实际上可能不会有效果 (这一般取决于 统一错误处理器).
///
/// @author scx567888
/// @version 0.0.1
public interface ScxHttpExceptionLike {

    ScxHttpStatusCode statusCode();

}
