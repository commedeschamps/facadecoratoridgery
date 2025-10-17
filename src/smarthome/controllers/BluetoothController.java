package smarthome.controllers;

public class BluetoothController implements DeviceController {
    @Override
    public void powerOn(String deviceName) {
        System.out.println("[Bluetooth] " + deviceName + " → POWER ON");
    }

    @Override
    public void powerOff(String deviceName) {
        System.out.println("[Bluetooth] " + deviceName + " → POWER OFF");
    }

    @Override
    public void send(String deviceName, String payload) {
        System.out.println("[Bluetooth] " + deviceName + " → CMD {" + payload + "}");
    }
}
