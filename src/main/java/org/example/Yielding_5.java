package org.example;

import lombok.SneakyThrows;

import java.time.Duration;

import static org.example.OnlyOneCarrierThread_4.alwaysTrue;
import static org.example.OnlyOneCarrierThread_4.takeABreak;
import static org.example.Utils.log;
import static org.example.Utils.sleep;
import static org.example.Utils.virtualThread;

// You can control yielding with Thread.yield()
public class Yielding_5 {

    static Thread workConsciously() {
        return virtualThread("Working hard", () -> {
            log("I`m working hard");
            while (alwaysTrue()) { // avoid compiler optimization
                sleep(Duration.ofMillis(100L)); // this will yield control.
            }
            sleep(Duration.ofMillis(100L));
            log("I'm done with the work");
        });
    }

    @SneakyThrows
    public static void main(String[] args) {
        var workConsciously = workConsciously();
        var takingBreak = takeABreak();     // break this time will have a chance to run
        workConsciously.join();
        takingBreak.join();
    }
}
