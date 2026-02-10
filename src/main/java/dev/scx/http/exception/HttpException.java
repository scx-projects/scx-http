package dev.scx.http.exception;

import dev.scx.http.error_handler.ScxHttpServerErrorHandler;
import dev.scx.http.status_code.ScxHttpStatusCode;

///  HttpException
///
///  这是 [ScxHttpException] 的一个便捷默认实现, 用于快速抛出带状态码的异常.
///
///  核心说明:
///  - 本类只是辅助类, 目的是简化开发者使用接口的流程.
///  - 真正的核心是 [ScxHttpException] 接口, 任何异常只要实现该接口 就可以被 [ScxHttpServerErrorHandler] 识别为 HTTP 响应异常.
///
///  注意:
///  - 使用此类可直接指定 HTTP 状态码和可选的错误信息.
///  - 对于更复杂或同时具有多种语义的异常, 仍然建议自定义异常类并实现接口.
///
/// ### 使用示例:
/// ```java
/// throw new HttpException(HttpStatusCode.NOT_FOUND, "Requested resource not found");
/// ```
///
/// @author scx567888
/// @version 0.0.1
public class HttpException extends RuntimeException implements ScxHttpException {

    private final ScxHttpStatusCode statusCode;

    public HttpException(ScxHttpStatusCode statusCode) {
        super();
        this.statusCode = statusCode;
    }

    public HttpException(ScxHttpStatusCode statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public HttpException(ScxHttpStatusCode statusCode, Throwable cause) {
        super(cause);
        this.statusCode = statusCode;
    }

    public HttpException(ScxHttpStatusCode statusCode, String message, Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
    }

    @Override
    public final ScxHttpStatusCode statusCode() {
        return this.statusCode;
    }

}
