package com.product.async;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AsyncUils {

    public static void runAsync(Runnable task) {
        CompletableFuture.runAsync(() -> {
            try {
                task.run();
            } catch (Exception e) {
                log.error("Async task failed: {}", e.getMessage(), e);
            }
        });
    }

}
