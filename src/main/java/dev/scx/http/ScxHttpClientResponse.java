package dev.scx.http;

import dev.scx.http.body.ScxHttpBody;
import dev.scx.http.headers.EasyHttpHeadersReader;
import dev.scx.http.headers.ScxHttpHeaders;
import dev.scx.http.status_code.ScxHttpStatusCode;
import dev.scx.http.version.HttpVersion;

/// ScxHttpClientResponse
///
/// @author scx567888
/// @version 0.0.1
public interface ScxHttpClientResponse extends EasyHttpHeadersReader {

    ScxHttpStatusCode statusCode();

    HttpVersion version();

    @Override
    ScxHttpHeaders headers();

    ScxHttpBody body();

}
