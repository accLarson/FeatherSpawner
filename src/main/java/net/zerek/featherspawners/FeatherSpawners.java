package net.zerek.featherspawners;

import net.zerek.featherspawners.commands.SpawnerCommand;
import net.zerek.featherspawners.commands.SpawnerTabCompleter;
import net.zerek.featherspawners.listeners.BlockBreakListener;
import net.zerek.featherspawners.listeners.BlockPlaceListener;
import net.zerek.featherspawners.managers.ConfigManager;
import net.zerek.featherspawners.managers.MessagesManager;
import net.zerek.featherspawners.managers.SpawnersManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class FeatherSpawners extends JavaPlugin {

    private ConfigManager configManager;

    private SpawnersManager spawnerFileManager;

    private MessagesManager messagesFileManager;

    @Override
    public void onEnable() {

        this.saveDefaultConfig();

        this.saveResource("spawners.yml",false);

        this.saveResource("messages.yml",false);


        configManager = new ConfigManager(this);

        spawnerFileManager = new SpawnersManager(this);

        messagesFileManager = new MessagesManager(this);


        this.getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);

        this.getServer().getPluginManager().registerEvents(new BlockPlaceListener(this),this);


        this.getCommand("spawner").setExecutor(new SpawnerCommand(this));

        this.getCommand("spawner").setTabCompleter(new SpawnerTabCompleter(this));

    }

    @Override
    public void onDisable() {
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public SpawnersManager getSpawnerFileManager() {
        return spawnerFileManager;
    }

    public MessagesManager getMessagesFileManager() {
        return messagesFileManager;
    }
}
