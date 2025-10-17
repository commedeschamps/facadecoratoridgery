package smarthome.decorators;

import smarthome.devices.Device;

public class RemoteAccessDecorator extends DeviceDecorator {

    public RemoteAccessDecorator(Device device) {
        super(device);
    }

    @Override
    public void operate() {
        connectFromInternet();
        device.operate();
    }

    public void connectFromInternet() {
        System.out.println("[Remote] Connected to " + getName() + " via secure tunnel");
    }
}
