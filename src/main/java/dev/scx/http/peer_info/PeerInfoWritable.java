package dev.scx.http.peer_info;

import java.security.Principal;
import java.security.cert.Certificate;

/// PeerInfoWritable
///
/// @author scx567888
public interface PeerInfoWritable extends PeerInfo {

    PeerInfoWritable tlsPrincipal(Principal principal);

    PeerInfoWritable tlsCertificates(Certificate[] certificates);

}
