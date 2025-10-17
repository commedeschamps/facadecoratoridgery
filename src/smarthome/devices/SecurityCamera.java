package smarthome.devices;

import smarthome.controllers.DeviceController;
import java.util.HashMap;
import java.util.Map;

public class SecurityCamera implements Device {
    private boolean armed = false;
    private final DeviceController controller;

    public SecurityCamera(DeviceController controller) {
        if (controller == null) {
            throw new IllegalArgumentException("DeviceController cannot be null");
        }
        this.controller = controller;
    }

    public void arm() {
        controller.powerOn(getName());
        controller.send(getName(), "armed=true");
        armed = true;
        System.out.println("Security camera ARMED");
    }

    public void disarm() {
        controller.send(getName(), "armed=false");
        controller.powerOff(getName());
        armed = false;
        System.out.println("Security camera DISARMED");
    }

    @Override
    public void operate() {
        arm();
    }

    @Override
    public void turnOff() {
        disarm();
    }

    @Override
    public String getName() {
        return "SecurityCamera";
    }

    @Override
    public Map<String, Object> status() {
        Map<String, Object> state = new HashMap<>();
        state.put("name", getName());
        state.put("type", "SecurityCamera");
        state.put("armed", armed);
        return state;
    }
}
