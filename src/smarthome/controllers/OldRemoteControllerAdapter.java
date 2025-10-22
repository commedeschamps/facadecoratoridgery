package smarthome.controllers;

import smarthome.thirdparty.OldRemote;

//adapter to make OldRemote compatible with DeviceController
public class OldRemoteControllerAdapter implements DeviceController {
    private final OldRemote oldRemote = new OldRemote();

    @Override
    public void powerOn(String deviceName) {
        oldRemote.turnOn(deviceName);
    }

    @Override
    public void powerOff(String deviceName) {
        oldRemote.turnOff(deviceName);
    }

    @Override
    public void send(String deviceName, String payload) {
        //OldRemote.cmd() expects (int id, String data)
        //we convert deviceName to a stable int using hashCode
        int deviceId = Math.abs(deviceName.hashCode());
        oldRemote.cmd(deviceId, deviceName + ": " + payload);
    }
}
