package netty.c4;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * @className: Server
 * @description: TODO
 * @author: m1ria
 * @date: 2022/8/23 18:12
 * @version: 1.0
 */
@Slf4j
public class Server {

    public static void main(String[] args) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(16);
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);//非阻塞模式
        ssc.bind(new InetSocketAddress(8000));

        List<SocketChannel> channels = new ArrayList<>();
        while (true) {
            log.debug("connecting...");
            SocketChannel sc = ssc.accept();
            if (sc != null) {
                log.debug("connected=>{}", sc);
                sc.configureBlocking(false);
                channels.add(sc);
            }
            for (SocketChannel channel : channels) {
                log.debug("before read...{}", channel);
                int read = channel.read(buffer);
                if (read > 0) {
                    buffer.flip();
                    buffer.clear();
                    log.debug("after read... {}", channel);
                }
            }
        }
    }
}
