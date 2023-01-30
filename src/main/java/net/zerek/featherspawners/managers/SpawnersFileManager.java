package net.zerek.featherspawners.managers;

import net.zerek.featherspawners.FeatherSpawners;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SpawnersFileManager {

    private final FeatherSpawners plugin;
    File file;
    private FileConfiguration yml;
    private final Map<String,ItemStack> spawnersMap = new HashMap<>();

    public SpawnersFileManager(FeatherSpawners plugin) {

        this.plugin = plugin;
        this.init();
    }

    private void init() {
        file = new File(plugin.getDataFolder() + File.separator + "spawners.yml");
        yml = YamlConfiguration.loadConfiguration(file);
        this.generateSpawnersMap();

    }

    private void generateSpawnersMap() {

        yml.getKeys(false).forEach(entityType -> spawnersMap.put(entityType,yml.getItemStack(entityType)));
    }

    public boolean saveSpawner(String entityType, ItemStack spawner) {

        try {
            yml.set(entityType, spawner);
            yml.save(file);
        } catch (IOException e) {
            plugin.getLogger().info("Spawner failed to save: " + entityType);
            return false;
        }

        plugin.getLogger().info("Spawner saved: " + entityType);
        this.generateSpawnersMap();
        return true;
    }

    public boolean isSpawnerSaved(String entityType) {

        return spawnersMap.containsKey(entityType);
    }

    public ItemStack getSpawner(String entityType) {

        return spawnersMap.get(entityType);
    }

    public Set<String> getSpawnerList() {

        return spawnersMap.keySet();
    }
}
