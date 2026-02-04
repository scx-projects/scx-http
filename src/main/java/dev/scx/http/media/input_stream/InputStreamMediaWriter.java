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
/// 设计思路和 ByteInputMediaWriter 保持一致
///
/// @author scx567888
/// @version 0.0.1
public final class InputStreamMediaWriter implements MediaWriter {

    private final InputStreamByteSupplier inputStreamByteSupplier;
    private final boolean autoClose;

    public InputStreamMediaWriter(InputStream inputStream) {
        // 直接传输的时候 一般表示 整个输入流已经被用尽了 所以这里我们默认自动关闭输入流.
        this(inputStream, true);
    }

    public InputStreamMediaWriter(InputStream inputStream, boolean autoClose) {
        this.inputStreamByteSupplier = new InputStreamByteSupplier(inputStream);
        this.autoClose = autoClose;
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
        try (byteOutput) {
            ScxIO.transferToAll(inputStreamByteSupplier, byteOutput);
        } catch (ScxInputException e) {
            // 这里必然是 IOException 强转安全.
            throw (IOException) e.getCause();
        }
        // 不在 finally 中关闭:
        // 仅当写入流程完整成功(包括 ByteOutput 关闭阶段)且 autoClose=true 时才关闭 inputStream.
        // 若写入过程中抛出异常, 将保留 inputStream 由调用方自行处理.
        if (autoClose) {
            try {
                inputStreamByteSupplier.close();
            } catch (ScxInputException e) {
                // 这里必然是 IOException 强转安全.
                throw (IOException) e.getCause();
            }
        }
    }

}
