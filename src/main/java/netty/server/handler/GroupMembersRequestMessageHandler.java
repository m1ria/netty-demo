package netty.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.message.GroupMembersRequestMessage;
import netty.message.GroupMembersResponseMessage;
import netty.server.session.GroupSession;
import netty.server.session.GroupSessionFactory;

import java.util.Set;

/**
 * @className: GroupMembersRequestMessageHandler
 * @description: TODO
 * @author: m1ria
 * @date: 2022/9/28 16:35
 * @version: 1.0
 */
@ChannelHandler.Sharable
public class GroupMembersRequestMessageHandler extends SimpleChannelInboundHandler<GroupMembersRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMembersRequestMessage msg) throws Exception {
        GroupSession groupSession = GroupSessionFactory.getGroupSession();
        if (groupSession.findGroup(msg.getGroupName()) != null) {
            String groupName = msg.getGroupName();
            Set<Channel> channels = groupSession.getMembersChannel(groupName);
            ctx.writeAndFlush(new GroupMembersResponseMessage(channels));
        } else {
            ctx.writeAndFlush(new GroupMembersResponseMessage(null));
        }

    }
}
