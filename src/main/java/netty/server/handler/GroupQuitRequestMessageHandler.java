package netty.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.message.GroupJoinRequestMessage;
import netty.message.GroupQuitResponseMessage;
import netty.server.session.GroupSession;
import netty.server.session.GroupSessionFactory;
import netty.server.session.SessionFactory;

import java.util.Set;

/**
 * @className: GroupQuitRequestMessageHandler
 * @description: TODO
 * @author: m1ria
 * @date: 2022/9/29 2:57
 * @version: 1.0
 */
@ChannelHandler.Sharable
public class GroupQuitRequestMessageHandler extends SimpleChannelInboundHandler<GroupJoinRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupJoinRequestMessage msg) throws Exception {
        String groupName = msg.getGroupName();
        String username = msg.getUsername();
        GroupSession groupSession = GroupSessionFactory.getGroupSession();
        if (groupSession.findGroup(groupName) != null) {
            Set<Channel> channels = groupSession.getMembersChannel(groupName);
            Channel uc = SessionFactory.getSession().getChannel(username);
            if (channels.contains(uc)) {
                groupSession.removeMember(groupName, username);
                if (channels.isEmpty()) {
                    groupSession.removeGroup(groupName);
                    ctx.writeAndFlush(new GroupQuitResponseMessage(true, groupName + " 群无用户，已删除"));
                }
                for (Channel channel : channels) {
                    channel.writeAndFlush(new GroupQuitResponseMessage(true, username + " 已经退出群 " + groupName));
                }
            } else {
                ctx.writeAndFlush(new GroupQuitResponseMessage(false, username + " 不存在"));
            }

        }
    }
}
