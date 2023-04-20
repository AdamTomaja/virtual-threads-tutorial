package org.example;

import java.time.Duration;

public class OomPlatformThreads_1 {
    /**
     * Running concurrent code on small number of Threads:
     * Akka, Fibers, (CE, ZIO), coroutines (kotlin)
     * VirtualThreads
     * <p>
     * OS/JVM Thread - platform threads - expensive
     */

    // Spins OS threads
    public static void main(String[] args) {
        for (int i = 0; i < 1000000; i++) {
            new Thread(() -> {
                try {
                    Thread.sleep(Duration.ofSeconds(1L));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }
}
