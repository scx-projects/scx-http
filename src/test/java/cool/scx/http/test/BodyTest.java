package cool.scx.http.test;

import cool.scx.http.body.BodyAlreadyConsumedException;
import cool.scx.http.body.BodyReadException;
import cool.scx.http.body.ScxHttpBody;
import cool.scx.http.headers.ScxHttpHeaders;
import cool.scx.http.headers.content_encoding.ContentEncoding;
import cool.scx.http.media.MediaReader;
import cool.scx.io.ByteInput;
import cool.scx.io.ScxIO;
import cool.scx.io.exception.AlreadyClosedException;
import cool.scx.io.exception.ScxIOException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

public class BodyTest {

    public static void main(String[] args) throws IOException {
        test1();
        test2();
        test3();
        test4();
    }

    @Test
    public static void test1() {
        var rawData = ScxIO.createByteInput("123456789abcdefg".getBytes());
        var rawHeader = ScxHttpHeaders.of();

        var s = new TestHttpBody(rawData, rawHeader);

        var string = s.asString();

        // 第一次读取 正常
        Assert.assertEquals(string, "123456789abcdefg");
        // 第二次读取 报错
        Assert.assertThrows(BodyAlreadyConsumedException.class, () -> s.asString());
        // 底层应该被关闭
        Assert.assertEquals(rawData.isClosed(), true);
    }

    @Test
    public static void test2() {
        var rawData = ScxIO.createByteInput("123456789abcdefg".getBytes());
        var rawHeader = ScxHttpHeaders.of();

        ScxHttpBody s = new TestHttpBody(rawData, rawHeader);
        // 测试缓存
        s = s.asCacheBody();
        var string1 = s.asString();
        var string2 = s.asString();

        // 第一次读取 正常
        Assert.assertEquals(string1, "123456789abcdefg");
        // 第二次读取 正常
        Assert.assertEquals(string2, "123456789abcdefg");
        // 底层应该被关闭
        Assert.assertEquals(rawData.isClosed(), true);
    }

    @Test
    public static void test3() throws IOException {
        // 测试
        var rawData = ScxIO.createByteInput(convertToGzip("123456789abcdefg".getBytes()));
        var rawHeader = ScxHttpHeaders.of().contentEncoding(ContentEncoding.GZIP);

        ScxHttpBody a = new TestHttpBody(rawData, rawHeader);
        // 测试 gzip
        var s = a.asGzipBody();
        var string = s.asString();

        // 第一次读取 正常
        Assert.assertEquals(string, "123456789abcdefg");
        // 第二次读取 报错
        Assert.assertThrows(BodyAlreadyConsumedException.class, () -> s.asString());
        // 底层应该被关闭
        Assert.assertEquals(rawData.isClosed(), true);
    }

    @Test
    public static void test4() throws IOException {
        // 测试
        var rawData = ScxIO.createByteInput(convertToGzip("123456789abcdefg".getBytes()));
        var rawHeader = ScxHttpHeaders.of().contentEncoding(ContentEncoding.GZIP);

        ScxHttpBody a = new TestHttpBody(rawData, rawHeader);
        // 测试缓存+gzip
        var s = a.asGzipBody().asCacheBody();
        var string1 = s.asString();
        var string2 = s.asString();

        // 第一次读取 正常
        Assert.assertEquals(string1, "123456789abcdefg");
        // 第二次读取 正常
        Assert.assertEquals(string2, "123456789abcdefg");
        // 底层应该被关闭
        Assert.assertEquals(rawData.isClosed(), true);
    }

    private static byte[] convertToGzip(byte[] data) throws IOException {
        var s = new ByteArrayInputStream(data);
        var o = new ByteArrayOutputStream();
        var g = new GZIPOutputStream(o);
        try (s; g) {
            s.transferTo(g);
        }
        return o.toByteArray();
    }

    public record TestHttpBody(ByteInput byteInput, ScxHttpHeaders headers) implements ScxHttpBody {

        @Override
        public <T> T as(MediaReader<T> mediaReader) throws BodyAlreadyConsumedException, BodyReadException {
            try {
                return mediaReader.read(byteInput, headers);
            } catch (ScxIOException e) {
                throw new BodyReadException(e);
            } catch (AlreadyClosedException e) {
                throw new BodyAlreadyConsumedException();
            }
        }

    }

}
