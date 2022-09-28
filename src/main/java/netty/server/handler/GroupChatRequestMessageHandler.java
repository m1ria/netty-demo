package netty.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.message.GroupChatRequestMessage;
import netty.message.GroupChatResponseMessage;
import netty.server.session.GroupSessionFactory;

import java.util.List;
import java.util.Set;

/**
 * @className: GroupChatRequestMessageHandler
 * @description: TODO
 * @author: m1ria
 * @date: 2022/9/28 2:58
 * @version: 1.0
 */
@ChannelHandler.Sharable
public class GroupChatRequestMessageHandler extends SimpleChannelInboundHandler<GroupChatRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupChatRequestMessage msg) throws Exception {
        Set<Channel> channels = GroupSessionFactory.getGroupSession().getMembersChannel(msg.getGroupName());
        for (Channel channel : channels) {
            ctx.writeAndFlush(new GroupChatResponseMessage(msg.getFrom(), msg.getContent()));
        }
    }
}
