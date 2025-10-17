package smarthome.factory;

import smarthome.controllers.WifiController;
import smarthome.devices.Light;
import smarthome.devices.MusicSystem;
import smarthome.devices.Thermostat;
import smarthome.devices.SecurityCamera;
import smarthome.devices.SmartMirror;
import smarthome.devices.SmartDoor;
import smarthome.devices.RobotVacuum;

public class WifiDeviceFactory implements SmartDeviceFactory {
    private final WifiController controller = new WifiController();

    @Override
    public Light createLight() {
        return new Light(controller);
    }

    @Override
    public MusicSystem createMusicSystem() {
        return new MusicSystem(controller);
    }

    @Override
    public Thermostat createThermostat() {
        return new Thermostat(controller);
    }

    @Override
    public SecurityCamera createSecurityCamera() {
        return new SecurityCamera(controller);
    }

    @Override
    public SmartMirror createSmartMirror() {
        return new SmartMirror(controller);
    }

    @Override
    public SmartDoor createSmartDoor() {
        return new SmartDoor(controller);
    }

    @Override
    public RobotVacuum createRobotVacuum() {
        return new RobotVacuum(controller);
    }
}
