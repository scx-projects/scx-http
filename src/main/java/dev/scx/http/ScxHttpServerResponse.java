package dev.scx.http;

import dev.scx.http.headers.EasyHttpHeadersWriter;
import dev.scx.http.sender.ScxHttpMediaSender;
import dev.scx.http.status_code.ScxHttpStatusCode;

/// ScxHttpServerResponse
///
/// @author scx567888
/// @version 0.0.1
public interface ScxHttpServerResponse extends ScxHttpMediaSender<Void>, EasyHttpHeadersWriter<ScxHttpServerResponse> {

    ScxHttpServerRequest request();

    ScxHttpStatusCode statusCode();

    ScxHttpServerResponse statusCode(ScxHttpStatusCode statusCode);

    //******************** 简化操作 *******************

    default ScxHttpServerResponse statusCode(int statusCode) {
        return statusCode(ScxHttpStatusCode.of(statusCode));
    }

}
