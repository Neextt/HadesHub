package io.github.biielkts.servers;

        import io.github.biielkts.Main;
        import io.github.biielkts.utils.LocationSerialize;
        import org.bukkit.Location;

public class SpawnManager {
    private static Location spawn;

    public SpawnManager() {
        String serializedLocation = Main.getInstance().getConfig().getString("settings.spawn");

        SpawnManager.spawn = LocationSerialize.toLocation(serializedLocation);
    }

    public static Location getSpawn() {
        return SpawnManager.spawn;
    }

    public static void setSpawn(Location spawn) {
        SpawnManager.spawn = spawn;
    }
}
