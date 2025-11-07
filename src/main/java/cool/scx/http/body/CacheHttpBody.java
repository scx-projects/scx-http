package cool.scx.http.body;

import cool.scx.http.headers.ScxHttpHeaders;
import cool.scx.http.media.MediaReader;
import cool.scx.io.ByteInput;
import cool.scx.io.DefaultByteInput;
import cool.scx.io.exception.AlreadyClosedException;
import cool.scx.io.exception.ScxIOException;
import cool.scx.io.supplier.CacheByteSupplier;
import cool.scx.io.supplier.ClosePolicyByteSupplier;

import static cool.scx.io.supplier.ClosePolicyByteSupplier.singleClose;

/// CacheHttpBody
///
/// @author scx567888
/// @version 0.0.1
public final class CacheHttpBody implements ScxHttpBody {

    private final ByteInput byteInput;
    private final ScxHttpHeaders headers;
    private final CacheByteSupplier cacheByteSupplier;
    private final ClosePolicyByteSupplier singleCloseByteSupplier;

    public CacheHttpBody(ByteInput byteInput, ScxHttpHeaders headers) {
        this.byteInput = byteInput;
        this.headers = headers;
        this.cacheByteSupplier = new CacheByteSupplier(byteInput);
        // 这里需要 结合 singleCloseByteSupplier 使用防止 多次关闭.
        this.singleCloseByteSupplier = singleClose(cacheByteSupplier);
    }

    @Override
    public ByteInput byteInput() {
        cacheByteSupplier.reset();
        return new DefaultByteInput(singleCloseByteSupplier);
    }

    @Override
    public <T> T as(MediaReader<T> mediaReader) throws BodyAlreadyConsumedException, BodyReadException {
        try {
            return mediaReader.read(byteInput(), headers);
        } catch (ScxIOException e) {
            throw new BodyReadException(e);
        } catch (AlreadyClosedException e) {
            throw new BodyAlreadyConsumedException();
        }
    }

    @Override
    public CacheHttpBody asCacheBody() {
        return this;
    }

}
