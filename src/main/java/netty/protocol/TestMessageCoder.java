package netty.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.logging.LoggingHandler;
import netty.message.LoginRequestMessage;
import netty.message.Message;

import java.nio.Buffer;


/**
 * @className: TestMessageCoder
 * @description: TODO
 * @author: m1ria
 * @date: 2022/9/24 23:22
 * @version: 1.0
 */
public class TestMessageCoder {
    public static void main(String[] args) throws Exception {
        EmbeddedChannel channel = new EmbeddedChannel(
                new LoggingHandler(),
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
