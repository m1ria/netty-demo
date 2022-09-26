package netty.protocol;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.nio.ByteOrder;

/**
 * @className: ProtocolFrameDecedr
 * @description: TODO
 * @author: m1ria
 * @date: 2022/9/26 4:12
 * @version: 1.0
 */
public class ProtocolFrameDecedr extends LengthFieldBasedFrameDecoder {
    public ProtocolFrameDecedr() {
        this(1024, 12, 4, 0, 0);
    }

    public ProtocolFrameDecedr(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }

}
