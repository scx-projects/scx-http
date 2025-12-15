package dev.scx.http.media.multi_part;

import dev.scx.function.Function0;
import dev.scx.http.headers.ScxHttpHeaders;
import dev.scx.http.headers.ScxHttpHeadersWritable;
import dev.scx.http.headers.content_disposition.ContentDisposition;
import dev.scx.http.media_type.ScxMediaType;
import dev.scx.io.ByteInput;
import dev.scx.io.supplier.ByteArrayByteSupplier;
import dev.scx.io.supplier.InputStreamByteSupplier;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static dev.scx.io.ScxIO.createByteInput;

/// MultiPartPartWritable
///
/// @author scx567888
/// @version 0.0.1
public interface MultiPartPartWritable extends MultiPartPart {

    ScxHttpHeadersWritable headers();

    MultiPartPartWritable headers(ScxHttpHeaders headers);

    MultiPartPartWritable body(Function0<ByteInput, ?> os);

    default MultiPartPartWritable contentType(ScxMediaType contentType) {
        headers().contentType(contentType);
        return this;
    }

    default MultiPartPartWritable contentDisposition(ContentDisposition contentDisposition) {
        headers().contentDisposition(contentDisposition);
        return this;
    }

    default MultiPartPartWritable name(String name) {
        var contentDisposition = headers().contentDisposition();
        if (contentDisposition != null) {
            contentDisposition(ContentDisposition.of(contentDisposition).name(name));
        } else {
            contentDisposition(ContentDisposition.of().type("form-data").name(name));
        }
        return this;
    }

    default MultiPartPartWritable filename(String filename) {
        var contentDisposition = headers().contentDisposition();
        if (contentDisposition != null) {
            contentDisposition(ContentDisposition.of(contentDisposition).filename(filename));
        } else {
            contentDisposition(ContentDisposition.of().type("form-data").filename(filename));
        }
        return this;
    }

    default MultiPartPartWritable size(long size) {
        var contentDisposition = headers().contentDisposition();
        if (contentDisposition != null) {
            contentDisposition(ContentDisposition.of(contentDisposition).size(size));
        } else {
            contentDisposition(ContentDisposition.of().type("form-data").size(size));
        }
        return this;
    }

    default MultiPartPartWritable body(ByteInput os) {
        return body(() -> os);
    }

    default MultiPartPartWritable body(InputStream os) {
        return body(() -> createByteInput(new InputStreamByteSupplier(os)));
    }

    default MultiPartPartWritable body(byte[] os) {
        return body(() -> createByteInput(new ByteArrayByteSupplier(os)));
    }

    default MultiPartPartWritable body(String os) {
        return body(() -> createByteInput(new ByteArrayByteSupplier(os.getBytes())));
    }

    default MultiPartPartWritable body(File os) {
        return body(() -> createByteInput(new InputStreamByteSupplier(new FileInputStream(os))));
    }

}
