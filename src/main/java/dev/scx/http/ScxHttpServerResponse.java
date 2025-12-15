package dev.scx.http;

import dev.scx.http.headers.EasyHttpHeadersWriter;
import dev.scx.http.headers.ScxHttpHeaders;
import dev.scx.http.headers.ScxHttpHeadersWritable;
import dev.scx.http.media.MediaWriter;
import dev.scx.http.sender.HttpSendException;
import dev.scx.http.sender.IllegalSenderStateException;
import dev.scx.http.sender.ScxHttpSender;
import dev.scx.http.status_code.ScxHttpStatusCode;

/// ScxHttpServerResponse
///
/// @author scx567888
/// @version 0.0.1
public interface ScxHttpServerResponse extends ScxHttpSender<Void>, EasyHttpHeadersWriter<ScxHttpServerResponse> {

    ScxHttpServerRequest request();

    ScxHttpStatusCode statusCode();

    @Override
    ScxHttpHeadersWritable headers();

    ScxHttpServerResponse statusCode(ScxHttpStatusCode statusCode);

    ScxHttpServerResponse headers(ScxHttpHeaders headers);

    @Override
    Void send(MediaWriter mediaWriter) throws IllegalSenderStateException, HttpSendException;

    //******************** 简化操作 *******************

    default ScxHttpServerResponse statusCode(int statusCode) {
        return statusCode(ScxHttpStatusCode.of(statusCode));
    }

}
