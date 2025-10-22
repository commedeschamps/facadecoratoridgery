package smarthome;

import smarthome.devices.*;
import smarthome.decorators.*;
import smarthome.factory.*;
import smarthome.facade.HomeAutomationFacade;
import smarthome.facade.HomeAutomationFacadeBuilder;
import smarthome.util.StatusExporter;
import smarthome.util.StatusImporter;
import java.nio.file.Path;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("=                                                                    =");
        System.out.println("=        SMART HOME AUTOMATION SYSTEM DEMO                          =");
        System.out.println("=        Design Patterns: Bridge + Decorator + Factory + Facade     =");
        System.out.println("=                                                                    =");
        System.out.println("=".repeat(70));

        System.out.println("\nStep 1: Creating devices using Abstract Factory (WiFi)...");
        SmartDeviceFactory factory = new WifiDeviceFactory();

        Light light = factory.createLight();
        MusicSystem music = factory.createMusicSystem();
        Thermostat thermostat = factory.createThermostat();
        SecurityCamera camera = factory.createSecurityCamera();
        System.out.println("  Created: Light, MusicSystem, Thermostat, SecurityCamera");

        SmartMirror mirror = factory.createSmartMirror();
        SmartDoor door = factory.createSmartDoor();
        RobotVacuum vacuum = factory.createRobotVacuum();
        System.out.println("  Created: SmartMirror, SmartDoor, RobotVacuum");

        System.out.println("\nStep 2: Wrapping devices with Decorators...");

        VoiceControlDecorator voiceLight = new VoiceControlDecorator(light);
        EnergySavingDecorator energyLight = new EnergySavingDecorator(voiceLight);


        Device schedLight = new SchedulerDecorator(energyLight, LocalTime.of(8, 0));
        System.out.println("  Light: Scheduler -> EnergySaving -> VoiceControl");

        RemoteAccessDecorator remoteMusic = new RemoteAccessDecorator(music);
        System.out.println("  Music: RemoteAccess");

        Device decoratedThermostat = new EnergySavingDecorator(thermostat);
        System.out.println("  Thermostat: EnergySaving");

        Device loggedCamera = new SecurityLoggerDecorator(camera);
        System.out.println("  Camera: SecurityLogger");

        Device secDoor = new SecurityLoggerDecorator(door);
        System.out.println("  Door: SecurityLogger");

        SchedulerDecorator schedVacuum = new SchedulerDecorator(new SecurityLoggerDecorator(vacuum), LocalTime.of(22, 0));
        System.out.println("  Vacuum: Scheduler -> SecurityLogger");

        Device decoratedMirror = new SecurityLoggerDecorator(mirror);
        System.out.println("  Mirror: SecurityLogger");

        //  collect all decorated devices for JSON export later
        List<Device> decoratedDevices = Arrays.asList(
            schedLight,
            remoteMusic,
            decoratedThermostat,
            loggedCamera,
            secDoor,
            schedVacuum,
            decoratedMirror
        );

        System.out.println("\nStep 3: Building Facade with all devices...");
        HomeAutomationFacade facade = new HomeAutomationFacadeBuilder()
            .withLight(light)
            .withMusic(music)
            .withThermostat(thermostat)
            .withCamera(camera)
            .withDoor(door)
            .withVacuum(vacuum)
            .withMirror(mirror)
            .addDecoratedDevice(schedLight)
            .addDecoratedDevice(remoteMusic)
            .addDecoratedDevice(decoratedThermostat)
            .addDecoratedDevice(loggedCamera)
            .addDecoratedDevice(secDoor)
            .addDecoratedDevice(schedVacuum)
            .addDecoratedDevice(decoratedMirror)
            .build();
        System.out.println("Facade created with all devices using builder pattern.");

        System.out.println("\n" + "-".repeat(70));
        System.out.println("DEMONSTRATION: FACADE PATTERN - High-Level Scenarios");
        System.out.println("-".repeat(70));

        facade.startPartyMode();

        System.out.println("\n[Simulating time passing: evening arrives...]");
        facade.activateNightMode();

        System.out.println("\n[Simulating time passing: next morning...]");
        facade.morningRoutine();

        System.out.println("\n[Simulating: you're leaving for work...]");
        facade.leaveHome();

        System.out.println("\n" + "-".repeat(70));
        System.out.println("DEMONSTRATION: DECORATOR PATTERN - Individual Features");
        System.out.println("-".repeat(70));

        System.out.println("\nDemo 1: Voice Control Decorator");
        System.out.println("-".repeat(50));
        System.out.println("Testing voice commands on light...");
        voiceLight.handleVoice("turn on");
        System.out.println();
        voiceLight.handleVoice("turn off");

        System.out.println("\nDemo 2: Remote Access Decorator");
        System.out.println("-".repeat(50));
        System.out.println("Accessing music system remotely...");
        remoteMusic.connectFromInternet();
        remoteMusic.operate();

        System.out.println("\nDemo 3: Stacked Decorators (Multiple layers)");
        System.out.println("-".repeat(50));
        System.out.println("Operating light with 3 decorators: Scheduler -> EnergySaving -> Voice");
        schedLight.operate();

        System.out.println("\nDemo 4: Security Logger Decorator");
        System.out.println("-".repeat(50));
        System.out.println("Testing security logging on door...");
        secDoor.operate();

        System.out.println("\nDemo 5: Scheduler Decorator (Manual Trigger)");
        System.out.println("-".repeat(50));
        System.out.println("Manually triggering scheduled vacuum (normally runs at 22:00)...");
        schedVacuum.triggerNow();

        System.out.println("\n" + "-".repeat(70));
        System.out.println("DEMONSTRATION: Direct Device Control");
        System.out.println("-".repeat(70));

        System.out.println("\nTesting new devices directly:");
        System.out.println("-".repeat(50));

        System.out.println("\n1. RobotVacuum:");
        vacuum.setPower(75);
        vacuum.startCleaning("Kitchen");

        System.out.println("\n2. SmartMirror:");
        mirror.display("Test Message: All Systems Operational");

        System.out.println("\n3. SmartDoor:");
        door.lock();
        door.unlock();

        System.out.println("\n" + "-".repeat(70));
        System.out.println("DEMONSTRATION: ADAPTER PATTERN - Legacy Integration");
        System.out.println("-".repeat(70));

        System.out.println("\nDemo: Using old remote control with modern devices");
        System.out.println("-".repeat(50));
        System.out.println("Creating devices through OldRemoteDeviceFactory...");

        SmartDeviceFactory oldRemoteFactory = new OldRemoteDeviceFactory();
        Light legacyLight = oldRemoteFactory.createLight();
        MusicSystem legacyMusic = oldRemoteFactory.createMusicSystem();

        System.out.println("\nTesting legacy light control:");
        legacyLight.on();
        legacyLight.setBrightness(50);
        legacyLight.off();

        System.out.println("\nTesting legacy music system:");
        legacyMusic.play("classic_hits");
        legacyMusic.setVolume(60);
        legacyMusic.stop();

        System.out.println("\nAdapter successfully bridges old remote protocol with new system!");

        System.out.println("\n" + "-".repeat(70));
        System.out.println("JSON EXPORT/IMPORT DEMONSTRATION");
        System.out.println("-".repeat(70));

        try {
            Path jsonPath = Path.of("status.json");

            System.out.println("\nExporting device statuses to JSON...");
            StatusExporter.exportDevices(decoratedDevices, jsonPath);

            System.out.println("\nImporting device snapshots from JSON...");
            List<Map<String, Object>> snapshots = StatusImporter.importSnapshots(jsonPath);
            StatusImporter.printSnapshots(snapshots);

        } catch (java.io.IOException e) {
            System.err.println("Error during JSON export/import: " + e.getMessage());
        }

        System.out.println("\n" + "=".repeat(70));
        System.out.println("=                                                                    =");
        System.out.println("=                  DEMO COMPLETED SUCCESSFULLY                      =");
        System.out.println("=                                                                    =");
        System.out.println("=  Patterns demonstrated:                                            =");
        System.out.println("=    - Bridge: Controllers (WiFi/Bluetooth) <-> Devices             =");
        System.out.println("=    - Decorator: Voice, Energy, Remote, Scheduler, Logger          =");
        System.out.println("=    - Abstract Factory: WifiDeviceFactory creates device families  =");
        System.out.println("=    - Facade: HomeAutomationFacade orchestrates complex scenes     =");
        System.out.println("=    - Builder: HomeAutomationFacadeBuilder for fluent construction =");
        System.out.println("=    - Adapter: OldRemoteControllerAdapter integrates legacy remote =");
        System.out.println("=                                                                    =");
        System.out.println("=  Total devices: 7 | Total decorators: 6 | Total scenes: 4         =");
        System.out.println("=  JSON export file: status.json                                     =");
        System.out.println("=                                                                    =");
        System.out.println("=".repeat(70));
    }
}