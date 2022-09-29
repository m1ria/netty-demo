package netty.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.message.GroupJoinRequestMessage;
import netty.message.GroupJoinResponseMessage;
import netty.server.session.Group;
import netty.server.session.GroupSession;
import netty.server.session.GroupSessionFactory;

/**
 * @className: GroupJoinRequestMessageHandler
 * @description: TODO
 * @author: m1ria
 * @date: 2022/9/29 2:57
 * @version: 1.0
 */
@ChannelHandler.Sharable
public class GroupJoinRequestMessageHandler extends SimpleChannelInboundHandler<GroupJoinRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupJoinRequestMessage msg) throws Exception {
        String groupName = msg.getGroupName();
        String member = msg.getUsername();
        GroupSession groupSession = GroupSessionFactory.getGroupSession();
        if (groupSession.findGroup(groupName) != null) {
            groupSession.joinMember(groupName, member);
            ctx.writeAndFlush(new GroupJoinResponseMessage(true, member + " 已加入群" + groupName));
        } else {
            ctx.writeAndFlush(new GroupJoinResponseMessage(false, "群不存在。。"));
        }
    }
}
