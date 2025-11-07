package cool.scx.http.test;

import cool.scx.common.util.CaseUtils;
import cool.scx.http.headers.HttpHeaderName;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HttpHeaderNameTest {

    public static void main(String[] args) {
        test1();
    }

    @Test
    public static void test1() {
        for (var value : HttpHeaderName.values()) {
            Assert.assertEquals(value.value().toLowerCase(), CaseUtils.toKebab(value.name()));
        }
    }

}
