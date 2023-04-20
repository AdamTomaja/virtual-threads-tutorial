package org.example;

import lombok.SneakyThrows;

import java.time.Duration;

import static org.example.Utils.log;
import static org.example.Utils.sleep;
import static org.example.Utils.virtualThread;

public class SimpleVirtualThreads_2 {

    // morning routine - boil some water while I am taking a shower
    static Thread bathTime() {
        return virtualThread("Bath Time!", SimpleVirtualThreads_2::bathTimeProcess);
    }

    public static void bathTimeProcess() {
        log("I`m going for a bath");
        sleep(Duration.ofMillis(500L));
        log("I`m done with the bath");
    }

    static Thread boilingWater() {
        return virtualThread("Boil water", SimpleVirtualThreads_2::boilingWaterProcess);
    }

    public static void boilingWaterProcess() {
        log("I`m going to boil some water for tea");
        sleep(Duration.ofMillis(1000L)); // VT is DESCHEDULED from the OS thread (carrier thread)
        log("I`m done with the water");
    }

    @SneakyThrows
    public static void main(String[] args) {
        var bathTime = bathTime();
        var boilingWater = boilingWater();
        bathTime.join();
        boilingWater.join();
    }
}
