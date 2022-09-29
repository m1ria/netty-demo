package netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import netty.protocol.MessageCodecSharable;
import netty.protocol.ProtocolFrameDecedr;
import netty.server.handler.*;

/**
 * @className: ChatServer
 * @description: 聊天服务
 * @author: m1ria
 * @date: 2022/9/25 15:35
 * @version: 1.0
 */
@Slf4j
public class ChatServer {

    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.group(boss, worker);
            LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.DEBUG);
            MessageCodecSharable MESSAGE_CODEC = new MessageCodecSharable();
            LoginRequestMessageHandler LOGIN_HANDLER = new LoginRequestMessageHandler();
            ChatRequestMessageHandler CHAT_HANDLER = new ChatRequestMessageHandler();
            GroupCreateRequestMessageHandler GROUP_CREATE_HANDLER = new GroupCreateRequestMessageHandler();
            GroupMembersRequestMessageHandler GROUP_MEMBERS_HANDLER = new GroupMembersRequestMessageHandler();
            GroupChatRequestMessageHandler GROUP_CHAT_HANDLER = new GroupChatRequestMessageHandler();
            GroupJoinRequestMessageHandler GROUP_JOIN_HANDLER = new GroupJoinRequestMessageHandler();
            GroupQuitRequestMessageHandler GROUP_QUIT_HANDLER = new GroupQuitRequestMessageHandler();
            QuiteHandler QUITE_HANDLER = new QuiteHandler();
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel sc) {
                    sc.pipeline().addLast(new IdleStateHandler(5000, 0, 0));
                    sc.pipeline().addLast(new ChannelDuplexHandler(){
                        @Override
                        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                            IdleStateEvent event = (IdleStateEvent) evt;
                            if (event.state() == IdleState.READER_IDLE) {
                                log.debug("已经5000s没有数据了。。。");
                            }
                        }
                    });
                    sc.pipeline().addLast(new ProtocolFrameDecedr());
                    sc.pipeline().addLast(LOGGING_HANDLER);
                    sc.pipeline().addLast(MESSAGE_CODEC);
                    sc.pipeline().addLast(LOGIN_HANDLER);
                    sc.pipeline().addLast(CHAT_HANDLER);
                    sc.pipeline().addLast(GROUP_CREATE_HANDLER);
                    sc.pipeline().addLast(GROUP_CHAT_HANDLER);
                    sc.pipeline().addLast(GROUP_MEMBERS_HANDLER);
                    sc.pipeline().addLast(GROUP_JOIN_HANDLER);
                    sc.pipeline().addLast(GROUP_QUIT_HANDLER);
                    sc.pipeline().addLast(QUITE_HANDLER);
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
