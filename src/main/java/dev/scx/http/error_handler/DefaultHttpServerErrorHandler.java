package dev.scx.http.error_handler;

import dev.scx.http.ScxHttpServerRequest;
import dev.scx.http.exception.InternalServerErrorException;
import dev.scx.http.exception.ScxHttpException;
import dev.scx.http.headers.accept.Accept;
import dev.scx.http.media_type.ScxMediaType;
import dev.scx.http.status_code.ScxHttpStatusCode;
import dev.scx.serialize.ScxSerialize;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.System.Logger;
import java.util.Map;

import static dev.scx.http.error_handler.ErrorPhaseHelper.getErrorPhaseStr;
import static dev.scx.http.media_type.MediaType.APPLICATION_JSON;
import static dev.scx.http.media_type.MediaType.TEXT_HTML;
import static dev.scx.http.status_code.ScxHttpStatusCodeHelper.getReasonPhrase;
import static java.lang.System.Logger.Level.ERROR;
import static java.lang.System.getLogger;
import static java.nio.charset.StandardCharsets.UTF_8;

/// 默认错误处理器
///
/// @author scx567888
/// @version 0.0.1
public class DefaultHttpServerErrorHandler implements ScxHttpServerErrorHandler {

    public static final DefaultHttpServerErrorHandler DEFAULT_HTTP_SERVER_ERROR_HANDLER = new DefaultHttpServerErrorHandler(true);

    private static final Logger LOGGER = getLogger(DefaultHttpServerErrorHandler.class.getName());

    /// 默认 html 模板
    private static final String HTML_TEMPLATE = """
        <!DOCTYPE html>
        <html lang="en">
        <head>
            <meta charset="UTF-8">
            <title>%s</title>
        </head>
        <body>
            <h1>%s - %s</h1>
            <hr>
            <pre>%s</pre>
        </body>
        </html>
        """;

    private final boolean useDevelopmentErrorPage;

    public DefaultHttpServerErrorHandler(boolean useDevelopmentErrorPage) {
        this.useDevelopmentErrorPage = useDevelopmentErrorPage;
    }

    /// 获取 jdk 内部默认实现的堆栈跟踪字符串
    public static String getStackTraceString(Throwable throwable) {
        var stringWriter = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.getBuffer().toString();
    }

    public static void sendToClient(ScxHttpStatusCode statusCode, String info, ScxHttpServerRequest request) {
        //防止页面出现 null 这种奇怪的情况
        if (info == null) {
            info = "";
        }
        var reasonPhrase = getReasonPhrase(statusCode, "unknown");
        Accept accepts = null;
        try {
            accepts = request.headers().accept();
        } catch (Exception _) {

        }
        //根据 accept 返回不同的错误信息 只有明确包含的时候才返回 html
        if (accepts != null && accepts.contains(TEXT_HTML)) {
            var htmlStr = String.format(HTML_TEMPLATE, reasonPhrase, statusCode.value(), reasonPhrase, info);
            request.response()
                .contentType(ScxMediaType.of(TEXT_HTML).charset(UTF_8))
                .statusCode(statusCode)
                .send(htmlStr);
        } else {
            var jsonStr = ScxSerialize.toJson(Map.of("statusCode", statusCode.value(), "title", reasonPhrase, "info", info));
            request.response()
                .contentType(ScxMediaType.of(APPLICATION_JSON).charset(UTF_8))
                .statusCode(statusCode)
                .send(jsonStr);
        }
    }

    public void handleScxHttpException(ScxHttpException scxHttpException, ScxHttpServerRequest request) {
        String info = null;
        //1, 这里根据是否开启了开发人员错误页面 进行相应的返回
        if (useDevelopmentErrorPage) {
            var cause = ((Throwable) scxHttpException).getCause();
            if (cause == null) {
                info = ((Throwable) scxHttpException).getMessage();
            } else {
                info = getStackTraceString(cause);
            }
        }
        sendToClient(scxHttpException.statusCode(), info, request);
    }

    @Override
    public void accept(Throwable throwable, ScxHttpServerRequest request, ErrorPhase errorPhase) {
        // Http 异常无需打印
        if (throwable instanceof ScxHttpException h) {
            this.handleScxHttpException(h, request);
        } else {
            // 其余异常包装为 500 异常, 同时需要打印
            LOGGER.log(ERROR, getErrorPhaseStr(errorPhase) + " 发生异常 !!!", throwable);
            this.handleScxHttpException(new InternalServerErrorException(throwable), request);
        }
    }

}
