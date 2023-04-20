package org.example;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static org.example.Utils.log;
import static org.example.Utils.sleep;

public class ExecutorsAndVirtualThreads_3 {

    static final Logger logger = LoggerFactory.getLogger(ExecutorsAndVirtualThreads_3.class);

    // executors on VT
    @SneakyThrows
    static void concurrentMorningRoutineWithExecutors() {
        // We can create a factory to give name for threads
        final var threadFactory = Thread.ofVirtual().name("routine-", 0).factory();

        try (var executor = Executors.newThreadPerTaskExecutor(threadFactory)) {
//        try(var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            var bathTime = executor.submit(SimpleVirtualThreads_2::bathTimeProcess);
            var boilingWater = executor.submit(SimpleVirtualThreads_2::boilingWaterProcess);

            bathTime.get();
            boilingWater.get();
        }
    }

    static int numberOfCores() {
        return Runtime.getRuntime().availableProcessors();
    }

    static void viewCarrierThreadPoolSize() {
        final var threadFactory = Thread.ofVirtual().name("routine-", 0).factory();
        try (var executor = Executors.newThreadPerTaskExecutor(threadFactory)) {

            // 1 more to occupy carrier thread 2 times
            int threads = numberOfCores() + 1;
            logger.info("Running with thread count: {}", threads);

            IntStream.range(0, threads).forEach(i -> {
                executor.submit(() -> {
                    log("Hi from virtual thread: " + i);
                    sleep(Duration.ofSeconds(1L));
                });
            });
        }
    }

    public static void main(String[] args) {
        concurrentMorningRoutineWithExecutors();
//        viewCarrierThreadPoolSize();
    }
}
