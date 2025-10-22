package smarthome.factory;

import smarthome.controllers.OldRemoteControllerAdapter;
import smarthome.controllers.DeviceController;
import smarthome.devices.*;

public class OldRemoteDeviceFactory implements SmartDeviceFactory {
    private final DeviceController oldRemoteController = new OldRemoteControllerAdapter();

    @Override
    public Light createLight() {
        return new Light(oldRemoteController);
    }

    @Override
    public MusicSystem createMusicSystem() {
        return new MusicSystem(oldRemoteController);
    }

    @Override
    public Thermostat createThermostat() {
        return new Thermostat(oldRemoteController);
    }

    @Override
    public SecurityCamera createSecurityCamera() {
        return new SecurityCamera(oldRemoteController);
    }

    @Override
    public SmartDoor createSmartDoor() {
        return new SmartDoor(oldRemoteController);
    }

    @Override
    public RobotVacuum createRobotVacuum() {
        return new RobotVacuum(oldRemoteController);
    }

    @Override
    public SmartMirror createSmartMirror() {
        return new SmartMirror(oldRemoteController);
    }
}

