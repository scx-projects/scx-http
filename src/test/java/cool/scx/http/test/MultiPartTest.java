package cool.scx.http.test;

import cool.scx.common.util.ArrayUtils;
import cool.scx.http.headers.ScxHttpHeaders;
import cool.scx.http.media.multi_part.MultiPart;
import cool.scx.http.media.multi_part.MultiPartMediaWriter;
import cool.scx.http.media.multi_part.MultiPartPart;
import cool.scx.http.media.multi_part.MultiPartStream;
import cool.scx.http.media_type.MediaType;
import cool.scx.http.media_type.ScxMediaType;
import cool.scx.io.ByteArrayByteOutput;
import cool.scx.io.DefaultByteInput;
import cool.scx.io.supplier.ByteArrayByteSupplier;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

import static cool.scx.http.media.multi_part.MultiPartStreamMediaReader.MULTI_PART_STREAM_MEDIA_READER;

public class MultiPartTest {

    public static void main(String[] args) throws IOException {
        test1();
        test2();
    }

    @Test
    public static void test1() throws IOException {
        // 性能测试

        var multipart = MultiPart.of("wwwwwwwwww");
        multipart.add("name", "123");
        multipart.add("name", "456");
        multipart.add("name", "789");

        multipart.add("name1", "a");
        multipart.add("name2", "b");
        multipart.add("name3", "c");
        multipart.add("name4", "d");
        multipart.add("name5", "e");
        multipart.add("name6", "f");
        multipart.add("name8", "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

        byte[] byteArray = createMultiPartBytes(multipart);

        //复制两遍查看是否会产生错误的读取
        byteArray = ArrayUtils.concat(byteArray, byteArray);

        long l = System.nanoTime();
        for (int j = 0; j < 9999; j = j + 1) {

            var s = new DefaultByteInput(new ByteArrayByteSupplier(byteArray));
            MultiPart read = MULTI_PART_STREAM_MEDIA_READER.read(s, ScxHttpHeaders.of().contentType(ScxMediaType.of(MediaType.MULTIPART_FORM_DATA).boundary("wwwwwwwwww")));

            for (MultiPartPart multiPartPart : read) {
                // 什么都不做
            }

        }
        System.out.println((System.nanoTime() - l) / 1000_000);

        long l1 = System.nanoTime();
        for (int j = 0; j < 9999; j = j + 1) {

            var s = new DefaultByteInput(new ByteArrayByteSupplier(byteArray));
            MultiPart read = MULTI_PART_STREAM_MEDIA_READER.read(s, ScxHttpHeaders.of().contentType(ScxMediaType.of(MediaType.MULTIPART_FORM_DATA).boundary("wwwwwwwwww")));

            for (MultiPartPart multiPartPart : read) {
                // 什么都不做
            }

        }
        System.out.println((System.nanoTime() - l1) / 1000_000);

    }


    @Test
    public static void test2() {
        // 1, 构建 MultiPart
        var multipart = MultiPart.of("wwwwwwwwww");
        multipart.add("name", "123");
        multipart.add("name", "456");
        multipart.add("name", "789");

        multipart.add("name1", "a");
        multipart.add("name2", "b");
        multipart.add("name3", "c");
        multipart.add("name4", "d");
        multipart.add("name5", "e");
        multipart.add("name6", "f");
        multipart.add("name8", "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

        byte[] byteArray = createMultiPartBytes(multipart);

        //复制两遍查看是否会产生错误的读取
        byteArray = ArrayUtils.concat(byteArray, byteArray);

        // 测试 标准读取
        var s = new DefaultByteInput(new ByteArrayByteSupplier(byteArray));

        MultiPartStream read = MULTI_PART_STREAM_MEDIA_READER.read(s, ScxHttpHeaders.of().contentType(ScxMediaType.of(MediaType.MULTIPART_FORM_DATA).boundary("wwwwwwwwww")));

        try (read) {
            for (MultiPartPart multiPartPart : read) {
                System.out.println(multiPartPart.name() + " : " + multiPartPart.asBytes().length);
            }
        }

        // 检测是否关闭底层
        Assert.assertEquals(s.isClosed(), true);

    }

    private static byte[] createMultiPartBytes(MultiPart multipart) {
        var ss = new MultiPartMediaWriter(multipart);

        var b = new ByteArrayByteOutput();
        ss.write(b);
        return b.bytes();
    }

}
