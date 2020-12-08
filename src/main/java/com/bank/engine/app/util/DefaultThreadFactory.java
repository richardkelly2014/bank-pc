package com.bank.engine.app.util;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * Created by jiangfei on 2020/6/27.
 */
public final class DefaultThreadFactory {

    private ThreadPoolTaskExecutor executor;
    private final static DefaultThreadFactory factory = new DefaultThreadFactory();

    private DefaultThreadFactory() {
        executor = createThreadPool();
    }

    public void execute(Runnable runnable) {
        executor.execute(runnable);
    }

    public <T> Future<T> submit(Callable<T> callable) {
        return executor.submit(callable);
    }

    public static void runLater(Runnable runnable) {
        factory.execute(runnable);
    }

    public static <T> Future<T> runLater(Callable<T> callable) {
        return factory.submit(callable);
    }

    private ThreadPoolTaskExecutor createThreadPool() {
        String name = "process-";
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setThreadNamePrefix(name);
        taskExecutor.setCorePoolSize(10);
        taskExecutor.setKeepAliveSeconds(60);
        taskExecutor.setMaxPoolSize(250);
        taskExecutor.setQueueCapacity(0);
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);

        taskExecutor.initialize();

        return taskExecutor;
    }

    public static void sleep() {
        sleep(2000);
    }

    public static void sleep(long n) {
        try {
            Thread.sleep(n);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
