package dev.scx.http.body;

import dev.scx.http.headers.ScxHttpHeaders;
import dev.scx.http.media.MediaReader;
import dev.scx.io.ByteInput;
import dev.scx.io.exception.AlreadyClosedException;
import dev.scx.io.exception.ScxIOException;
import dev.scx.io.supplier.CacheByteSupplier;
import dev.scx.io.supplier.ClosePolicyByteSupplier;

import static dev.scx.io.ScxIO.createByteInput;
import static dev.scx.io.supplier.ClosePolicyByteSupplier.singleClose;

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
        return createByteInput(singleCloseByteSupplier);
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
