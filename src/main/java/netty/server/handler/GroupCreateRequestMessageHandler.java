package netty.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.message.GroupCreateRequestMessage;
import netty.message.GroupCreateResponseMessage;
import netty.server.session.Group;
import netty.server.session.GroupSession;
import netty.server.session.GroupSessionFactory;

import java.util.List;
import java.util.Set;

/**
 * @className: GroupCreateRequestMessageHandler
 * @description: TODO
 * @author: m1ria
 * @date: 2022/9/27 23:35
 * @version: 1.0
 */
@ChannelHandler.Sharable
public class GroupCreateRequestMessageHandler extends SimpleChannelInboundHandler<GroupCreateRequestMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupCreateRequestMessage msg) throws Exception {
        String groupName = msg.getGroupName();
        Set<String> member = msg.getMembers();
        //群管理器
        GroupSession groupSession = GroupSessionFactory.getGroupSession();
        Group group = groupSession.createGroup(groupName, member);
        if (group != null) {
            ctx.writeAndFlush(new GroupCreateResponseMessage(true, "创建成功"));
            List<Channel> channels = groupSession.getMembersChannel(groupName);
            for (Channel channel : channels) {
                channel.writeAndFlush(new GroupCreateResponseMessage(true,   "你已加入"+groupName));
            }

        } else {
            ctx.writeAndFlush(new GroupCreateResponseMessage(false, "已存在"));
        }
    }
}
