package cool.scx.http.media.file;

import cool.scx.http.headers.ScxHttpHeaders;
import cool.scx.http.media.MediaReader;
import cool.scx.io.ByteInput;
import cool.scx.io.consumer.OutputStreamByteConsumer;
import cool.scx.io.exception.AlreadyClosedException;
import cool.scx.io.exception.ScxIOException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;

/// 将内容写入到文件
///
/// 这里设计为不支持 复杂的写入 比如指定文件的偏移量和写入长度, 因为 用户可以拿到 ByteInput 自行完成这些操作.
///
/// @author scx567888
/// @version 0.0.1
public final class FileMediaReader implements MediaReader<File> {

    private final File file;
    private final boolean createDirs;
    private final OpenOption[] options;

    /// 默认构造, 默认创建父目录
    public FileMediaReader(File file, OpenOption... options) {
        this(file, true, options);
    }

    /// @param file       目标文件
    /// @param createDirs 如果父目录不存在, 是否自动创建
    /// @param options    写入选项
    public FileMediaReader(File file, boolean createDirs, OpenOption... options) {
        this.file = file;
        this.createDirs = createDirs;
        this.options = options;
    }

    @Override
    public File read(ByteInput byteInput, ScxHttpHeaders headers) throws ScxIOException, AlreadyClosedException {
        var path = file.toPath();
        try {

            // 自动创建父目录
            if (createDirs) {
                if (path.getParent() != null) {
                    Files.createDirectories(path.getParent());
                }
            }

            // 获取 ByteOutput 并写入
            try (var outputStream = Files.newOutputStream(path, options)) {
                byteInput.readAll(new OutputStreamByteConsumer(outputStream));
            }

            return file;
        } catch (IOException e) {
            throw new ScxIOException(e);
        }
    }

}
