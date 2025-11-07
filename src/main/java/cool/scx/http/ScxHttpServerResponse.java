package cool.scx.http;

import cool.scx.http.headers.ScxHttpHeaders;
import cool.scx.http.headers.ScxHttpHeadersWritable;
import cool.scx.http.headers.ScxHttpHeadersWriteHelper;
import cool.scx.http.media.MediaWriter;
import cool.scx.http.sender.BodyAlreadySentException;
import cool.scx.http.sender.ScxHttpSender;
import cool.scx.http.status_code.ScxHttpStatusCode;

/// ScxHttpServerResponse
///
/// @author scx567888
/// @version 0.0.1
public interface ScxHttpServerResponse extends ScxHttpSender<Void>, ScxHttpHeadersWriteHelper<ScxHttpServerResponse> {

    ScxHttpServerRequest request();

    ScxHttpStatusCode statusCode();

    @Override
    ScxHttpHeadersWritable headers();

    ScxHttpServerResponse statusCode(ScxHttpStatusCode statusCode);

    ScxHttpServerResponse headers(ScxHttpHeaders headers);

    boolean isSent();

    @Override
    Void send(MediaWriter mediaWriter) throws BodyAlreadySentException;

    //******************** 简化操作 *******************

    default ScxHttpServerResponse statusCode(int code) {
        return statusCode(ScxHttpStatusCode.of(code));
    }

}
