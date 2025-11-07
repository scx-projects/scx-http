package cool.scx.http.media.input_stream;

import cool.scx.http.headers.ScxHttpHeaders;
import cool.scx.http.headers.ScxHttpHeadersWritable;
import cool.scx.http.media.MediaWriter;
import cool.scx.io.ByteOutput;
import cool.scx.io.ScxIO;
import cool.scx.io.exception.AlreadyClosedException;
import cool.scx.io.exception.ScxIOException;

import java.io.IOException;
import java.io.InputStream;

/// InputStreamMediaWriter
///
/// 设计思路和 ByteInputMediaWriter 保持一致
///
/// @author scx567888
/// @version 0.0.1
public final class InputStreamMediaWriter implements MediaWriter {

    private final InputStream inputStream;
    private final boolean autoClose;

    public InputStreamMediaWriter(InputStream inputStream) {
        // 直接传输的时候 一般表示 整个输入流已经被用尽了 所以这里我们默认自动关闭输入流.
        this(inputStream, true);
    }

    public InputStreamMediaWriter(InputStream inputStream, boolean autoClose) {
        this.inputStream = inputStream;
        this.autoClose = autoClose;
    }

    @Override
    public long beforeWrite(ScxHttpHeadersWritable responseHeaders, ScxHttpHeaders requestHeaders) {
        // 这里也可以使用 通过 instanceof 之类的方法来获取 inputStream 的真实字节数,但这里为了简化逻辑 统一返回 -1.
        return -1;
    }

    @Override
    public void write(ByteOutput byteOutput) throws ScxIOException, AlreadyClosedException {
        try (byteOutput) {
            inputStream.transferTo(ScxIO.byteOutputToOutputStream(byteOutput));
        } catch (IOException e) {
            throw new ScxIOException(e);
        }
        // 这里不在 finally 中 处理, 换句话说 在 transferToAll 发生异常时 不会静默关闭 byteInput
        if (autoClose) {
            try {
                inputStream.close();
            } catch (IOException e) {
                throw new ScxIOException(e);
            }
        }
    }

}
