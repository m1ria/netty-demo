package netty.c4;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;

import static netty.c4.TestByteBuf.log;

/**
 * @className: TestCompositeByteBuf
 * @description: TODO
 * @author: m1ria
 * @date: 2022/9/22 2:14
 * @version: 1.0
 */
public class TestCompositeByteBuf {
    public static void main(String[] args) {
        ByteBuf buf1 = ByteBufAllocator.DEFAULT.buffer();
        buf1.writeBytes(new byte[]{1, 2, 3, 4, 5});

        ByteBuf buf2 = ByteBufAllocator.DEFAULT.buffer();
        buf2.writeBytes(new byte[]{6, 7, 8, 9, 99});

//        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
//        buf.writeBytes(buf1).writeBytes(buf2);
        CompositeByteBuf bufs = ByteBufAllocator.DEFAULT.compositeBuffer();
        bufs.addComponents(true, buf1, buf2);
        log(bufs);
    }
}
