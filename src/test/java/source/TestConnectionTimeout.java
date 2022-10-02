package source;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @className: TestConnectionTimeout
 * @description: option（）方法 给客户端的socketchannel配置参数，给服务端的serversocketchannel配置，服务端 用childoption（）给socketchannel配置参数
 * @author: m1ria
 * @date: 2022/10/2 3:05
 * @version: 1.0
 */
@Slf4j
public class TestConnectionTimeout {
    public static void main(String[] args)  {

        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap()
                    .group(group)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,5000)
                    .channel(NioSocketChannel.class)
                    .handler(new LoggingHandler());
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8002);
            channelFuture.sync().channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
            log.debug("timeout...");
        }finally {
            group.shutdownGracefully();
        }
    }
}
