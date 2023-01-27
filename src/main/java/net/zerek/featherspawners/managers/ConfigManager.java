package net.zerek.featherspawners.managers;

import net.zerek.featherspawners.FeatherSpawners;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ConfigManager {

    private final FeatherSpawners plugin;

    private final List<Material> silkTouchTools = new ArrayList<>();

    public ConfigManager(FeatherSpawners plugin) {
        this.plugin = plugin;
        this.init();
    }

    private void init() {
        plugin.getConfig().getStringList("silk-touch.tools").forEach(t -> silkTouchTools.add(Material.valueOf(t)));
    }

    public List<Material> getSilkTouchTools() {
        return silkTouchTools;
    }






}
