package smarthome.devices;

import smarthome.controllers.DeviceController;
import java.util.HashMap;
import java.util.Map;

public class SmartDoor implements Device {
    private final DeviceController controller;
    private boolean locked = true;

    public SmartDoor(DeviceController controller) {
        if (controller == null) {
            throw new IllegalArgumentException("DeviceController cannot be null");
        }
        this.controller = controller;
    }

    public void lock() {
        controller.powerOn(getName());
        controller.send(getName(), "locked=true");
        locked = true;
        System.out.println("SmartDoor LOCKED");
    }

    public void unlock() {
        controller.powerOn(getName());
        controller.send(getName(), "locked=false");
        locked = false;
        System.out.println("SmartDoor UNLOCKED");
    }

    @Override
    public void operate() {
        unlock();
    }

    @Override
    public void turnOff() {
        lock();
    }

    @Override
    public String getName() {
        return "SmartDoor";
    }

    @Override
    public Map<String, Object> status() {
        Map<String, Object> state = new HashMap<>();
        state.put("name", getName());
        state.put("type", "SmartDoor");
        state.put("locked", locked);
        return state;
    }
}
