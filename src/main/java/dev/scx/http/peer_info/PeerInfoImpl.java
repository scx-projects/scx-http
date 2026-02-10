package dev.scx.http.peer_info;

import java.net.SocketAddress;
import java.security.Principal;
import java.security.cert.Certificate;

/// PeerInfoImpl (允许继承以便拓展功能)
///
/// @author scx567888
/// @version 0.0.1
final class PeerInfoImpl implements PeerInfoWritable {

    private final SocketAddress socketAddress;
    private Principal principal;
    private Certificate[] certificates;

    public PeerInfoImpl(SocketAddress socketAddress) {
        this.socketAddress = socketAddress;
        this.principal = null;
        this.certificates = null;
    }

    @Override
    public SocketAddress address() {
        return socketAddress;
    }

    @Override
    public Principal tlsPrincipal() {
        return principal;
    }

    @Override
    public Certificate[] tlsCertificates() {
        return certificates;
    }

    @Override
    public PeerInfoWritable tlsPrincipal(Principal principal) {
        this.principal = principal;
        return this;
    }

    @Override
    public PeerInfoWritable tlsCertificates(Certificate[] certificates) {
        this.certificates = certificates;
        return this;
    }

}
