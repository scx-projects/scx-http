package dev.scx.http.media.string;

import dev.scx.http.media.MediaWriter;
import dev.scx.http.media_type.ScxMediaType;
import dev.scx.io.ByteOutput;
import dev.scx.io.exception.OutputAlreadyClosedException;
import dev.scx.io.exception.ScxOutputException;

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
    public ScxMediaType mediaType() {
        return ScxMediaType.of(TEXT_PLAIN).charset(charset);
    }

    @Override
    public Long bodyLength() {
        return (long) getBytes().length;
    }

    @Override
    public void write(ByteOutput byteOutput) throws ScxOutputException, OutputAlreadyClosedException {
        try (byteOutput) {
            byteOutput.write(getBytes());
        }
    }

    private byte[] getBytes() {
        if (bytes == null) {
            bytes = str.getBytes(charset);
        }
        return bytes;
    }

}
