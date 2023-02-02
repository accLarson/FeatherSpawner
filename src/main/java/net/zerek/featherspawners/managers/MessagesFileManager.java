package net.zerek.featherspawners.managers;

import net.zerek.featherspawners.FeatherSpawners;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MessagesFileManager {

    private final FeatherSpawners plugin;

    File file;

    private FileConfiguration yml;

    private final Map<String, String> messagesMap = new HashMap<>();

    public MessagesFileManager(FeatherSpawners plugin) {

        this.plugin = plugin;

        this.init();
    }

    private void init() {
        file = new File(plugin.getDataFolder() + File.separator + "messages.yml");

        yml = YamlConfiguration.loadConfiguration(file);

        this.generateMessagesMap();
    }

    private void generateMessagesMap() {

        yml.getKeys(false).forEach(m -> messagesMap.put(m, yml.getString(m)));
    }

    public Map<String, String> getMessagesMap() {
        return messagesMap;
    }


}
