package smarthome.decorators;

import smarthome.devices.Device;
import java.time.LocalTime;

public class SchedulerDecorator extends DeviceDecorator {
    private final LocalTime time;

    public SchedulerDecorator(Device device, LocalTime time) {
        super(device);
        this.time = time;
    }

    public void triggerNow() {
        System.out.println("[Scheduler] Triggering " + getName() + " immediately (was scheduled at " + time + ")");
        device.operate();
    }

    @Override
    public void operate() {
        System.out.println("[Scheduler] " + getName() + " scheduled at " + time + " (demo prints & executes now)");
        device.operate();
    }

    @Override
    public void turnOff() {
        System.out.println("[Scheduler] Turning off " + getName());
        device.turnOff();
    }
}
