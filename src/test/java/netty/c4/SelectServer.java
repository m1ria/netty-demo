package netty.c4;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

import static netty.c1.ByteBufferUtil.debugRead;

/**
 * @className: Server
 * @description: TODO
 * @author: m1ria
 * @date: 2022/8/23 18:12
 * @version: 1.0
 */
@Slf4j
public class SelectServer {

    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);//非阻塞模式

        SelectionKey ssckey = ssc.register(selector, 0, null);
        ssckey.interestOps(SelectionKey.OP_ACCEPT);
        log.debug("register->{}", ssckey);
        ssc.bind(new InetSocketAddress(8000));
        while (true) {
            selector.select();
            selector.selectedKeys();
            Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                iter.remove();
                log.debug("key->{}", key);
                if (key.isAcceptable()) {
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel sc = channel.accept();
                    sc.configureBlocking(false);
                    SelectionKey sckey = sc.register(selector, 0, null);
                    sckey.interestOps(SelectionKey.OP_READ);
                    log.debug("sc->{}", sc);
                } else if (key.isReadable()) {
                    try {
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(16);
                        int read = channel.read(buffer);
                        if (read == -1) {
                            key.cancel();
                        } else {
                            buffer.flip();
                            debugRead(buffer);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        key.cancel();
                    }
                }
            }
        }
    }
}
