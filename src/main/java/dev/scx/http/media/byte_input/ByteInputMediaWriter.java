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
/// 默认自动关闭输入流
///
/// @author scx567888
/// @version 0.0.1
public final class ByteInputMediaWriter implements MediaWriter {

    private final ByteInput byteInput;

    public ByteInputMediaWriter(ByteInput byteInput) {
        this.byteInput = byteInput;
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
        try (byteOutput; byteInput) {
            // 这里发生的 ScxInputException, InputAlreadyClosedException 错误我们直接穿透.
            ScxIO.transferToAll(byteInput, byteOutput);
        }
    }

}
