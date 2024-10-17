package com.product.async.config;

import com.product.async.exception.AsyncExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
@RequiredArgsConstructor
public class AsyncConfig implements AsyncConfigurer {

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();

    private final AsyncExceptionHandler asyncExceptionHandler;

    public TaskExecutor apiLoggingAsyncExecutor() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        int corePoolSize = calculateOptimalThreadPoolSize(1, 1);
        int maxPoolSize = calculateOptimalThreadPoolSize(3, 1);
        int queSize = calculateQueueSize(200, corePoolSize);

        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queSize);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setThreadNamePrefix("async");
        executor.initialize();
        return executor;
    }

    @Override
    @Bean("apiLoggingAsyncExecutor")
    public Executor getAsyncExecutor() {
        return apiLoggingAsyncExecutor();
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return asyncExceptionHandler;
    }

    private int calculateQueueSize(int taskSubmissionRate, int threadPoolSize) {
        return Math.max(100, taskSubmissionRate - threadPoolSize);
    }

    private int calculateOptimalThreadPoolSize(int ioWaitTimeSeconds, int processingTimeSeconds) {
        return (int) (CPU_COUNT * (1 + (double) ioWaitTimeSeconds / processingTimeSeconds));
    }

}
