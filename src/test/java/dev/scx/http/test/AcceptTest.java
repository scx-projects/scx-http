package dev.scx.http.test;

import dev.scx.http.headers.accept.Accept;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AcceptTest {

    public static void main() {
        test1();
    }

    @Test
    public static void test1() {
        var str1 = "text/html, application/xhtml+xml, application/xml;q=0.9, image/avif, image/webp, image/apng, */*;q=0.8, application/signed-exchange;v=b3;q=0.7";
        var accept1 = Accept.parse(str1);
        Assert.assertEquals(accept1.encode(), str1);
    }

}
