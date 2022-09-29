package netty.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import lombok.extern.slf4j.Slf4j;
import netty.config.Config;
import netty.message.Message;

import java.util.List;

/**
 * @className: MessageCodecSharable
 * @description: sharable
 * @author: m1ria
 * @date: 2022/9/26 3:47
 * @version: 1.0
 */
@Slf4j
@ChannelHandler.Sharable
public class MessageCodecSharable extends MessageToMessageCodec<ByteBuf, Message> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Message message, List<Object> list) throws Exception {
        ByteBuf byteBuf = channelHandlerContext.alloc().buffer();
        byteBuf.writeBytes(new byte[]{1, 2, 3, 4});
        byteBuf.writeByte(1);
        //字节序列化的方式
        byteBuf.writeByte(Config.getSerializerAlgorithm().ordinal());
        byteBuf.writeByte(message.getMessageType());
        byteBuf.writeInt(message.getSequenceId());
        byteBuf.writeByte(0xff);
        byte[] bytes = Config.getSerializerAlgorithm().serializer(message);
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
        list.add(byteBuf);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int magicNum = byteBuf.readInt();
        byte version = byteBuf.readByte();
        byte serializerType = byteBuf.readByte();
        byte messageType = byteBuf.readByte();
        int sequenceId = byteBuf.readInt();
        byteBuf.readByte();
        int length = byteBuf.readInt();
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes, 0, length);
        Serializer.Algorithm algorithm = Serializer.Algorithm.values()[serializerType];
        Class<? extends Message> messageClass = Message.getMessageClass(messageType);
        Object deserializer = algorithm.deserializer(messageClass, bytes);
//        log.debug("------{}, {}, {}, {},{} ,{}", magicNum, version, serializerType, messageType, sequenceId, length);
//        log.debug("------{}", message);
        list.add(deserializer);
    }
}
