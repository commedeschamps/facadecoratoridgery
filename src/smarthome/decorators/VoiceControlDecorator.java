package smarthome.decorators;

import smarthome.devices.Device;

public class VoiceControlDecorator extends DeviceDecorator {

    public VoiceControlDecorator(Device device) {
        super(device);
    }

    @Override
    public void operate() {
        System.out.println("Voice control enabled for " + getName());
        device.operate();
    }

    public void handleVoice(String phrase) {
        if (phrase.toLowerCase().contains("on")) {
            device.operate();
        } else if (phrase.toLowerCase().contains("off")) {
            device.turnOff();
        }
    }
}

