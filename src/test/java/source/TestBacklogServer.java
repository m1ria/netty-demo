package source;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @className: TestBacklogServer
 * @description: TODO
 * @author: m1ria
 * @date: 2022/10/2 3:44
 * @version: 1.0
 */
public class TestBacklogServer {
    public static void main(String[] args) {
        new ServerBootstrap().group(new NioEventLoopGroup())
                .option(ChannelOption.SO_BACKLOG, 2)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        channel.pipeline().addLast(new LoggingHandler());
                    }
                }).bind(8002);
    }
}
