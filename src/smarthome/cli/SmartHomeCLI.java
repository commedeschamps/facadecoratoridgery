package smarthome.cli;

import smarthome.devices.*;
import smarthome.decorators.*;
import smarthome.factory.*;
import smarthome.facade.HomeAutomationFacade;
import smarthome.facade.HomeAutomationFacadeBuilder;

import java.time.LocalTime;
import java.util.*;


public class SmartHomeCLI {
    private final Scanner scanner;
    private HomeAutomationFacade facade;
    private Map<String, Device> devices;
    private boolean running;
    private String currentController;

    public SmartHomeCLI() {
        this.scanner = new Scanner(System.in);
        this.running = true;

        // choose controller and initialize devices
        SmartDeviceFactory factory = chooseController();
        initializeDevices(factory);
    }

    private SmartDeviceFactory chooseController() {
        System.out.println("\n" + "═".repeat(70));
        System.out.println("║" + centerText("CONTROLLER SELECTION") + "║");
        System.out.println("═".repeat(70));
        System.out.println("\nChoose a controller type for your smart home devices:");
        System.out.println("┌──────────────────────────────────────────────────┐");
        System.out.println("│ 1. WiFi Controller      - Modern WiFi devices    │");
        System.out.println("│ 2. Bluetooth Controller - Bluetooth devices      │");
        System.out.println("│ 3. Old Remote Controller - Legacy IR remote      │");
        System.out.println("└──────────────────────────────────────────────────┘");
        System.out.print("\nEnter your choice (1-3): ");

        String choice = scanner.nextLine().trim();
        System.out.println();

        switch (choice) {
            case "1":
                currentController = "WiFi";
                System.out.println("WiFi Controller selected");
                return new WifiDeviceFactory();
            case "2":
                currentController = "Bluetooth";
                System.out.println("Bluetooth Controller selected");
                return new BluetoothDeviceFactory();
            case "3":
                currentController = "Old Remote";
                System.out.println("Old Remote Controller selected");
                return new OldRemoteDeviceFactory();
            default:
                currentController = "WiFi";
                System.out.println("Invalid choice. Using WiFi Controller by default.");
                return new WifiDeviceFactory();
        }
    }

    private void initializeDevices(SmartDeviceFactory factory) {
        this.devices = new HashMap<>();

        // Initialize devices with selected controller
        Light light = factory.createLight();
        MusicSystem music = factory.createMusicSystem();
        Thermostat thermostat = factory.createThermostat();
        SecurityCamera camera = factory.createSecurityCamera();
        SmartMirror mirror = factory.createSmartMirror();
        SmartDoor door = factory.createSmartDoor();
        RobotVacuum vacuum = factory.createRobotVacuum();

        // Add decorators
        Device decoratedLight = new SchedulerDecorator(
            new EnergySavingDecorator(
                new VoiceControlDecorator(light)),
            LocalTime.of(8, 0));

        Device decoratedMusic = new RemoteAccessDecorator(music);
        Device decoratedThermostat = new EnergySavingDecorator(thermostat);
        Device decoratedCamera = new SecurityLoggerDecorator(camera);
        Device decoratedDoor = new SecurityLoggerDecorator(door);
        Device decoratedVacuum = new SchedulerDecorator(
            new SecurityLoggerDecorator(vacuum),
            LocalTime.of(22, 0));
        Device decoratedMirror = new SecurityLoggerDecorator(mirror);

        // Register devices
        devices.put("light", light);
        devices.put("music", music);
        devices.put("thermostat", thermostat);
        devices.put("camera", camera);
        devices.put("mirror", mirror);
        devices.put("door", door);
        devices.put("vacuum", vacuum);

        // Create facade
        this.facade = new HomeAutomationFacadeBuilder()
            .withLight(light)
            .withMusic(music)
            .withThermostat(thermostat)
            .withCamera(camera)
            .withDoor(door)
            .withVacuum(vacuum)
            .withMirror(mirror)
            .addDecoratedDevice(decoratedLight)
            .addDecoratedDevice(decoratedMusic)
            .addDecoratedDevice(decoratedThermostat)
            .addDecoratedDevice(decoratedCamera)
            .addDecoratedDevice(decoratedDoor)
            .addDecoratedDevice(decoratedVacuum)
            .addDecoratedDevice(decoratedMirror)
            .build();
    }

