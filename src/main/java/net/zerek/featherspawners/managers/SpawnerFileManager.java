package net.zerek.featherspawners.managers;

import net.zerek.featherspawners.FeatherSpawners;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class SpawnerFileManager {

    private final FeatherSpawners plugin;

    Map<String,ItemStack> spawnerMap = new HashMap<>();

    ConfigurationSection yml;

    public SpawnerFileManager(FeatherSpawners plugin) {

        this.plugin = plugin;

        this.init();
    }

    private void init() {

        File file = new File(plugin.getDataFolder() + File.separator + "spawners.yml");

        if (!file.exists()) plugin.saveResource("spawners.yml",false);

        yml = YamlConfiguration.loadConfiguration(file).getConfigurationSection("spawners");

        yml.getKeys(false).forEach(entityType -> spawnerMap.put(entityType,yml.getItemStack(entityType)));

    }

    public void saveSpawner(String entityType, ItemStack spawner) {

        yml.set(entityType, spawner);
        plugin.getLogger().info("Spawner saved in spawners.yml: " + entityType);
    }

    public boolean isSpawnerSaved(String entityType) {
        return yml.isItemStack("spawners." + entityType);
    }

    public ItemStack getSpawner(String entityType) {
        return yml.getItemStack("spawners." + entityType);
    }
}
