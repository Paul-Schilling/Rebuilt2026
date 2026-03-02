package frc.robot;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ConstantsLoader {
    private static JsonObject constants;

    static {
        try {
            loadConstants();
        } catch (IOException e) {
            System.err.println("Failed to load constants from JSON: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void loadConstants() throws IOException {
        String deployPath = "/home/lvuser/deploy/constants.json";
        String jsonContent = new String(Files.readAllBytes(Paths.get(deployPath)));
        constants = JsonParser.parseString(jsonContent).getAsJsonObject();
    }

    // Getter methods for each constants section
    public static JsonObject getShooterConstants() {
        return constants.getAsJsonObject("ShooterConstants");
    }

    public static JsonObject getFeederConstants() {
        return constants.getAsJsonObject("FeederConstants");
    }

    public static JsonObject getRollerConstants() {
        return constants.getAsJsonObject("RollerConstants");
    }

    public static JsonObject getIntakeConstants() {
        return constants.getAsJsonObject("IntakeConstants");
    }

    public static JsonObject getIntakeDeployerConstants() {
        return constants.getAsJsonObject("IntakeDeployerConstants");
    }

    public static JsonObject getVisionConstants() {
        return constants.getAsJsonObject("Vision");
    }

    public static JsonObject getHubConstants() {
        return constants.getAsJsonObject("HubConstants");
    }

    // Helper method to safely get Double values
    public static double getDouble(JsonObject obj, String key, double defaultValue) {
        if (obj.has(key)) {
            return obj.get(key).getAsDouble();
        }
        return defaultValue;
    }

    // Helper method to safely get Integer values
    public static int getInt(JsonObject obj, String key, int defaultValue) {
        if (obj.has(key)) {
            return obj.get(key).getAsInt();
        }
        return defaultValue;
    }

    // Helper method to safely get String values
    public static String getString(JsonObject obj, String key, String defaultValue) {
        if (obj.has(key)) {
            return obj.get(key).getAsString();
        }
        return defaultValue;
    }

    // Helper method to safely get Boolean values
    public static boolean getBoolean(JsonObject obj, String key, boolean defaultValue) {
        if (obj.has(key)) {
            return obj.get(key).getAsBoolean();
        }
        return defaultValue;
    }
}
