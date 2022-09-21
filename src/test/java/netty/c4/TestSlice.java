package netty.c4;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import static netty.c4.TestByteBuf.log;

/**
 * @className: TestSlice
 * @description: TODO
 * @author: m1ria
 * @date: 2022/9/21 18:05
 * @version: 1.0
 */
public class TestSlice {
    public static void main(String[] args) {
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer(10);
        buf.writeBytes(new byte[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'});
        log(buf);

        //切片过程中 没有复制
        ByteBuf buf1 = buf.slice(0, 5);
        ByteBuf buf2 = buf.slice(5, 5);
        log(buf1);
        log(buf2);

        buf1.setByte(2, 'w');
        log(buf1);
        log(buf);

    }
}
