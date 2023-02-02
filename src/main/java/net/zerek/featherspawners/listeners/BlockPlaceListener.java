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
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class BlockPlaceListener implements Listener {

    private final FeatherSpawners plugin;

    public BlockPlaceListener(FeatherSpawners plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {

        // Check if the block broken is a spawner.
        if (event.getBlock().getType() != Material.SPAWNER) return;

        CreatureSpawner spawner = (CreatureSpawner) event.getBlock().getState();

        if (spawner.getSpawnedType() == EntityType.SKELETON) {

            plugin.getLogger().info("SKELETON SPAWNER PLACED");
        }

        Player player = event.getPlayer();

        // Check if player is in survival mode.
        if (player.getGameMode() != GameMode.SURVIVAL) return;

        // Check if spawner placed has equivalent ItemStack stored in spawners.yml
        if (!plugin.getSpawnerFileManager().getSpawnersMap().containsValue(event.getItemInHand().asOne())) return;


        // Checks passed ----------------------------------------------------------------

        String spawnerEntity = plugin.getSpawnerFileManager().getEntityTypeFromItemStack(event.getItemInHand().asOne());

        spawner.setSpawnedType(EntityType.valueOf(spawnerEntity));
    }
}
