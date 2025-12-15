package dev.scx.http.media.form_params;

import dev.scx.http.headers.ScxHttpHeaders;
import dev.scx.http.headers.ScxHttpHeadersWritable;
import dev.scx.http.media.MediaWriter;
import dev.scx.io.ByteOutput;
import dev.scx.io.exception.AlreadyClosedException;
import dev.scx.io.exception.ScxIOException;

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
    public long beforeWrite(ScxHttpHeadersWritable responseHeaders, ScxHttpHeaders requestHeaders) {
        if (responseHeaders.contentType() == null) {
            responseHeaders.contentType(APPLICATION_X_WWW_FORM_URLENCODED);
        }
        bytes = formParams.encode().getBytes(UTF_8);
        return bytes.length;
    }

    @Override
    public void write(ByteOutput byteOutput) throws ScxIOException, AlreadyClosedException {
        try (byteOutput) {
            byteOutput.write(bytes);
        }
    }

}
