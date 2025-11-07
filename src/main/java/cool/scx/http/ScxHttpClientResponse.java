package cool.scx.http;

import cool.scx.http.body.ScxHttpBody;
import cool.scx.http.headers.ScxHttpHeaders;
import cool.scx.http.headers.ScxHttpHeadersReadHelper;
import cool.scx.http.status_code.ScxHttpStatusCode;

/// ScxHttpClientResponse
///
/// @author scx567888
/// @version 0.0.1
public interface ScxHttpClientResponse extends ScxHttpHeadersReadHelper {

    ScxHttpStatusCode statusCode();

    @Override
    ScxHttpHeaders headers();

    ScxHttpBody body();

}
