package netty.c3;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @className: TestJDKFuture
 * @description: TODO
 * @author: m1ria
 * @date: 2022/9/21 2:36
 * @version: 1.0
 */
@Slf4j
public class TestJDKFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        //线程池
        ExecutorService service = Executors.newFixedThreadPool(2);
        //提交任务
        Future<Integer> future = service.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                log.debug("执行计算");
                Thread.sleep(1000);
                return 50;
            }
        });
        log.debug("等待结果");
        log.debug("结果是{}",future.get());
    }
}
