package smarthome.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import smarthome.devices.Device;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class StatusExporter {

    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public static void exportDevices(List<Device> devices, Path path) throws IOException {
        List<Object> statuses = devices.stream()
                .map(Device::status)
                .collect(Collectors.toList());

        String json = gson.toJson(statuses);
        Files.writeString(path, json);

        System.out.println("\n Device statuses exported to: " + path.toAbsolutePath());
        System.out.println("   Total devices: " + devices.size());
    }
}