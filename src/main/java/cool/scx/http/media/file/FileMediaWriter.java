package cool.scx.http.media.file;

import cool.scx.http.headers.ScxHttpHeaders;
import cool.scx.http.headers.ScxHttpHeadersWritable;
import cool.scx.http.media.MediaWriter;
import cool.scx.http.media_type.FileFormat;
import cool.scx.http.sender.HttpSendException;
import cool.scx.io.ByteInput;
import cool.scx.io.ByteOutput;
import cool.scx.io.ScxIO;
import cool.scx.io.exception.AlreadyClosedException;
import cool.scx.io.exception.ScxIOException;

import java.io.File;
import java.io.IOException;

import static cool.scx.http.media_type.MediaType.APPLICATION_OCTET_STREAM;

/// FileMediaWriter
///
/// @author scx567888
/// @version 0.0.1
public final class FileMediaWriter implements MediaWriter {

    private final File file;
    private final long length;
    private final ByteInput byteInput;

    public FileMediaWriter(File file) throws HttpSendException {
        this(file, 0, file.length());
    }

    public FileMediaWriter(File file, long offset, long length) throws HttpSendException {
        this.file = file;
        this.length = length;
        try {
            this.byteInput = ScxIO.createByteInput(file, offset, length);
        } catch (ScxIOException e) {
            throw new HttpSendException("读取 file 时发生异常 !!!", e.getCause());
        }
    }

    @Override
    public long beforeWrite(ScxHttpHeadersWritable responseHeaders, ScxHttpHeaders requestHeaders) {
        // 这里如果用户没有指定格式的话 我们尝试猜测一下
        if (responseHeaders.contentType() == null) {
            var fileFormat = FileFormat.findByFileName(file.getName());
            if (fileFormat == null) { //没找到就使用 二进制流
                responseHeaders.contentType(APPLICATION_OCTET_STREAM);
            } else {
                responseHeaders.contentType(fileFormat.mediaType());
            }
        }
        return length;
    }

    @Override
    public void write(ByteOutput byteOutput) throws ScxIOException, AlreadyClosedException {
        // 因为 byteInput 是我们自己创建的, 我们有责任自动关闭
        try (byteOutput; byteInput) {
            byteInput.transferToAll(byteOutput);
        }
    }

}
