package dev.scx.http.media.string;

import dev.scx.http.media.MediaReader;
import dev.scx.http.media_type.ScxMediaType;
import dev.scx.io.ByteInput;
import dev.scx.io.exception.InputAlreadyClosedException;
import dev.scx.io.exception.ScxInputException;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/// 1, 如果未指定字符集则 使用 请求头中的字符集 如果请求头中的字符集为空则回退到 UTF_8
/// 2, 如果指定字符集 忽略 请求头中的字符集
///
/// @author scx567888
/// @version 0.0.1
public final class StringMediaReader implements MediaReader<String, RuntimeException> {

    /// 常用情况 特殊情况直接 new
    public static final StringMediaReader STRING_MEDIA_READER = new StringMediaReader();

    /// 用户指定的 charset
    private final Charset charset;

    public StringMediaReader(Charset charset) {
        this.charset = charset;
    }

    private StringMediaReader() {
        this.charset = null;
    }

    public static Charset getCharsetOrUTF8(ScxMediaType mediaType) {
        if (mediaType != null) {
            var charset = mediaType.charset();
            if (charset != null) {
                return charset;
            }
        }
        return StandardCharsets.UTF_8;
    }

    @Override
    public String read(ByteInput byteInput, ScxMediaType mediaType) throws ScxInputException, InputAlreadyClosedException {
        // 如果用户没有指定编码 我们尝试查找 ContentType 中的编码
        var c = charset != null ? charset : getCharsetOrUTF8(mediaType);

        try (byteInput) {
            var bytes = byteInput.readAll();
            return new String(bytes, c);
        }
    }

}
