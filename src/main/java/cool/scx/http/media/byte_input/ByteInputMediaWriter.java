package cool.scx.http.media.byte_input;

import cool.scx.http.headers.ScxHttpHeaders;
import cool.scx.http.headers.ScxHttpHeadersWritable;
import cool.scx.http.media.MediaWriter;
import cool.scx.io.ByteInput;
import cool.scx.io.ByteOutput;
import cool.scx.io.NullByteInput;
import cool.scx.io.exception.AlreadyClosedException;
import cool.scx.io.exception.ScxIOException;

/// ByteInputMediaWriter
///
/// @author scx567888
/// @version 0.0.1
public final class ByteInputMediaWriter implements MediaWriter {

    private final ByteInput byteInput;
    private final boolean autoClose;

    public ByteInputMediaWriter(ByteInput byteInput) {
        // 直接传输的时候 一般表示 整个输入流已经被用尽了 所以这里我们默认自动关闭输入流.
        this(byteInput, true);
    }

    public ByteInputMediaWriter(ByteInput byteInput, boolean autoClose) {
        this.byteInput = byteInput;
        this.autoClose = autoClose;
    }

    @Override
    public long beforeWrite(ScxHttpHeadersWritable responseHeaders, ScxHttpHeaders requestHeaders) {
        // 如果是 NullByteInput 我们可以特殊处理一下
        if (byteInput instanceof NullByteInput) {
            return 0;
        } else {
            return -1;
        }
    }

    @Override
    public void write(ByteOutput byteOutput) throws ScxIOException, AlreadyClosedException {
        try (byteOutput) {
            byteInput.transferToAll(byteOutput);
        }
        // 这里不在 finally 中 处理, 换句话说 在 transferToAll 发生异常时 不会静默关闭 byteInput
        if (autoClose) {
            byteInput.close();
        }
    }

}
