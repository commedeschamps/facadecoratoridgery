package smarthome.controllers;
//implementor in bridge pattern

public interface DeviceController {
    void powerOn(String deviceName);
    void powerOff(String deviceName);
    void send(String deviceName, String payload);
}

