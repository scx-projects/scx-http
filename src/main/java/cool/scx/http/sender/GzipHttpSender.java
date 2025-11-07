package cool.scx.http.sender;

import cool.scx.http.media.MediaWriter;
import cool.scx.http.media.gzip.GzipMediaWriter;

/// GzipHttpSender
///
/// @author scx567888
/// @version 0.0.1
public final class GzipHttpSender<T> implements ScxHttpSender<T> {

    private final ScxHttpSender<T> sender;

    public GzipHttpSender(ScxHttpSender<T> sender) {
        this.sender = sender;
    }

    public T send(MediaWriter mediaWriter) {
        return this.sender.send(new GzipMediaWriter(mediaWriter));
    }

    @Override
    public ScxHttpSender<T> sendGzip() {
        return this;
    }

}
