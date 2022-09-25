package netty.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;
import netty.message.LoginRequestMessage;



/**
 * @className: TestMessageCoder
 * @description: LengthFieldBasedFrameDecoder 处理半包黏包
 * @author: m1ria
 * @date: 2022/9/24 23:22
 * @version: 1.0
 */
public class TestMessageCoder {
    public static void main(String[] args) throws Exception {
        EmbeddedChannel channel = new EmbeddedChannel(
                new LoggingHandler(),
                new LengthFieldBasedFrameDecoder(1024, 12, 4, 0, 0),
                new MessageCodec()
        );
        //encode
        LoginRequestMessage message = new LoginRequestMessage("zhangsa4n", "123", "we");
        channel.writeOutbound(message);
        //decode
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        new MessageCodec().encode(null, message, buf);
        //入站

        channel.writeInbound(buf);
    }
}
