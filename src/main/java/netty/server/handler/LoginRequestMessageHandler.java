package netty.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.message.LoginRequestMessage;
import netty.message.LoginResponseMessage;
import netty.server.service.UserServiceFactory;
import netty.server.session.SessionFactory;

/**
 * @className: LoginRequestMessageHandler
 * @description: TODO
 * @author: m1ria
 * @date: 2022/9/27 23:11
 * @version: 1.0
 */
@ChannelHandler.Sharable
public class LoginRequestMessageHandler extends SimpleChannelInboundHandler<LoginRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestMessage msg) throws Exception {
        String username = msg.getUsername();
        String password = msg.getPassword();
        boolean login = UserServiceFactory.getUserService().login(username, password);
        LoginResponseMessage message;
        if (login) {
            SessionFactory.getSession().bind(ctx.channel(), username);
            message = new LoginResponseMessage(true, "登录成功");
        } else {
            message = new LoginResponseMessage(false, "用户名密码错误");
        }
        ctx.writeAndFlush(message);
    }
}
