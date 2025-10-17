package smarthome.decorators;

import smarthome.devices.Device;

public class EnergySavingDecorator extends DeviceDecorator {

    public EnergySavingDecorator(Device device) {
        super(device);
    }

    @Override
    public void operate() {
        System.out.println("[EnergySaving] Optimizing power for " + getName());
        device.operate();
    }
}

