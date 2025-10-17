package smarthome.factory;

import smarthome.devices.Light;
import smarthome.devices.MusicSystem;
import smarthome.devices.Thermostat;
import smarthome.devices.SecurityCamera;
import smarthome.devices.SmartMirror;
import smarthome.devices.SmartDoor;
import smarthome.devices.RobotVacuum;

public interface SmartDeviceFactory {
    Light createLight();
    MusicSystem createMusicSystem();
    Thermostat createThermostat();
    SecurityCamera createSecurityCamera();

    default SmartMirror createSmartMirror() {
        throw new UnsupportedOperationException("SmartMirror not supported");
    }

    default SmartDoor createSmartDoor() {
        throw new UnsupportedOperationException("SmartDoor not supported");
    }

    default RobotVacuum createRobotVacuum() {
        throw new UnsupportedOperationException("RobotVacuum not supported");
    }
}