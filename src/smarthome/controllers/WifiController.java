package smarthome.controllers;

public class WifiController implements DeviceController {
    @Override
    public void powerOn(String deviceName) {
        System.out.println("[WiFi] " + deviceName + " → POWER ON");
    }

    @Override
    public void powerOff(String deviceName) {
        System.out.println("[WiFi] " + deviceName + " → POWER OFF");
    }

    @Override
    public void send(String deviceName, String payload) {
        System.out.println("[WiFi] " + deviceName + " → CMD {" + payload + "}");
    }
}

