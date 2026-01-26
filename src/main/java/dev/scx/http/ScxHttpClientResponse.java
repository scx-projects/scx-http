package dev.scx.http;

import dev.scx.http.headers.EasyHttpHeadersReader;
import dev.scx.http.peer_info.PeerInfo;
import dev.scx.http.received.ScxHttpMediaReceived;
import dev.scx.http.status_code.ScxHttpStatusCode;
import dev.scx.http.version.HttpVersion;

/// ScxHttpClientResponse
///
/// @author scx567888
/// @version 0.0.1
public interface ScxHttpClientResponse extends ScxHttpMediaReceived, EasyHttpHeadersReader {

    ScxHttpStatusCode statusCode();

    HttpVersion version();

    PeerInfo remotePeer();

    PeerInfo localPeer();

}
