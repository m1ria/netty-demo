package nio.c1;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * @className: TestByteBufferString
 * @description: TODO
 * @author: m1ria
 * @date: 2022/8/23 2:28
 * @version: 1.0
 */
public class TestByteBufferString {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(16);
        buffer.put("hello".getBytes());

        ByteBuffer buffer1 = StandardCharsets.UTF_8.encode("hello");
    }
}
