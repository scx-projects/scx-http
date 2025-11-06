package cool.scx.http.test;

import cool.scx.http.headers.ScxHttpHeaders;
import cool.scx.http.headers.ScxHttpHeadersWritable;
import cool.scx.http.media.MediaWriter;
import cool.scx.http.sender.BodyAlreadySentException;
import cool.scx.http.sender.HttpSendException;
import cool.scx.http.sender.ScxHttpSender;
import cool.scx.io.ByteOutput;
import cool.scx.io.ScxIO;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;

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

        public final ByteOutput byteOut;
        private final ByteArrayOutputStream out;
        private final ScxHttpHeadersWritable responseHeaders;
        private final ScxHttpHeaders requestHeaders;

        public TestHttpSender(ScxHttpHeadersWritable responseHeaders, ScxHttpHeaders requestHeaders) {
            this.responseHeaders = responseHeaders;
            this.requestHeaders = requestHeaders;
            this.out = new ByteArrayOutputStream();
            this.byteOut = ScxIO.outputStreamToByteOutput(out);
        }

        @Override
        public TestHttpSender send(MediaWriter mediaWriter) throws BodyAlreadySentException, HttpSendException {
            mediaWriter.beforeWrite(responseHeaders, requestHeaders);
            mediaWriter.write(byteOut);
            return this;
        }

        public byte[] getBytes() {
            return out.toByteArray();
        }

        public String getString() {
            return new String(out.toByteArray());
        }

    }

}
