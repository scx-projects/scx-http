package dev.scx.http.media.input_stream;

import dev.scx.http.media.MediaWriter;
import dev.scx.http.media_type.ScxMediaType;
import dev.scx.io.ByteOutput;
import dev.scx.io.ScxIO;
import dev.scx.io.exception.OutputAlreadyClosedException;
import dev.scx.io.exception.ScxInputException;
import dev.scx.io.exception.ScxOutputException;
import dev.scx.io.supplier.InputStreamByteSupplier;

import java.io.IOException;
import java.io.InputStream;

/// InputStreamMediaWriter
///
/// 默认自动关闭输入流
///
/// @author scx567888
/// @version 0.0.1
public final class InputStreamMediaWriter implements MediaWriter {

    private final InputStreamByteSupplier inputStreamByteSupplier;

    public InputStreamMediaWriter(InputStream inputStream) {
        this.inputStreamByteSupplier = new InputStreamByteSupplier(inputStream);
    }

    @Override
    public ScxMediaType mediaType() {
        return null;
    }

    @Override
    public Long bodyLength() {
        // 这里也可以使用 通过 instanceof 之类的方法来获取 inputStream 的真实字节数,但这里为了简化逻辑 统一返回 null.
        return null;
    }

    @Override
    public void write(ByteOutput byteOutput) throws ScxOutputException, OutputAlreadyClosedException, IOException {
        try (byteOutput; inputStreamByteSupplier) {
            ScxIO.transferToAll(inputStreamByteSupplier, byteOutput);
        } catch (ScxInputException e) {
            // 这里必然是 IOException 强转安全.
            throw (IOException) e.getCause();
        }
    }

}
