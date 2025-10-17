package smarthome.controllers;

public interface DeviceController {
    void powerOn(String deviceName);
    void powerOff(String deviceName);
    void send(String deviceName, String payload);
}

