package org.example;

import lombok.SneakyThrows;

import java.time.Duration;

import static org.example.Utils.log;
import static org.example.Utils.sleep;
import static org.example.Utils.virtualThread;

public class Pinning_6 {

    static Bathroom bathroom = new Bathroom();

    static Thread goToBathroom() {
        return virtualThread("Going to bathroom", bathroom::use);
    }

    @SneakyThrows
    public static void main(String[] args) {
        var riccardo = goToBathroom();
        var daniel = goToBathroom();
        riccardo.join();
        daniel.join();
        System.out.println("Threads Done!");
    }

    // pinning
    // one toiled in the office
    static class Bathroom {
        synchronized void use() { // this code will pin this VT to carrier thread until it ends
            log("Using this bathroom");
            sleep(Duration.ofSeconds(1L)); // this yields normally, but not here (synchronized block)
            log("Done using the bathroom");
        }
    }
}
