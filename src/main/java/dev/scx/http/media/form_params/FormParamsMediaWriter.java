package dev.scx.http.media.form_params;

import dev.scx.http.media.MediaWriter;
import dev.scx.http.media_type.ScxMediaType;
import dev.scx.io.ByteOutput;
import dev.scx.io.exception.OutputAlreadyClosedException;
import dev.scx.io.exception.ScxOutputException;

import static dev.scx.http.media_type.MediaType.APPLICATION_X_WWW_FORM_URLENCODED;
import static java.nio.charset.StandardCharsets.UTF_8;

/// FormParamsMediaWriter
///
/// @author scx567888
/// @version 0.0.1
public final class FormParamsMediaWriter implements MediaWriter {

    private final FormParams formParams;
    private byte[] bytes;

    public FormParamsMediaWriter(FormParams formParams) {
        this.formParams = formParams;
    }

    @Override
    public ScxMediaType mediaType() {
        return APPLICATION_X_WWW_FORM_URLENCODED;
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
            bytes = formParams.encode().getBytes(UTF_8);
        }
        return bytes;
    }

}
