package smarthome.facade;

import smarthome.devices.*;
import java.util.ArrayList;
import java.util.List;


public class HomeAutomationFacadeBuilder {
    private Light light;
    private MusicSystem music;
    private Thermostat thermostat;
    private SecurityCamera camera;
    private List<Device> decoratedDevices = new ArrayList<>();
    private SmartDoor door;
    private RobotVacuum vacuum;
    private SmartMirror mirror;

    public HomeAutomationFacadeBuilder withLight(Light light) {
        this.light = light;
        return this;
    }

    public HomeAutomationFacadeBuilder withMusic(MusicSystem music) {
        this.music = music;
        return this;
    }

    public HomeAutomationFacadeBuilder withThermostat(Thermostat thermostat) {
        this.thermostat = thermostat;
        return this;
    }

    public HomeAutomationFacadeBuilder withCamera(SecurityCamera camera) {
        this.camera = camera;
        return this;
    }

    public HomeAutomationFacadeBuilder withDecoratedDevices(List<Device> devices) {
        this.decoratedDevices = devices;
        return this;
    }

    public HomeAutomationFacadeBuilder addDecoratedDevice(Device device) {
        this.decoratedDevices.add(device);
        return this;
    }

    public HomeAutomationFacadeBuilder withDoor(SmartDoor door) {
        this.door = door;
        return this;
    }

    public HomeAutomationFacadeBuilder withVacuum(RobotVacuum vacuum) {
        this.vacuum = vacuum;
        return this;
    }

    public HomeAutomationFacadeBuilder withMirror(SmartMirror mirror) {
        this.mirror = mirror;
        return this;
    }

    public HomeAutomationFacade build() {
        return new HomeAutomationFacade(
            light, music, thermostat, camera, decoratedDevices,
            door, vacuum, mirror
        );
    }
}

