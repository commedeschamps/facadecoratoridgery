package smarthome.devices;

import smarthome.controllers.DeviceController;
import java.util.HashMap;
import java.util.Map;

public class Thermostat implements Device {
    private static final int MIN_TEMP = -10;
    private static final int MAX_TEMP = 40;
    private static final int DEFAULT_TEMP = 20;
    public static final int ECO_TEMP = 18;

    private int target = DEFAULT_TEMP;
    private final DeviceController controller;

    public Thermostat(DeviceController controller) {
        if (controller == null) {
            throw new IllegalArgumentException("DeviceController cannot be null");
        }
        this.controller = controller;
    }

    public void setTemperature(int degreesC) {
        final int clamped = Math.max(MIN_TEMP, Math.min(MAX_TEMP, degreesC));
        target = clamped;
        controller.powerOn(getName());
        controller.send(getName(), "target=" + clamped + "C");
        System.out.println("Thermostat target set to " + clamped + "°C");
    }

    public void ecoMode() {
        setTemperature(ECO_TEMP);
        System.out.println("Thermostat set to ECO mode");
    }

    @Override
    public void operate() {
        setTemperature(target);
    }

    @Override
    public void turnOff() {
        controller.powerOff(getName());
        System.out.println("Thermostat is off");
    }

    @Override
    public String getName() {
        return "Thermostat";
    }

    @Override
    public Map<String, Object> status() {
        Map<String, Object> state = new HashMap<>();
        state.put("name", getName());
        state.put("type", "Thermostat");
        state.put("target", target);
        return state;
    }
}
