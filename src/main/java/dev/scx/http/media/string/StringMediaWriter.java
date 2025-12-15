package dev.scx.http.media.string;

import dev.scx.http.headers.ScxHttpHeaders;
import dev.scx.http.headers.ScxHttpHeadersWritable;
import dev.scx.http.media.MediaWriter;
import dev.scx.http.media_type.ScxMediaType;
import dev.scx.io.ByteOutput;
import dev.scx.io.exception.AlreadyClosedException;
import dev.scx.io.exception.ScxIOException;

import java.nio.charset.Charset;

import static dev.scx.http.media_type.MediaType.TEXT_PLAIN;
import static java.nio.charset.StandardCharsets.UTF_8;

/// StringMediaWriter
///
/// @author scx567888
/// @version 0.0.1
public final class StringMediaWriter implements MediaWriter {

    private final Charset charset;
    private final String str;
    private byte[] bytes;

    public StringMediaWriter(String str) {
        this(str, UTF_8);
    }

    public StringMediaWriter(String str, Charset charset) {
        this.str = str;
        this.charset = charset;
        this.bytes = null;
    }

    @Override
    public long beforeWrite(ScxHttpHeadersWritable responseHeaders, ScxHttpHeaders requestHeaders) {
        // 只有在没设置 contentType 的时候我们才主动设置
        if (responseHeaders.contentType() == null) {
            responseHeaders.contentType(ScxMediaType.of(TEXT_PLAIN).charset(charset));
        }
        bytes = str.getBytes(charset);
        return bytes.length;
    }

    @Override
    public void write(ByteOutput byteOutput) throws ScxIOException, AlreadyClosedException {
        try (byteOutput) {
            byteOutput.write(bytes);
        }
    }

}
