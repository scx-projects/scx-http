package dev.scx.http.peer_info;

import java.net.SocketAddress;
import java.security.Principal;
import java.security.cert.Certificate;

/// PeerInfo
///
/// @author scx567888
/// @version 0.0.1
public interface PeerInfo {

    static PeerInfoWritable of(SocketAddress socketAddress) {
        return new PeerInfoImpl(socketAddress);
    }

    SocketAddress address();

    /// 若连接未使用 TLS 或对端身份不可验证, 返回 null
    Principal tlsPrincipal();

    /// 若连接未使用 TLS 或对端身份不可验证, 返回 null
    Certificate[] tlsCertificates();

}
