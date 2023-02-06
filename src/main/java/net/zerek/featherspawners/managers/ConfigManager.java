package net.zerek.featherspawners.managers;

import net.zerek.featherspawners.FeatherSpawners;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

public class ConfigManager {

    private final FeatherSpawners plugin;

    private final List<Material> silkTouchTools = new ArrayList<>();

    private final List<EntityType> settableEntityTypes = new ArrayList<>();


    public ConfigManager(FeatherSpawners plugin) {

        this.plugin = plugin;

        this.init();
    }

    private void init() {

        plugin.getConfig().getStringList("approved-silk-touch-tools").forEach(t -> silkTouchTools.add(Material.valueOf(t)));

        plugin.getConfig().getStringList("settable-spawners").forEach(e -> settableEntityTypes.add(EntityType.valueOf(e)));
    }

    public List<Material> getSilkTouchTools() {
        return silkTouchTools;
    }

    public List<EntityType> getSettableEntityTypes() {
        return settableEntityTypes;
    }






}
