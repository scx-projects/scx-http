package dev.scx.http.media.byte_input;

import dev.scx.http.media.MediaWriter;
import dev.scx.http.media_type.ScxMediaType;
import dev.scx.io.ByteInput;
import dev.scx.io.ByteOutput;
import dev.scx.io.ScxIO;
import dev.scx.io.exception.InputAlreadyClosedException;
import dev.scx.io.exception.OutputAlreadyClosedException;
import dev.scx.io.exception.ScxInputException;
import dev.scx.io.exception.ScxOutputException;

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
    public ScxMediaType mediaType() {
        return null;
    }

    @Override
    public Long bodyLength() {
        // 这里也可以使用 通过 instanceof 之类的方法来获取 byteInput 的真实字节数,但这里为了简化逻辑 统一返回 null.
        return null;
    }

    @Override
    public void write(ByteOutput byteOutput) throws ScxOutputException, OutputAlreadyClosedException, ScxInputException, InputAlreadyClosedException {
        try (byteOutput) {
            // 这里发生的 ScxInputException, InputAlreadyClosedException 错误我们直接穿透.
            ScxIO.transferToAll(byteInput, byteOutput);
        }
        // 不在 finally 中关闭:
        // 仅当写入流程完整成功(包括 ByteOutput 关闭阶段)且 autoClose=true 时才关闭 byteInput.
        // 若写入过程中抛出异常, 将保留 byteInput 由调用方自行处理.
        if (autoClose) {
            byteInput.close();
        }
    }

}
