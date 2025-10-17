package smarthome.devices;

import smarthome.controllers.DeviceController;
import java.util.HashMap;
import java.util.Map;

public class MusicSystem implements Device {
    private int volume = 0;
    private final DeviceController controller;

    public MusicSystem(DeviceController controller) {
        if (controller == null) {
            throw new IllegalArgumentException("DeviceController cannot be null");
        }
        this.controller = controller;
    }

    public void play(String playlist) {
        controller.powerOn(getName());
        controller.send(getName(), "play='" + playlist + "'");
        System.out.println("Music is playing: " + playlist);
    }

    public void stop() {
        controller.send(getName(), "stop");
        controller.powerOff(getName());
        System.out.println("Music stopped");
    }

    public void setVolume(int value) {
        volume = Math.max(0, Math.min(100, value));
        controller.send(getName(), "volume=" + volume);
        System.out.println("Music volume set to " + volume + "%");
    }

    @Override
    public void operate() {
        play("default_playlist");
    }

    @Override
    public void turnOff() {
        stop();
    }

    @Override
    public String getName() {
        return "MusicSystem";
    }

    @Override
    public Map<String, Object> status() {
        Map<String, Object> state = new HashMap<>();
        state.put("name", getName());
        state.put("type", "MusicSystem");
        state.put("volume", volume);
        return state;
    }
}
