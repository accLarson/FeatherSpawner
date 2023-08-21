package net.zerek.featherspawners.listeners;

import net.zerek.featherspawners.FeatherSpawners;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;


public class BlockPlaceListener implements Listener {

    private final FeatherSpawners plugin;

    public BlockPlaceListener(FeatherSpawners plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {

        // Check if the block placed is a spawner.
        if (event.getBlock().getType() != Material.SPAWNER) return;

        CreatureSpawner spawner = (CreatureSpawner) event.getBlock().getState();

        Player player = event.getPlayer();

        // Check if player is in survival mode.
        if (player.getGameMode() != GameMode.SURVIVAL) return;

        // Checks passed ----------------------------------------------------------------

        String spawnerEntity = "";

        if (plugin.getSpawnerFileManager().getSpawnersMap().containsValue(event.getItemInHand().asOne())) {

            spawnerEntity = plugin.getSpawnerFileManager().getEntityTypeFromItemStack(event.getItemInHand().asOne());

            spawner.setSpawnedType(EntityType.valueOf(spawnerEntity));
        }

        else {

            spawnerEntity = "ZOMBIE";

            spawner.setSpawnedType(EntityType.valueOf(spawnerEntity));
        }

        spawner.update();


        plugin.getLogger().info(player.getName() + " placed a " + spawnerEntity + " spawner at: "
                + spawner.getLocation().getBlockX() + " "
                + spawner.getLocation().getBlockY() + " "
                + spawner.getLocation().getBlockZ() + ".");

    }
}
