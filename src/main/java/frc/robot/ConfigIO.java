package frc.robot;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import frc.robot.config.RootConfig;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class ConfigIO {
    private static final Path DEPLOY = Paths.get("/home/lvuser/deploy/constants.json");
    private static final DateTimeFormatter TS = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private ConfigIO() {}

    public static RootConfig load() throws IOException {
        if (!Files.exists(DEPLOY)) {
            return new RootConfig();
        }
        String json = Files.readString(DEPLOY);
        return GSON.fromJson(json, RootConfig.class);
    }

    public static void save(RootConfig cfg) throws IOException {
        // archive previous
        if (Files.exists(DEPLOY)) {
            String ts = LocalDateTime.now().format(TS);
            Path archive = DEPLOY.resolveSibling("constants-" + ts + ".json");
            Files.move(DEPLOY, archive, StandardCopyOption.REPLACE_EXISTING);
        }

        Path tmp = DEPLOY.resolveSibling("constants.json.tmp");
        Files.writeString(tmp, GSON.toJson(cfg), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        Files.move(tmp, DEPLOY, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
    }
}
