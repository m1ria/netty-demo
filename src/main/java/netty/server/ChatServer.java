package netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import netty.protocol.MessageCodec;
import netty.protocol.MessageCodecSharable;
import netty.protocol.ProtocolFrameDecedr;

/**
 * @className: ChatServer
 * @description: 聊天服务
 * @author: m1ria
 * @date: 2022/9/25 15:35
 * @version: 1.0
 */
@Slf4j
public class ChatServer {
    public static final LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.DEBUG);

    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.group(boss, worker);
            LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.DEBUG);
            MessageCodecSharable MESSAGE_CODEC = new MessageCodecSharable();
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) {
                    socketChannel.pipeline().addLast(new ProtocolFrameDecedr());
                    socketChannel.pipeline().addLast(LOGGING_HANDLER);
                    socketChannel.pipeline().addLast(MESSAGE_CODEC);
                }
            });
            ChannelFuture channelFuture = serverBootstrap.bind(8002).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("server error...", e);
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
