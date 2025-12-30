package dev.scx.http.test;

import dev.scx.http.headers.ScxHttpHeaders;
import dev.scx.http.headers.ScxHttpHeadersWritable;
import dev.scx.http.media.MediaWriter;
import dev.scx.http.sender.IllegalSenderStateException;
import dev.scx.http.sender.ScxHttpSender;
import dev.scx.http.sender.ScxHttpSenderStatus;
import dev.scx.io.exception.AlreadyClosedException;
import dev.scx.io.exception.ScxIOException;
import dev.scx.io.output.ByteArrayByteOutput;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SenderTest {

    public static void main(String[] args) {
        test1();
    }

    @Test
    public static void test1() {
        var sender = new TestHttpSender(ScxHttpHeaders.of(), ScxHttpHeaders.of());
        sender.send("123");
        var str = sender.getString();
        Assert.assertEquals(str, "123");
        Assert.assertEquals(sender.byteOut.isClosed(), true);
    }

    public static class TestHttpSender implements ScxHttpSender<TestHttpSender> {

        public final ByteArrayByteOutput byteOut;
        private final ScxHttpHeadersWritable responseHeaders;
        private final ScxHttpHeaders requestHeaders;

        public TestHttpSender(ScxHttpHeadersWritable responseHeaders, ScxHttpHeaders requestHeaders) {
            this.responseHeaders = responseHeaders;
            this.requestHeaders = requestHeaders;
            this.byteOut = new ByteArrayByteOutput();
        }

        @Override
        public TestHttpSender send(MediaWriter mediaWriter) throws IllegalSenderStateException, ScxIOException, AlreadyClosedException {
            mediaWriter.beforeWrite(responseHeaders, requestHeaders);
            mediaWriter.write(byteOut);
            return this;
        }

        @Override
        public ScxHttpSenderStatus senderStatus() {
            return null;
        }

        public byte[] getBytes() {
            return byteOut.bytes();
        }

        public String getString() {
            return new String(byteOut.bytes());
        }

    }

}
