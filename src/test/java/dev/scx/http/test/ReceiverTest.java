package dev.scx.http.test;

import dev.scx.http.headers.ScxHttpHeaders;
import dev.scx.http.headers.content_encoding.ContentEncoding;
import dev.scx.http.received.ScxHttpMediaReceived;
import dev.scx.io.ByteInput;
import dev.scx.io.ScxIO;
import dev.scx.io.exception.InputAlreadyClosedException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

public class ReceiverTest {

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

        var s = new TestHttpReceiver(rawData, rawHeader);

        var string = s.asString();

        // 第一次读取 正常
        Assert.assertEquals(string, "123456789abcdefg");
        // 第二次读取 报错
        Assert.assertThrows(InputAlreadyClosedException.class, () -> s.asString());
        // 底层应该被关闭
        Assert.assertEquals(rawData.isClosed(), true);
    }

    @Test
    public static void test2() {
        var rawData = ScxIO.createByteInput("123456789abcdefg".getBytes());
        var rawHeader = ScxHttpHeaders.of();

        ScxHttpMediaReceived s = new TestHttpReceiver(rawData, rawHeader);
        // 测试缓存
        s = s.cache();
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

        ScxHttpMediaReceived a = new TestHttpReceiver(rawData, rawHeader);
        // 测试 gzip
        var s = a.gzip();
        var string = s.asString();

        // 第一次读取 正常
        Assert.assertEquals(string, "123456789abcdefg");
        // 第二次读取 报错
        Assert.assertThrows(InputAlreadyClosedException.class, () -> s.asString());
        // 底层应该被关闭
        Assert.assertEquals(rawData.isClosed(), true);
    }

    @Test
    public static void test4() throws IOException {
        // 测试
        var rawData = ScxIO.createByteInput(convertToGzip("123456789abcdefg".getBytes()));
        var rawHeader = ScxHttpHeaders.of().contentEncoding(ContentEncoding.GZIP);

        ScxHttpMediaReceived a = new TestHttpReceiver(rawData, rawHeader);
        // 测试缓存+gzip
        var s = a.gzip().cache();
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

    public record TestHttpReceiver(ByteInput body, ScxHttpHeaders headers) implements ScxHttpMediaReceived {

    }

}
