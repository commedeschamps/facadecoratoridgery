package smarthome.decorators;

import smarthome.devices.Device;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class DeviceDecorator implements Device {
    protected final Device device;

    public DeviceDecorator(Device device) {
        if (device == null) {
            throw new IllegalArgumentException("Device cannot be null");
        }
        this.device = device;
    }

    @Override
    public void operate() {
        device.operate();
    }

    @Override
    public void turnOff() {
        device.turnOff();
    }

    @Override
    public String getName() {
        return device.getName();
    }

    @Override
    public Map<String, Object> status() {
        var base = new java.util.HashMap<String,Object>(device.status());
        List<String> decorators = new ArrayList<>();
        Object existing = base.get("decorators");
        if (existing instanceof java.util.List<?> raw) {
            for (Object x : raw) decorators.add(String.valueOf(x));
        }
        decorators.add(getClass().getSimpleName());
        base.put("decorators", decorators);
        return base;
    }
}
