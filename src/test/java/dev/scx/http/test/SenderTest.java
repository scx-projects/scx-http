package dev.scx.http.test;

import dev.scx.exception.ScxWrappedException;
import dev.scx.http.headers.ScxHttpHeaders;
import dev.scx.http.headers.ScxHttpHeadersWritable;
import dev.scx.http.sender.IllegalSenderStateException;
import dev.scx.http.sender.ScxHttpMediaSender;
import dev.scx.http.sender.ScxHttpReceiveException;
import dev.scx.http.sender.ScxHttpSendException;
import dev.scx.io.output.ByteArrayByteOutput;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.nio.charset.StandardCharsets;

import static dev.scx.http.headers.content_encoding.ContentEncoding.GZIP;

public class SenderTest {

    public static void main(String[] args) throws IllegalSenderStateException, ScxHttpReceiveException, ScxHttpSendException {
        test1();
        test2();
    }

    @Test
    public static void test1() throws IllegalSenderStateException, ScxHttpReceiveException, ScxHttpSendException {
        var sender = new TestHttpSender(ScxHttpHeaders.of(), ScxHttpHeaders.of());
        sender.send("123");
        var str = sender.getString();
        Assert.assertEquals(str, "123");
        Assert.assertEquals(sender.byteOut.isClosed(), true);
    }

    @Test
    public static void test2() throws IllegalSenderStateException, ScxHttpReceiveException, ScxHttpSendException {
        var sender = new TestHttpSender(ScxHttpHeaders.of(), ScxHttpHeaders.of());
        sender.gzip().send("123", StandardCharsets.US_ASCII);
        var str = sender.getString();
        Assert.assertEquals(str.length(), 23);
        Assert.assertEquals(sender.headers().contentType().encode(), "text/plain; charset=US-ASCII");
        Assert.assertEquals(sender.headers().contentEncoding(), GZIP);
        Assert.assertEquals(sender.byteOut.isClosed(), true);
    }

    public static class TestHttpSender implements ScxHttpMediaSender<TestHttpSender> {

        public final ByteArrayByteOutput byteOut;
        private final ScxHttpHeadersWritable responseHeaders;
        private final ScxHttpHeaders requestHeaders;

        public TestHttpSender(ScxHttpHeadersWritable responseHeaders, ScxHttpHeaders requestHeaders) {
            this.responseHeaders = responseHeaders;
            this.requestHeaders = requestHeaders;
            this.byteOut = new ByteArrayByteOutput();
        }

        @Override
        public ScxHttpHeadersWritable headers() {
            return responseHeaders;
        }

        @Override
        public TestHttpSender send(BodyWriter bodyWriter) throws IllegalSenderStateException, ScxHttpSendException, ScxWrappedException, ScxHttpReceiveException {
            try {
                bodyWriter.write(byteOut);
            } catch (Throwable e) {
                throw new ScxWrappedException(e);
            }
            return this;
        }

        public byte[] getBytes() {
            return byteOut.bytes();
        }

        public String getString() {
            return new String(byteOut.bytes());
        }

    }

}
