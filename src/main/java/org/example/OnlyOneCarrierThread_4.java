package org.example;

import lombok.SneakyThrows;

import java.time.Duration;

import static org.example.Utils.log;
import static org.example.Utils.sleep;
import static org.example.Utils.virtualThread;

/**
 * Need to set jvm options:
 * -Djdk.virtualThreadScheduler.parallelism=1
 * -Djdk.virtualThreadScheduler.maxPoolSize=1
 * -Djdk.virtualThreadScheduler.minRunnable=1
 * <p>
 * With CPU-bound tasks, virtual thread will NOT yield control over the platform/carrier thread.
 * E.g. a sleep will force VT to yield.
 **/
public class OnlyOneCarrierThread_4 {

    static Thread workHard() {
        return virtualThread("Working hard", () -> {
            log("I`m working hard");
            while (alwaysTrue()) { // avoid compiler optimization
                // do nothing, cpu intensive
            }
            sleep(Duration.ofMillis(100L));
            log("I'm done with the work");
        });
    }

    public static Thread takeABreak() {
        return virtualThread("Taking a break", () -> {
            log("I'm going to take a break");
            sleep(Duration.ofSeconds(1L));
            log("I`m done with the break");
        });
    }

    public static boolean alwaysTrue() {
        return true;
    }

    @SneakyThrows
    public static void main(String[] args) {
        var workingHard = workHard(); // Start the VT on top of the (only) carrier thread
        var takingBreak = takeABreak();
        workingHard.join(); // blocking the calling thread
        takingBreak.join();
    }
}
