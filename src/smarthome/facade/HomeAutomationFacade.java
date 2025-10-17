package smarthome.facade;

import smarthome.devices.Device;
import smarthome.devices.Light;
import smarthome.devices.MusicSystem;
import smarthome.devices.Thermostat;
import smarthome.devices.SecurityCamera;
import smarthome.devices.SmartDoor;
import smarthome.devices.RobotVacuum;
import smarthome.devices.SmartMirror;
import java.util.List;

public class HomeAutomationFacade {
    private static final int PARTY_BRIGHTNESS = 70;
    private static final int PARTY_VOLUME = 100;
    private static final int PARTY_TEMP = 22;
    private static final int AWAY_TEMP = 16;
    private static final String PARTY_PLAYLIST = "party_hits";

    private final Light light;
    private final MusicSystem music;
    private final Thermostat thermostat;
    private final SecurityCamera camera;
    private final List<Device> decoratedDevices;

    private final SmartDoor door;
    private final RobotVacuum vacuum;
    private final SmartMirror mirror;

    public HomeAutomationFacade(Light light, MusicSystem music, Thermostat thermostat,
                                SecurityCamera camera, List<Device> decoratedDevices) {
        this(light, music, thermostat, camera, decoratedDevices, null, null, null);
    }

    public HomeAutomationFacade(Light light, MusicSystem music, Thermostat thermostat,
                                SecurityCamera camera, List<Device> decoratedDevices,
                                SmartDoor door, RobotVacuum vacuum, SmartMirror mirror) {
        java.util.Objects.requireNonNull(light, "light");
        java.util.Objects.requireNonNull(music, "music");
        java.util.Objects.requireNonNull(thermostat, "thermostat");
        java.util.Objects.requireNonNull(camera, "camera");
        java.util.Objects.requireNonNull(decoratedDevices, "decoratedDevices");
        this.light = light;
        this.music = music;
        this.thermostat = thermostat;
        this.camera = camera;
        this.decoratedDevices = java.util.List.copyOf(decoratedDevices);
        this.door = door;
        this.vacuum = vacuum;
        this.mirror = mirror;
    }

    public void startPartyMode() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("  P A R T Y   M O D E   A C T I V A T E D  ");
        System.out.println("=".repeat(60));

        System.out.println("\n Setting up lighting...");
        light.on();
        light.setBrightness(PARTY_BRIGHTNESS);

        System.out.println("\n Starting party music...");
        music.setVolume(PARTY_VOLUME);
        music.play(PARTY_PLAYLIST);

        System.out.println("\n️ Adjusting temperature for comfort...");
        thermostat.setTemperature(PARTY_TEMP);

        System.out.println("\n Disabling security camera (guests arriving)...");
        camera.disarm();

        if (door != null) {
            System.out.println("\n Unlocking main door for guests...");
            door.unlock();
        }
        if (mirror != null) {
            System.out.println("\n Updating smart mirror display...");
            mirror.display("Party Time! Welcome Everyone! ");
        }

        System.out.println("\n Party mode setup complete! Let's have fun!");
        System.out.println("=".repeat(60));
    }

    public void activateNightMode() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("  N I G H T   M O D E   A C T I V A T E D  ");
        System.out.println("=".repeat(60));

        System.out.println("\n Dimming lights for sleep...");
        light.setBrightness(0);
        light.off();

        System.out.println("\n Stopping all music...");
        music.stop();

        System.out.println("\n️ Switching to ECO mode (energy saving)...");
        thermostat.ecoMode();

        System.out.println("\n Arming security camera for night protection...");
        camera.arm();

        if (door != null) {
            System.out.println("\n Locking all doors for security...");
            door.lock();
        }
        if (mirror != null) {
            System.out.println("\n Setting goodnight message...");
            mirror.display(" Good night! Sweet dreams! ");
        }

        System.out.println("\n Night mode activated. Sleep well! ");
        System.out.println("=".repeat(60));
    }

    public void leaveHome() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("  L E A V E   H O M E   M O D E  ");
        System.out.println("=".repeat(60));

        System.out.println("\n Shutting down all decorated devices...");
        for (Device device : decoratedDevices) {
            System.out.println("   Turning off: " + device.getName());
            device.turnOff();
        }

        System.out.println("\n Arming security system...");
        camera.arm();

        System.out.println("\n️ Setting away temperature (energy saving: " + AWAY_TEMP + "°C)...");
        thermostat.setTemperature(AWAY_TEMP);

        if (door != null) {
            System.out.println("\n Locking main door...");
            door.lock();
        }
        if (vacuum != null) {
            System.out.println("\n Starting robot vacuum for cleaning...");
            vacuum.startCleaning("AllRooms");
            System.out.println("  ↳ Vacuum will clean while you're away!");
        }

        System.out.println("\n Home secured! Safe travels! ");
        System.out.println("=".repeat(60));
    }

    public void morningRoutine() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("  M O R N I N G   R O U T I N E  A C T I V A T E D  ");
        System.out.println("=".repeat(60));

        if (mirror != null) {
            System.out.println("\n Displaying morning greeting...");
            mirror.display(" Good morning! Have a great day! ");
        }

        System.out.println("\n Playing morning playlist to wake you up...");
        music.play("morning_vibes");

        System.out.println("\n️ Setting comfortable morning temperature (21°C)...");
        thermostat.setTemperature(21);

        System.out.println("\n Turning on lights with gentle brightness...");
        light.on();
        light.setBrightness(80);

        if (door != null) {
            System.out.println("\n Unlocking main door...");
            door.unlock();
        }

        System.out.println("\n Good morning! Your home is ready for the day! ");
        System.out.println("=".repeat(60));
    }
}
