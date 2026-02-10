package dev.scx.http.received;

import dev.scx.http.headers.ScxHttpHeaders;
import dev.scx.io.ByteInput;
import dev.scx.io.ScxIO;
import dev.scx.io.exception.ScxInputException;

/// GzipHttpReceived
///
/// @author scx567888
/// @version 0.0.1
public final class GzipHttpReceived implements ScxHttpMediaReceived {

    private final ScxHttpReceived httpReceived;
    private final ByteInput gzipBody;

    public GzipHttpReceived(ScxHttpReceived httpReceived) throws ScxInputException {
        this.httpReceived = httpReceived;
        this.gzipBody = ScxIO.gzipByteInput(httpReceived.body());
    }

    @Override
    public ScxHttpHeaders headers() {
        return httpReceived.headers();
    }

    @Override
    public Long bodyLength() {
        // 解压后的 body 总长度通常无法预先确定, 返回 null
        return null;
    }

    @Override
    public ByteInput body() {
        return gzipBody;
    }

}
