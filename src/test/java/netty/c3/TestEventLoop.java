package netty.c3;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.NettyRuntime;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @className: TestEventLoop
 * @description: TODO
 * @author: m1ria
 * @date: 2022/9/19 18:21
 * @version: 1.0
 */
@Slf4j
public class TestEventLoop {

    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();//io任务，普通任务，定时任务
//        EventLoopGroup group1 = new DefaultEventLoop();//没有io
        System.out.println(NettyRuntime.availableProcessors());
        //获取下一个循环对象
        System.out.println(group.next());
        System.out.println(group.next());
        System.out.println(group.next());
        System.out.println(group.next());
        System.out.println(group.next());
        //执行普通任务
        group.next().submit(() ->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("ok");

        });
        log.info("main");

        //定时任务
        group.next().scheduleAtFixedRate(() -> {
            log.debug("ok-1");
        }, 0, 1, TimeUnit.SECONDS);
    }
}
