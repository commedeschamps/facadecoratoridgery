package smarthome.devices;

import smarthome.controllers.DeviceController;

import java.util.HashMap;
import java.util.Map;

public class SmartMirror implements Device {
    private final DeviceController controller;

    public SmartMirror(DeviceController controller) {
        if (controller == null) {
            throw new IllegalArgumentException("DeviceController cannot be null");
        }
        this.controller = controller;
    }

    public void display(String text) {
        controller.powerOn(getName());
        controller.send(getName(), "display=\"" + text + "\"");
        System.out.println("SmartMirror shows: " + text);
    }

    @Override
    public void operate() {
        display("Welcome!");
    }

    @Override
    public void turnOff() {
        controller.powerOff(getName());
        System.out.println("SmartMirror turned off");
    }

    @Override
    public String getName() {
        return "SmartMirror";
    }

    @Override
    public Map<String, Object> status() {
        Map<String, Object> state = new HashMap<>();
        state.put("name", getName());
        state.put("type", "SmartMirror");
        return state;
    }
}