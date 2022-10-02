package netty.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.message.GroupCreateResponseMessage;
import netty.message.GroupJoinRequestMessage;
import netty.message.GroupQuitRequestMessage;
import netty.message.GroupQuitResponseMessage;
import netty.server.session.GroupSession;
import netty.server.session.GroupSessionFactory;

import java.util.Set;

/**
 * @className: GroupQuitRequestMessageHandler
 * @description: TODO
 * @author: m1ria
 * @date: 2022/9/29 2:57
 * @version: 1.0
 */
@ChannelHandler.Sharable
public class GroupQuitRequestMessageHandler extends SimpleChannelInboundHandler<GroupQuitRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupQuitRequestMessage msg) throws Exception {
        String groupName = msg.getGroupName();
        String username = msg.getUsername();
        GroupSession groupSession = GroupSessionFactory.getGroupSession();
        if (groupSession.findGroup(groupName) != null) {
            groupSession.removeMember(groupName, username);
            ctx.writeAndFlush(new GroupQuitResponseMessage(true, " 你已经退出群=====> " + groupName));
            Set<Channel> channels = groupSession.getMembersChannel(groupName);
            if (channels.isEmpty()) {
                groupSession.removeGroup(groupName);
                ctx.writeAndFlush(new GroupQuitResponseMessage(true, groupName + " 群已删除."));
            } else {
                for (Channel channel : channels) {
                    channel.writeAndFlush(new GroupQuitResponseMessage(true, username + " 已退出群 " + groupName));
                }
            }
        } else {
            ctx.writeAndFlush(new GroupQuitResponseMessage(false, groupName + " 群不存在."));
        }
    }
}
