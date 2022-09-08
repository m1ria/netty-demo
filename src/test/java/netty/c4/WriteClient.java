package netty.c4;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @className: WriteClient
 * @description: TODO
 * @author: m1ria
 * @date: 2022/9/8 21:09
 * @version: 1.0
 */
public class WriteClient {
    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost", 8081));

        int count = 0;
        while (true) {
            ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
            count += sc.read(buffer);
            System.out.println("count = "+count);
            buffer.clear();
        }

    }

}
