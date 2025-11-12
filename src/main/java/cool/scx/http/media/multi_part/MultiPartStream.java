package cool.scx.http.media.multi_part;

import cool.scx.http.headers.ScxHttpHeaders;
import cool.scx.http.headers.ScxHttpHeadersWritable;
import cool.scx.io.ByteInput;
import cool.scx.io.DefaultByteInput;
import cool.scx.io.exception.AlreadyClosedException;
import cool.scx.io.exception.NoMatchFoundException;
import cool.scx.io.exception.NoMoreDataException;
import cool.scx.io.exception.ScxIOException;
import cool.scx.io.indexer.KMPByteIndexer;
import cool.scx.io.supplier.BoundaryByteSupplier;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static cool.scx.io.supplier.ClosePolicyByteSupplier.noCloseDrain;

/// MultiPartStream
///
/// @author scx567888
/// @version 0.0.1
public final class MultiPartStream implements MultiPart, Iterator<MultiPartPart>, AutoCloseable {

    private static final byte[] CRLF_CRLF_BYTES = "\r\n\r\n".getBytes();

    private final ByteInput byteInput;
    private final String boundary; // xxx
    private final int maxPartHeaderSize;

    private final byte[] boundaryBytes; // --xxx
    private final byte[] boundaryEndBytes; // \r\b--xxx
    private MultiPartPart lastPart;

    public MultiPartStream(ByteInput byteInput, String boundary, int maxPartHeaderSize) {
        this.byteInput = byteInput;
        this.boundary = boundary;
        this.maxPartHeaderSize = maxPartHeaderSize;

        this.boundaryBytes = ("--" + boundary).getBytes();
        this.boundaryEndBytes = ("\r\n--" + boundary).getBytes();
        this.lastPart = null;
    }

    public void consumeLastPart(MultiPartPart lastPart) {
        // 这里我们只要 close 就会消耗掉底层 (原因参见 readContent)
        try {
            lastPart.byteInput().close();
        } catch (AlreadyClosedException e) {
            // 忽略
        }
        // lastPart.byteInput() 中并不会消耗 最后的 \r\n, 但是接下来的判断我们也不需要 所以这里 跳过最后的 \r\n
        try {
            byteInput.skipFully(2);
        } catch (NoMoreDataException e) {
            throw new MultiPartParseException("Unexpected end of stream while skipping trailing CRLF after previous part");
        }
    }

    public ScxHttpHeadersWritable readHeaders() {
        // head 的终结点是 连续两个换行符 具体格式 如下
        // head /r/n
        // /r/n
        // content
        byte[] headersBytes;
        try {
            headersBytes = byteInput.readUntil(CRLF_CRLF_BYTES, maxPartHeaderSize);
        } catch (NoMatchFoundException e) {
            throw new MultiPartParseException("Multipart header too large");
        } catch (NoMoreDataException e) {
            throw new MultiPartParseException("Unexpected end of stream while reading multipart headers");
        }
        var headersStr = new String(headersBytes);
        return ScxHttpHeaders.ofStrict(headersStr);// 使用严格模式解析
    }

    public ByteInput readContent() {
        // 内容 的终结符是 \r\n--boundary
        // 所以我们创建一个以 \r\n--boundary 结尾的分割符 输入流
        // 1, 创建一个 可以一直读取到 分隔符的 字节 提供器
        var boundaryByteSupplier = new BoundaryByteSupplier(byteInput, new KMPByteIndexer(boundaryEndBytes), true);
        // 2, 设置为 不关闭底层 + close 时排空.
        return new DefaultByteInput(noCloseDrain(boundaryByteSupplier));
    }

    @Override
    public String boundary() {
        return boundary;
    }

    @Override
    public Iterator<MultiPartPart> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        // 用户可能并没有消耗掉上一个分块就调用了 hasNext 这里我们替他消费
        if (lastPart != null) {
            // 消费掉上一个分块的内容
            consumeLastPart(lastPart);
            //置空 防止重复消费
            lastPart = null;
        }

        // 下面的操作不会移动指针 所以我们可以 重复调用 hasNext
        // 向后查看
        byte[] startBytes;
        try {
            startBytes = byteInput.peekFully(boundaryBytes.length + 2);
        } catch (NoMoreDataException e) {
            throw new MultiPartParseException("Malformed multipart: boundary peek too short");
        }

        // 这种情况只可能发生在流已经提前结束了
        if (startBytes.length != boundaryBytes.length + 2) {
            throw new MultiPartParseException("Malformed multipart: boundary peek too short");
        }

        // 1. 先判断 peek 开头是否和 boundaryBytes 匹配
        for (int i = 0; i < boundaryBytes.length; i = i + 1) {
            if (startBytes[i] != boundaryBytes[i]) {
                throw new MultiPartParseException("Malformed multipart: boundary not matched");
            }
        }

        // 2. boundary 后两个字节判断
        byte a = startBytes[startBytes.length - 2];
        byte b = startBytes[startBytes.length - 1];

        if (a == '-' && b == '-') {
            // 遇到 --boundary-- , 整个 multipart 结束
            return false;
        } else if (a == '\r' && b == '\n') {
            // 遇到 --boundary\r\n , 还有下一个 part
            return true;
        } else {
            //其他字符那就只能抛异常了
            throw new MultiPartParseException("Malformed multipart: invalid boundary ending");
        }

    }

    @Override
    public MultiPartPart next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more parts available.");
        }

        // 跳过起始的 --boundary\r\n
        try {
            byteInput.skipFully(boundaryBytes.length + 2);
        } catch (NoMoreDataException e) {
            throw new MultiPartParseException("Failed to skip boundary: unexpected end of stream");
        }

        var part = new MultiPartPartImpl();

        // 读取当前部分的头部信息
        var headers = readHeaders();
        part.headers(headers);

        // 读取内容
        var content = readContent();
        part.body(content);

        lastPart = part;

        return part;

    }

    @Override
    public void close() throws ScxIOException, AlreadyClosedException {
        byteInput.close();
    }

}
