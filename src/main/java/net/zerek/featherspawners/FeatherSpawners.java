package net.zerek.featherspawners;

import net.zerek.featherspawners.commands.SpawnerCommand;
import net.zerek.featherspawners.commands.SpawnerTabCompleter;
import net.zerek.featherspawners.listeners.BlockBreakListener;
import net.zerek.featherspawners.listeners.BlockPlaceListener;
import net.zerek.featherspawners.managers.ConfigFileManager;
import net.zerek.featherspawners.managers.MessagesFileManager;
import net.zerek.featherspawners.managers.SpawnersFileManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class FeatherSpawners extends JavaPlugin {

    private ConfigFileManager configManager;

    private SpawnersFileManager spawnerFileManager;

    private MessagesFileManager messagesFileManager;

    @Override
    public void onEnable() {

        this.saveDefaultConfig();

        this.saveResource("spawners.yml",false);

        this.saveResource("messages.yml",false);


        configManager = new ConfigFileManager(this);

        spawnerFileManager = new SpawnersFileManager(this);

        messagesFileManager = new MessagesFileManager(this);


        this.getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);

        this.getServer().getPluginManager().registerEvents(new BlockPlaceListener(this),this);


        this.getCommand("spawner").setExecutor(new SpawnerCommand(this));

        this.getCommand("spawner").setTabCompleter(new SpawnerTabCompleter(this));

    }

    @Override
    public void onDisable() {
    }

    public ConfigFileManager getConfigManager() {
        return configManager;
    }

    public SpawnersFileManager getSpawnerFileManager() {
        return spawnerFileManager;
    }

    public MessagesFileManager getMessagesFileManager() {
        return messagesFileManager;
    }
}
