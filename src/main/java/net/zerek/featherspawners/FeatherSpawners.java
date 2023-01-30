package net.zerek.featherspawners;

import net.zerek.featherspawners.commands.SpawnerCommand;
import net.zerek.featherspawners.listeners.BlockBreakListener;
import net.zerek.featherspawners.managers.ConfigManager;
import net.zerek.featherspawners.managers.SpawnersFileManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class FeatherSpawners extends JavaPlugin {

    private ConfigManager configManager;
    private SpawnersFileManager spawnerFileManager;

    @Override
    public void onEnable() {

        this.saveDefaultConfig();
        this.saveResource("spawners.yml",false);

        configManager = new ConfigManager(this);
        spawnerFileManager = new SpawnersFileManager(this);

        this.getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);
        this.getCommand("spawner").setExecutor(new SpawnerCommand(this));

    }

    @Override
    public void onDisable() {
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }
    public SpawnersFileManager getSpawnerFileManager() {
        return spawnerFileManager;
    }
}
