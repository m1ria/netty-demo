package netty.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.message.ChatRequestMessage;
import netty.message.ChatResponseMessage;
import netty.server.session.SessionFactory;

/**
 * @className: ChatRequestMessageHandler
 * @description: TODO
 * @author: m1ria
 * @date: 2022/9/27 23:18
 * @version: 1.0
 */
@ChannelHandler.Sharable
public class ChatRequestMessageHandler extends SimpleChannelInboundHandler<ChatRequestMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatRequestMessage msg) throws Exception {
        String to = msg.getTo();
        Channel channel = SessionFactory.getSession().getChannel(to);
        if (channel != null) {
            channel.writeAndFlush(new ChatResponseMessage(msg.getFrom(), msg.getContent()));
        }
        else {
            ctx.writeAndFlush(new ChatResponseMessage(false, "无应答"));
        }
    }
}
