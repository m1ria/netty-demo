package netty.c4;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * @className: Client
 * @description: TODO
 * @author: m1ria
 * @date: 2022/8/23 18:20
 * @version: 1.0
 */
public class Client {

    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost", 8000));
        sc.write(Charset.defaultCharset().encode("hehehdada"));
        System.in.read();
    }
}
