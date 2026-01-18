package dev.scx.http.media.byte_input;

import dev.scx.http.media.MediaWriter;
import dev.scx.io.ByteInput;
import dev.scx.io.ByteOutput;
import dev.scx.io.exception.AlreadyClosedException;
import dev.scx.io.exception.ScxIOException;

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
    public Long bodyLength() {
        // 这里也可以使用 通过 instanceof 之类的方法来获取 byteInput 的真实字节数,但这里为了简化逻辑 统一返回 null.
        return null;
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
