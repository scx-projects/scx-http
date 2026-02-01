package dev.scx.http.media.file;

import dev.scx.http.media.MediaWriter;
import dev.scx.http.media_type.FileFormat;
import dev.scx.http.media_type.ScxMediaType;
import dev.scx.io.ByteOutput;
import dev.scx.io.exception.OutputAlreadyClosedException;
import dev.scx.io.exception.ScxInputException;
import dev.scx.io.exception.ScxOutputException;
import dev.scx.io.supplier.FileByteSupplier;

import java.io.File;

import static dev.scx.http.media_type.MediaType.APPLICATION_OCTET_STREAM;

/// FileMediaWriter
///
/// @author scx567888
/// @version 0.0.1
public final class FileMediaWriter implements MediaWriter {

    private final File file;
    private final long length;
    private final FileByteSupplier fileByteSupplier;

    public FileMediaWriter(File file) throws ScxInputException {
        this(file, 0, file.length());
    }

    public FileMediaWriter(File file, long offset, long length) throws ScxInputException {
        this.file = file;
        this.length = length;
        this.fileByteSupplier = new FileByteSupplier(file, offset, length);
    }

    @Override
    public ScxMediaType mediaType() {
        var fileFormat = FileFormat.findByFileName(file.getName());
        if (fileFormat == null) { //没找到就使用 二进制流
            return APPLICATION_OCTET_STREAM;
        } else {
            return fileFormat.mediaType();
        }
    }

    @Override
    public Long bodyLength() {
        return length;
    }

    @Override
    public void write(ByteOutput byteOutput) throws ScxOutputException, OutputAlreadyClosedException, ScxInputException {
        // 因为 fileByteSupplier 是我们自己创建的, 我们有责任自动关闭
        try (byteOutput; fileByteSupplier) {
            // 这里发生的 ScxInputException 错误我们直接穿透.
            fileByteSupplier.transferToAll(byteOutput);
        }
    }

}
