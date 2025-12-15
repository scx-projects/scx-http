package dev.scx.http;

import dev.scx.http.headers.EasyHttpHeadersWriter;
import dev.scx.http.headers.ScxHttpHeaders;
import dev.scx.http.headers.ScxHttpHeadersWritable;
import dev.scx.http.media.MediaWriter;
import dev.scx.http.method.ScxHttpMethod;
import dev.scx.http.sender.HttpSendException;
import dev.scx.http.sender.IllegalSenderStateException;
import dev.scx.http.sender.ScxHttpSender;
import dev.scx.http.uri.ScxURI;
import dev.scx.http.uri.ScxURIWritable;

/// ScxHttpClientRequest
///
/// 和 ScxHttpClientResponse, ScxHttpServerRequest, ScxHttpServerResponse 有所不同.
///
/// ScxHttpClientRequest 并不表示一个已经存在的请求或响应对象, 而是更接近 Builder.
///
/// @author scx567888
/// @version 0.0.1
public interface ScxHttpClientRequest extends ScxHttpSender<ScxHttpClientResponse>, EasyHttpHeadersWriter<ScxHttpClientRequest> {

    ScxHttpMethod method();

    ScxURIWritable uri();

    @Override
    ScxHttpHeadersWritable headers();

    ScxHttpClientRequest method(ScxHttpMethod method);

    ScxHttpClientRequest uri(ScxURI uri);

    ScxHttpClientRequest headers(ScxHttpHeaders headers);

    @Override
    ScxHttpClientResponse send(MediaWriter mediaWriter) throws IllegalSenderStateException, HttpSendException;

    //******************** 简化操作 *******************

    default ScxHttpClientRequest method(String method) {
        return method(ScxHttpMethod.of(method));
    }

    default ScxHttpClientRequest uri(String uri) {
        return uri(ScxURI.of(uri));
    }

}
