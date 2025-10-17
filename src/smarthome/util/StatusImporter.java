package smarthome.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class StatusImporter {

    private static final Gson gson = new Gson();

    public static List<Map<String, Object>> importSnapshots(Path path) throws IOException {
        String json = Files.readString(path);

        Type listType = new TypeToken<List<Map<String, Object>>>(){}.getType();
        List<Map<String, Object>> snapshots = gson.fromJson(json, listType);

        System.out.println("\n📥 Device snapshots imported from: " + path.toAbsolutePath());
        System.out.println("   Total snapshots: " + snapshots.size());

        return snapshots;
    }

    public static void printSnapshots(List<Map<String, Object>> snapshots) {
        System.out.println("\n Imported Device Snapshots:");
        System.out.println("─".repeat(60));

        for (Map<String, Object> snapshot : snapshots) {
            System.out.println("\nDevice: " + snapshot.get("name"));
            System.out.println("  Type: " + snapshot.get("type"));

            snapshot.forEach((key, value) -> {
                if (!key.equals("name") && !key.equals("type")) {
                    System.out.println("  " + key + ": " + value);
                }
            });
        }

        System.out.println("\n" + "─".repeat(60));
    }
}


