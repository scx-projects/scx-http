package dev.scx.http.received;

import dev.scx.http.headers.ScxHttpHeaders;
import dev.scx.io.ByteInput;
import dev.scx.io.supplier.CacheByteSupplier;
import dev.scx.io.supplier.ClosePolicyByteSupplier;

import static dev.scx.io.ScxIO.createByteInput;
import static dev.scx.io.supplier.ClosePolicyByteSupplier.singleClose;

/// CacheHttpReceived
///
/// @author scx567888
/// @version 0.0.1
public final class CacheHttpReceived implements ScxHttpMediaReceived {

    private final ScxHttpReceived httpReceived;
    private final CacheByteSupplier cacheByteSupplier;
    private final ClosePolicyByteSupplier singleCloseByteSupplier;

    public CacheHttpReceived(ScxHttpReceived httpReceived) {
        this.httpReceived = httpReceived;
        this.cacheByteSupplier = new CacheByteSupplier(httpReceived.body());
        // 这里需要 结合 singleCloseByteSupplier 使用防止 多次关闭.
        this.singleCloseByteSupplier = singleClose(cacheByteSupplier);
    }

    @Override
    public ScxHttpHeaders headers() {
        return httpReceived.headers();
    }

    @Override
    public Long bodyLength() {
        // 沿用原始长度信息
        return httpReceived.bodyLength();
    }

    @Override
    public ByteInput body() {
        // 重置读取点 + 每次都返回一个新的 (不过这也意味着不支持并发调用).
        cacheByteSupplier.reset();
        return createByteInput(singleCloseByteSupplier);
    }

}
