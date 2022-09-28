package netty.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import netty.server.session.SessionFactory;

/**
 * @className: QuiteHandler
 * @description: TODO
 * @author: m1ria
 * @date: 2022/9/28 3:07
 * @version: 1.0
 */
@Slf4j
@ChannelHandler.Sharable
public class QuiteHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SessionFactory.getSession().unbind(ctx.channel());
        log.debug("{} 已经断开", ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        SessionFactory.getSession().unbind(ctx.channel());
        log.debug("{} 异常断开， {}", ctx.channel(), cause);
    }
}
