package cool.scx.http.test;

import cool.scx.http.headers.ScxHttpHeaders;
import cool.scx.http.headers.ScxHttpHeadersWritable;
import cool.scx.http.media.MediaWriter;
import cool.scx.http.sender.BodyAlreadySentException;
import cool.scx.http.sender.HttpSendException;
import cool.scx.http.sender.ScxHttpSender;
import cool.scx.io.ByteArrayByteOutput;
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
        public TestHttpSender send(MediaWriter mediaWriter) throws BodyAlreadySentException, HttpSendException {
            mediaWriter.beforeWrite(responseHeaders, requestHeaders);
            mediaWriter.write(byteOut);
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
