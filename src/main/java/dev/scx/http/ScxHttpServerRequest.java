package dev.scx.http;

import dev.scx.http.body.ScxHttpBody;
import dev.scx.http.headers.EasyHttpHeadersReader;
import dev.scx.http.headers.ScxHttpHeaders;
import dev.scx.http.method.ScxHttpMethod;
import dev.scx.http.parameters.Parameters;
import dev.scx.http.peer_info.PeerInfo;
import dev.scx.http.uri.ScxURI;
import dev.scx.http.version.HttpVersion;

/// ScxHttpServerRequest
///
/// @author scx567888
/// @version 0.0.1
public interface ScxHttpServerRequest extends EasyHttpHeadersReader {

    ScxHttpServerResponse response();

    ScxHttpMethod method();

    ScxURI uri();

    HttpVersion version();

    @Override
    ScxHttpHeaders headers();

    ScxHttpBody body();

    PeerInfo remotePeer();

    PeerInfo localPeer();

    //******************** 简化 URI 操作 *******************

    default String path() {
        return uri().path();
    }

    default Parameters<String, String> query() {
        return uri().query();
    }

    default String getQuery(String name) {
        return uri().getQuery(name);
    }

}
