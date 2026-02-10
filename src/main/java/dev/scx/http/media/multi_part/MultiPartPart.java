package dev.scx.http.media.multi_part;

import dev.scx.http.headers.EasyHttpHeadersReader;
import dev.scx.http.media_type.FileFormat;
import dev.scx.http.received.ScxHttpMediaReceived;

import java.io.File;
import java.io.FileNotFoundException;

import static dev.scx.http.media_type.MediaType.APPLICATION_OCTET_STREAM;

/// MultiPartPart
///
/// @author scx567888
/// @version 0.0.1
public interface MultiPartPart extends ScxHttpMediaReceived, EasyHttpHeadersReader {

    static MultiPartPartWritable of() {
        return new MultiPartPartImpl();
    }

    static MultiPartPartWritable of(String name, String value) {
        return new MultiPartPartImpl().name(name).body(value);
    }

    static MultiPartPartWritable of(String name, byte[] value) {
        return new MultiPartPartImpl().name(name).body(value);
    }

    static MultiPartPartWritable of(String name, File value) throws FileNotFoundException {
        var fileSize = value.length();
        var filename = value.getName();
        var fileFormat = FileFormat.findByFileName(filename);
        //没找到就使用 二进制流
        var contentType = fileFormat == null ? APPLICATION_OCTET_STREAM : fileFormat.mediaType();
        return new MultiPartPartImpl().name(name).body(value).size(fileSize).filename(filename).contentType(contentType);
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
