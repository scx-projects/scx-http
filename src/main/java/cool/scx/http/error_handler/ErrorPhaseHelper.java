package cool.scx.http.error_handler;

/// ErrorPhaseHelper
///
/// @author scx567888
/// @version 0.0.1
public final class ErrorPhaseHelper {

    public static String getErrorPhaseStr(ErrorPhase errorPhase) {
        return switch (errorPhase) {
            case SYSTEM -> "系统处理器";
            case USER -> "用户处理器";
        };
    }

}
