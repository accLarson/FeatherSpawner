package net.zerek.featherspawners;

import net.zerek.featherspawners.listeners.BlockBreakListener;
import net.zerek.featherspawners.managers.ConfigManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class FeatherSpawners extends JavaPlugin {

    private ConfigManager configManager;

    @Override
    public void onEnable() {

        this.saveDefaultConfig();

        configManager = new ConfigManager(this);

        this.getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);

    }

    @Override
    public void onDisable() {
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }
}
