package smarthome.devices;

import java.util.Map;

public interface Device {
    void operate();

    default void turnOff() {
        System.out.println(getName() + " turned OFF.");
    }

    String getName();

    Map<String, Object> status();
}
