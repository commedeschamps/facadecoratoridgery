package smarthome.devices;

import java.util.Map;
//abstraction of Bridge pattern
//all of the devices are concrete abstractions of this interface

public interface Device {
    void operate();

    default void turnOff() {
        System.out.println(getName() + " turned OFF.");
    }

    String getName();

    Map<String, Object> status();
}
