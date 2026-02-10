package dev.scx.http.exception;

import dev.scx.http.error_handler.ScxHttpServerErrorHandler;
import dev.scx.http.status_code.ScxHttpStatusCode;

/// ScxHttpException (标记接口) : 供 [ScxHttpServerErrorHandler] 识别为响应异常的类型 .
///
/// 标记一个异常类型, 可被 [ScxHttpServerErrorHandler] 识别并生成对应的 HTTP 响应.
/// 异常不必继承特定基类, 只需实现该接口即可成为 HTTP 响应异常 (解决 Java 单继承限制).
///
/// ### 注意:
///
///   - 200 系列状态码 (表示请求成功) 或其他非异常状态码通常不应使用此类来表示, 因为它们不属于异常的范畴.
///   - 虽然接口无法限制实现是否真的是一个异常, 但非异常类实现实际上可能不会有效果 (这一般取决于 [ScxHttpServerErrorHandler]).
///
/// ### 适用场景:
///
///   - 标记自定义异常, 使 [ScxHttpServerErrorHandler] 能够据此生成标准 HTTP 响应.
///   - 与 [ScxHttpStatusCode] 配合使用, 快速指定响应状态码.
///
/// ### 使用示例:
///
/// ```java
/// public class NotFoundException extends RuntimeException implements ScxHttpException {
///
///     @Override
///     public ScxHttpStatusCode statusCode() {
///         return HttpStatusCode.NOT_FOUND;
///     }
///
/// }
/// ```
///
/// @author scx567888
/// @version 0.0.1
public interface ScxHttpException {

    ScxHttpStatusCode statusCode();

}
