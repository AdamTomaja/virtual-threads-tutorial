package org.example;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class Utils {
    static final Logger logger = LoggerFactory.getLogger(Utils.class);

    public static void log(String message) {
        logger.info("{} | " + message, Thread.currentThread());
    }

    @SneakyThrows
    public static void sleep(Duration duration) {
        Thread.sleep(duration);
    }

    // Virtual thread
    // Data is stored on Heap
    public static Thread virtualThread(String name, Runnable runnable) {
        return Thread.ofVirtual()
                .name(name)
                .start(runnable);
    }

}
