package dev.scx.http.error_handler;

import java.io.PrintWriter;
import java.io.StringWriter;

/// ErrorPhaseHelper
///
/// @author scx567888
/// @version 0.0.1
public final class ErrorHandlerHelper {

    public static String getErrorPhaseStr(ErrorPhase errorPhase) {
        return switch (errorPhase) {
            case SYSTEM -> "系统处理器";
            case USER -> "用户处理器";
        };
    }

    /// 获取 jdk 内部默认实现的堆栈跟踪字符串
    public static String getStackTraceString(Throwable throwable) {
        var stringWriter = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.getBuffer().toString();
    }

}
