package netty.c4;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import static io.netty.buffer.ByteBufUtil.appendPrettyHexDump;
import static io.netty.util.internal.StringUtil.NEWLINE;

/**
 * @className: TestBytebuf
 * @description: TestByteBuf
 * @author: m1ria
 * @date: 2022/9/21 4:10
 * @version: 1.0
 */
public class TestByteBuf {
    public static void main(String[] args) {
        ByteBuf buf1 = ByteBufAllocator.DEFAULT.heapBuffer();
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        log(buf);
        System.out.println(buf1.getClass());
        System.out.println(buf.getClass());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 30; i++) {
            sb.append("a");
        }
        buf.writeBytes(sb.toString().getBytes());
        log(buf);
    }

    public static void log(ByteBuf buf) {
        int length = buf.readableBytes();
        int rows = length / 16 + (length % 15 == 0 ? 0 : 1) + 4;
        StringBuilder sb = new StringBuilder(rows * 80 * 2)
                .append("read index: ")
                .append(buf.readerIndex())
                .append(" write index: ")
                .append(buf.writerIndex())
                .append(" capacity: ")
                .append(buf.capacity())
                .append(NEWLINE);
        appendPrettyHexDump(sb, buf);
        System.out.println(sb.toString());
    }
}
