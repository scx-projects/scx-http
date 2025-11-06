package cool.scx.http.media.multi_part;

import cool.scx.function.Function0;
import cool.scx.http.headers.ScxHttpHeaders;
import cool.scx.http.headers.ScxHttpHeadersWritable;
import cool.scx.http.headers.content_disposition.ContentDisposition;
import cool.scx.http.media_type.ScxMediaType;
import cool.scx.io.ByteInput;
import cool.scx.io.DefaultByteInput;
import cool.scx.io.supplier.ByteArrayByteSupplier;
import cool.scx.io.supplier.InputStreamByteSupplier;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

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
        return body(() -> new DefaultByteInput(new InputStreamByteSupplier(os)));
    }

    default MultiPartPartWritable body(byte[] os) {
        return body(() -> new DefaultByteInput(new ByteArrayByteSupplier(os)));
    }

    default MultiPartPartWritable body(String os) {
        return body(() -> new DefaultByteInput(new ByteArrayByteSupplier(os.getBytes())));
    }

    default MultiPartPartWritable body(File os) {
        return body(() -> new DefaultByteInput(new InputStreamByteSupplier(new FileInputStream(os))));
    }

}
