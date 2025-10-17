package smarthome.devices;

import smarthome.controllers.DeviceController;
import java.util.HashMap;
import java.util.Map;

public class RobotVacuum implements Device {
    private final DeviceController controller;
    private int power = 50;

    public RobotVacuum(DeviceController controller) {
        if (controller == null) {
            throw new IllegalArgumentException("DeviceController cannot be null");
        }
        this.controller = controller;
    }

    public void startCleaning(String area) {
        controller.powerOn(getName());
        controller.send(getName(), "clean=\"" + area + "\"");
        System.out.println("RobotVacuum started cleaning: " + area);
    }

    public void setPower(int level) {
        power = Math.max(0, Math.min(100, level));
        controller.send(getName(), "power=" + power);
        System.out.println("RobotVacuum power set to " + power + "%");
    }

    public void dock() {
        controller.send(getName(), "dock");
        controller.powerOff(getName());
        System.out.println("RobotVacuum docked");
    }

    @Override
    public void operate() {
        startCleaning("AllRooms");
    }

    @Override
    public void turnOff() {
        dock();
    }

    @Override
    public String getName() {
        return "RobotVacuum";
    }

    @Override
    public Map<String, Object> status() {
        Map<String, Object> state = new HashMap<>();
        state.put("name", getName());
        state.put("type", "RobotVacuum");
        state.put("power", power);
        return state;
    }
}
