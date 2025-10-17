package smarthome.devices;

import smarthome.controllers.DeviceController;
import java.util.HashMap;
import java.util.Map;

public class Light implements Device {
    private int brightness = 0;
    private final DeviceController controller;

    public Light(DeviceController controller) {
        if (controller == null) {
            throw new IllegalArgumentException("DeviceController cannot be null");
        }
        this.controller = controller;
    }

    public void on() {
        controller.powerOn(getName());
        setBrightness(100);
    }

    public void off() {
        controller.powerOff(getName());
        brightness = 0;
    }

    public void setBrightness(int value) {
        brightness = Math.max(0, Math.min(100, value));
        controller.send(getName(), "brightness=" + brightness);
        System.out.println("Light brightness set to " + brightness + "%");
    }

    @Override
    public void operate() {
        on();
    }

    @Override
    public void turnOff() {
        off();
    }

    @Override
    public String getName() {
        return "Light";
    }

    @Override
    public Map<String, Object> status() {
        Map<String, Object> state = new HashMap<>();
        state.put("name", getName());
        state.put("type", "Light");
        state.put("brightness", brightness);
        return state;
    }
}
