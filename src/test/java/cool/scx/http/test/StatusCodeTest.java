package cool.scx.http.test;

import cool.scx.http.status_code.HttpStatusCode;
import cool.scx.http.status_code.ScxHttpStatusCodeHelper;
import org.testng.Assert;
import org.testng.annotations.Test;

public class StatusCodeTest {

    public static void main(String[] args) {
        test1();
    }

    @Test
    public static void test1() {
        for (var value : HttpStatusCode.values()) {
            var reasonPhrase = ScxHttpStatusCodeHelper.getReasonPhrase(value);
            if (reasonPhrase == null) {
                Assert.fail("reasonPhrase is null");
            }
        }
    }

}
