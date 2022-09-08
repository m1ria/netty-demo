package netty.c4;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

import static netty.c1.ByteBufferUtil.debugAll;
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

    private static void split(ByteBuffer source) {
        source.flip();
        for (int i = 0; i < source.limit(); i++) {
            if (source.get(i) == '\n') {
                int length = i + 1 - source.position();
                ByteBuffer target = ByteBuffer.allocate(length);
                for (int j = 0; j < length; j++) {
                    target.put(source.get());
                }
                debugAll(target);
            }
        }
    }

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
                    ByteBuffer buffer = ByteBuffer.allocate(16);
                    SelectionKey sckey = sc.register(selector, 0, buffer);
                    sckey.interestOps(SelectionKey.OP_READ);
                    log.debug("sc->{}", sc);
                } else if (key.isReadable()) {
                    try {
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer buffer = (ByteBuffer)key.attachment();
                        int read = channel.read(buffer);
                        if (read == -1) {
                            key.cancel();
                        } else {
                            split(buffer);
                            if (buffer.position() == buffer.limit()) {
                                ByteBuffer newBuffer = ByteBuffer.allocate(buffer.capacity() * 2);
                                buffer.flip();
                                newBuffer.put(buffer);
                                key.attach(newBuffer);
                            }
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
