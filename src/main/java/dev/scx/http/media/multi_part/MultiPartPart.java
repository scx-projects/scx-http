package dev.scx.http.media.multi_part;

import dev.scx.function.Function0;
import dev.scx.http.body.BodyAlreadyConsumedException;
import dev.scx.http.body.BodyReadException;
import dev.scx.http.body.ScxHttpBody;
import dev.scx.http.headers.ScxHttpHeaders;
import dev.scx.http.headers.content_disposition.ContentDisposition;
import dev.scx.http.media.MediaReader;
import dev.scx.http.media_type.FileFormat;
import dev.scx.http.media_type.ScxMediaType;
import dev.scx.io.ByteInput;
import dev.scx.io.exception.AlreadyClosedException;
import dev.scx.io.exception.ScxIOException;

import java.io.File;

import static dev.scx.http.media_type.MediaType.APPLICATION_OCTET_STREAM;

/// MultiPartPart
///
/// @author scx567888
/// @version 0.0.1
public interface MultiPartPart extends ScxHttpBody {

    static MultiPartPartWritable of() {
        return new MultiPartPartImpl();
    }

    static MultiPartPartWritable of(String name, String value) {
        return new MultiPartPartImpl().name(name).body(value);
    }

    static MultiPartPartWritable of(String name, byte[] value) {
        return new MultiPartPartImpl().name(name).body(value);
    }

    static MultiPartPartWritable of(String name, File value) {
        var fileSize = value.length();
        var filename = value.getName();
        var fileFormat = FileFormat.findByFileName(filename);
        //没找到就使用 二进制流
        var contentType = fileFormat == null ? APPLICATION_OCTET_STREAM : fileFormat.mediaType();
        return new MultiPartPartImpl().name(name).body(value).size(fileSize).filename(filename).contentType(contentType);
    }

    ScxHttpHeaders headers();

    Function0<ByteInput, ?> body();

    @Override
    default ByteInput byteInput() throws ScxIOException {
        try {
            return body().apply();
        } catch (Throwable e) {
            throw new ScxIOException(e);
        }
    }

    @Override
    default <T> T as(MediaReader<T> mediaReader) throws BodyAlreadyConsumedException, BodyReadException {
        try {
            return mediaReader.read(byteInput(), headers());
        } catch (ScxIOException e) {
            throw new BodyReadException(e);
        } catch (AlreadyClosedException e) {
            throw new BodyAlreadyConsumedException();
        }
    }

    default ScxMediaType contentType() {
        return headers().contentType();
    }

    default ContentDisposition contentDisposition() {
        return headers().contentDisposition();
    }

    default String name() {
        var contentDisposition = contentDisposition();
        return contentDisposition != null ? contentDisposition.name() : null;
    }

    default String filename() {
        var contentDisposition = contentDisposition();
        return contentDisposition != null ? contentDisposition.filename() : null;
    }

    default Long size() {
        var contentDisposition = contentDisposition();
        return contentDisposition != null ? contentDisposition.size() : null;
    }

}