    public void start() {
        printWelcome();

        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            handleCommand(choice);
        }

        System.out.println("\nGoodbye! Smart home shut down.");
        scanner.close();
    }

    private void printWelcome() {
        System.out.println("\n" + "═".repeat(70));
        System.out.println("║" + " ".repeat(68) + "║");
        System.out.println("║" + centerText("SMART HOME AUTOMATION SYSTEM") + "║");
        System.out.println("║" + centerText("Command Line Interface") + "║");
        System.out.println("║" + " ".repeat(68) + "║");
        System.out.println("═".repeat(70));
    }

    private void printMenu() {
        System.out.println("\n┌─────────────────────────────────────────────────────────────────────┐");
        System.out.println("│ MAIN MENU                                    Controller: " +
            String.format("%-10s", currentController) + " │");
        System.out.println("├─────────────────────────────────────────────────────────────────────┤");
        System.out.println("│ SCENARIOS:                                                          │");
        System.out.println("│   1. Party Mode       - Activate party mode                         │");
        System.out.println("│   2. Night Mode       - Activate night mode                         │");
        System.out.println("│   3. Morning Routine  - Start morning routine                       │");
        System.out.println("│   4. Leave Home       - Activate leaving home mode                  │");
        System.out.println("├─────────────────────────────────────────────────────────────────────┤");
        System.out.println("│ DEVICE CONTROL:                                                     │");
        System.out.println("│   5. Control Light                                                  │");
        System.out.println("│   6. Control Music                                                  │");
        System.out.println("│   7. Control Thermostat                                             │");
        System.out.println("│   8. Control Camera                                                 │");
        System.out.println("│   9. Control Door                                                   │");
        System.out.println("│  10. Control Vacuum                                                 │");
        System.out.println("│  11. Control Mirror                                                 │");
        System.out.println("├─────────────────────────────────────────────────────────────────────┤");
        System.out.println("│  12. Show status of all devices                                     │");
        System.out.println("│  13. Change controller type (restart required)                      │");
        System.out.println("│   0. Exit                                                           │");
        System.out.println("└─────────────────────────────────────────────────────────────────────┘");
        System.out.print("\nEnter command: ");
    }

    private void handleCommand(String choice) {
        System.out.println();

        switch (choice) {
            case "1":
                facade.startPartyMode();
                break;
            case "2":
                facade.activateNightMode();
                break;
            case "3":
                facade.morningRoutine();
                break;
            case "4":
                facade.leaveHome();
                break;
            case "5":
                controlLight();
                break;
            case "6":
                controlMusic();
                break;
            case "7":
                controlThermostat();
                break;
            case "8":
                controlCamera();
                break;
            case "9":
                controlDoor();
                break;
            case "10":
                controlVacuum();
                break;
            case "11":
                controlMirror();
                break;
            case "12":
                showAllStatus();
                break;
            case "13":
                changeController();
                break;
            case "0":
                running = false;
                break;
            default:
                System.out.println("Invalid command. Please try again.");
        }

        if (running && (choice.equals("1") || choice.equals("2") || choice.equals("3") || choice.equals("4"))) {
            waitForEnter();
        }
    }

    private void changeController() {
        System.out.println("\nWARNING: Changing controller will reinitialize all devices!");
        System.out.println("Current controller: " + currentController);
        System.out.print("\nDo you want to reinitialize with a different controller? (yes/no): ");

        String confirm = scanner.nextLine().trim().toLowerCase();

        if (confirm.equals("yes") || confirm.equals("y")) {
            SmartDeviceFactory newFactory = chooseController();
            System.out.println("\nReinitializing all devices with " + currentController + " controller...");
            initializeDevices(newFactory);
            System.out.println("✓ All devices have been reinitialized!");
            waitForEnter();
        } else {
            System.out.println("\n✓ Controller change cancelled.");
        }
    }

    private void controlLight() {
        Light light = (Light) devices.get("light");

        while (true) {
            Map<String, Object> status = light.status();
            System.out.println("\n┌─── LIGHT CONTROL ───────────────────────────────┐");
            System.out.println("│ Status: " + formatStatus(status) + " │");
            System.out.println("├─────────────────────────────────────────────────┤");
            System.out.println("│ 1. Turn on light                                │");
            System.out.println("│ 2. Turn off light                               │");
            System.out.println("│ 3. Set brightness                               │");
            System.out.println("│ 0. Back                                         │");
            System.out.println("└─────────────────────────────────────────────────┘");
            System.out.print("Choice: ");

            String choice = scanner.nextLine().trim();
            System.out.println();

            switch (choice) {
                case "1":
                    light.on();
                    break;
                case "2":
                    light.off();
                    break;
                case "3":
                    System.out.print("Enter brightness (0-100): ");
                    try {
                        int brightness = Integer.parseInt(scanner.nextLine().trim());
                        light.setBrightness(brightness);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number format");
                    }
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid command");
            }
        }
    }

    private void controlMusic() {
        MusicSystem music = (MusicSystem) devices.get("music");

        while (true) {
            Map<String, Object> status = music.status();
            System.out.println("\n┌─── MUSIC CONTROL ───────────────────────────────┐");
            System.out.println("│ Status: " + formatStatus(status) + " │");
            System.out.println("├─────────────────────────────────────────────────┤");
            System.out.println("│ 1. Play music                                   │");
            System.out.println("│ 2. Stop music                                   │");
            System.out.println("│ 3. Set volume                                   │");
            System.out.println("│ 0. Back                                         │");
            System.out.println("└─────────────────────────────────────────────────┘");
            System.out.print("Choice: ");

            String choice = scanner.nextLine().trim();
            System.out.println();

            switch (choice) {
                case "1":
                    System.out.print("Track name: ");
                    String track = scanner.nextLine().trim();
                    music.play(track.isEmpty() ? "default_playlist" : track);
                    break;
                case "2":
                    music.stop();
                    break;
                case "3":
                    System.out.print("Enter volume (0-100): ");
                    try {
                        int volume = Integer.parseInt(scanner.nextLine().trim());
                        music.setVolume(volume);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number format");
                    }
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid command");
            }
        }
    }

    private void controlThermostat() {
        Thermostat thermostat = (Thermostat) devices.get("thermostat");

        while (true) {
            Map<String, Object> status = thermostat.status();
            System.out.println("\n┌─── THERMOSTAT CONTROL ──────────────────────────┐");
            System.out.println("│ Status: " + formatStatus(status) + " │");
            System.out.println("├─────────────────────────────────────────────────┤");
            System.out.println("│ 1. Turn on thermostat                           │");
            System.out.println("│ 2. Turn off thermostat                          │");
            System.out.println("│ 3. Set temperature                              │");
            System.out.println("│ 0. Back                                         │");
            System.out.println("└─────────────────────────────────────────────────┘");
            System.out.print("Choice: ");

            String choice = scanner.nextLine().trim();
            System.out.println();

            switch (choice) {
                case "1":
                    thermostat.operate();
                    break;
                case "2":
                    thermostat.turnOff();
                    break;
                case "3":
                    System.out.print("Enter temperature (°C): ");
                    try {
                        int temp = Integer.parseInt(scanner.nextLine().trim());
                        thermostat.setTemperature(temp);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number format");
                    }
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid command");
            }
        }
    }

    private void controlCamera() {
        SecurityCamera camera = (SecurityCamera) devices.get("camera");

        while (true) {
            Map<String, Object> status = camera.status();
            System.out.println("\n┌─── CAMERA CONTROL ──────────────────────────────┐");
            System.out.println("│ Status: " + formatStatus(status) + " │");
            System.out.println("├─────────────────────────────────────────────────┤");
            System.out.println("│ 1. Activate camera (ARM)                        │");
            System.out.println("│ 2. Deactivate camera (DISARM)                  │");
            System.out.println("│ 0. Back                                         │");
            System.out.println("└─────────────────────────────────────────────────┘");
            System.out.print("Choice: ");

            String choice = scanner.nextLine().trim();
            System.out.println();

            switch (choice) {
                case "1":
                    camera.arm();
                    break;
                case "2":
                    camera.disarm();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid command");
            }
        }
    }

    private void controlDoor() {
        SmartDoor door = (SmartDoor) devices.get("door");

        while (true) {
            Map<String, Object> status = door.status();
            System.out.println("\n┌─── DOOR CONTROL ────────────────────────────────┐");
            System.out.println("│ Status: " + formatStatus(status) + " │");
            System.out.println("├─────────────────────────────────────────────────┤");
            System.out.println("│ 1. Lock door                                    │");
            System.out.println("│ 2. Unlock door                                  │");
            System.out.println("│ 0. Back                                         │");
            System.out.println("└─────────────────────────────────────────────────┘");
            System.out.print("Choice: ");

            String choice = scanner.nextLine().trim();
            System.out.println();

            switch (choice) {
                case "1":
                    door.lock();
                    break;
                case "2":
                    door.unlock();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid command");
            }
        }
    }

    private void controlVacuum() {
        RobotVacuum vacuum = (RobotVacuum) devices.get("vacuum");

        while (true) {
            Map<String, Object> status = vacuum.status();
            System.out.println("\n┌─── VACUUM CONTROL ──────────────────────────────┐");
            System.out.println("│ Status: " + formatStatus(status) + " │");
            System.out.println("├─────────────────────────────────────────────────┤");
            System.out.println("│ 1. Start cleaning                               │");
            System.out.println("│ 2. Stop cleaning                                │");
            System.out.println("│ 3. Set charging                                 │");
            System.out.println("│ 0. Back                                         │");
            System.out.println("└─────────────────────────────────────────────────┘");
            System.out.print("Choice: ");

            String choice = scanner.nextLine().trim();
            System.out.println();

            switch (choice) {
                case "1":
                    System.out.print("Room name: ");
                    String room = scanner.nextLine().trim();
                    vacuum.startCleaning(room.isEmpty() ? "Living Room" : room);
                    break;
                case "2":
                    vacuum.turnOff();
                    break;
                case "3":
                    System.out.print("Vacuum set to charging...");
                    try {
                        int power = 50;
                        vacuum.setCharging(power);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number format");
                    }
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid command");
            }
        }
    }

    private void controlMirror() {
        SmartMirror mirror = (SmartMirror) devices.get("mirror");

        while (true) {
            Map<String, Object> status = mirror.status();
            System.out.println("\n┌─── MIRROR CONTROL ──────────────────────────────┐");
            System.out.println("│ Status: " + formatStatus(status) + " │");
            System.out.println("├─────────────────────────────────────────────────┤");
            System.out.println("│ 1. Turn on mirror                               │");
            System.out.println("│ 2. Turn off mirror                              │");
            System.out.println("│ 3. Display message                              │");
            System.out.println("│ 0. Back                                         │");
            System.out.println("└─────────────────────────────────────────────────┘");
            System.out.print("Choice: ");

            String choice = scanner.nextLine().trim();
            System.out.println();

            switch (choice) {
                case "1":
                    mirror.operate();
                    break;
                case "2":
                    mirror.turnOff();
                    break;
                case "3":
                    System.out.print("Enter message: ");
                    String message = scanner.nextLine().trim();
                    mirror.display(message.isEmpty() ? "Hello!" : message);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid command");
            }
        }
    }

    private void showAllStatus() {
        System.out.println("\n┌─────────────────────────────────────────────────────────────────────┐");
        System.out.println("│ STATUS OF ALL DEVICES                                               │");
        System.out.println("│ Current Controller: " + String.format("%-48s", currentController) + " │");
        System.out.println("└─────────────────────────────────────────────────────────────────────┘\n");

        for (Map.Entry<String, Device> entry : devices.entrySet()) {
            System.out.println(" " + entry.getKey().toUpperCase());
            System.out.println("   Name: " + entry.getValue().getName());

            Map<String, Object> status = entry.getValue().status();
            System.out.println("   Status: " + status);
            System.out.println();
        }

        waitForEnter();
    }

    private String formatStatus(Map<String, Object> status) {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (Map.Entry<String, Object> entry : status.entrySet()) {
            if (count > 0) sb.append(", ");
            sb.append(entry.getKey()).append(": ").append(entry.getValue());
            count++;
            if (count >= 2) break;
        }
        String result = sb.toString();
        if (result.length() > 43) {
            result = result.substring(0, 40) + "...";
        }
        return String.format("%-43s", result);
    }

    private void waitForEnter() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private String centerText(String text) {
        int padding = (68 - text.length()) / 2;
        return " ".repeat(padding) + text + " ".repeat(68 - padding - text.length());
    }

    public static void main(String[] args) {
        SmartHomeCLI cli = new SmartHomeCLI();
        cli.start();
    }
}

