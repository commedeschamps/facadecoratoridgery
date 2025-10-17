package smarthome.decorators;

import smarthome.devices.Device;

public class SecurityLoggerDecorator extends DeviceDecorator {
    public SecurityLoggerDecorator(Device device) {
        super(device);
    }

    @Override
    public void operate() {
        System.out.println("[SECURITY-LOG] operate() on " + getName());
        try {
            device.operate();
            System.out.println("[SECURITY-LOG] operate() SUCCESS on " + getName());
        } catch (RuntimeException ex) {
            System.out.println("[SECURITY-LOG] operate() FAIL on " + getName() + " : " + ex.getMessage());
            throw ex;
        }
    }

    @Override
    public void turnOff() {
        System.out.println("[SECURITY-LOG] turnOff() on " + getName());
        device.turnOff();
    }
}
