package net.zerek.featherspawners.listeners;

import net.zerek.featherspawners.FeatherSpawners;
import net.zerek.featherspawners.managers.ConfigManager;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;


public class BlockBreakListener implements Listener {

    private final FeatherSpawners plugin;

    public BlockBreakListener(FeatherSpawners plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){

        // Check if the block broken is a spawner.
        if (event.getBlock().getType() == Material.SPAWNER) {

            Player player = event.getPlayer();

            // Check if player is in survival mode and has permission to mine spawners.
            if (player.getGameMode() == GameMode.SURVIVAL && player.hasPermission("feather.spawners.silktouch")) {

                ItemStack tool = player.getInventory().getItemInMainHand();

                // Check if the tool being used is a pickaxe.
                if (plugin.getConfigManager().getSilkTouchTools().contains(tool.getType())) {

                    // Check if the approved tool has silk touch enchantment
                    if (tool.getEnchantments().containsKey(Enchantment.SILK_TOUCH)) {

                        CreatureSpawner spawner = (CreatureSpawner) event.getBlock().getBlockData();
                        String spawnerEntity = String.valueOf(spawner.getSpawnedType());

                        //check if spawner being broken is stored in spawnersMap for dropping.
                        if (plugin.getSpawnerFileManager().isSpawnerSaved(spawnerEntity)) {

                            ItemStack itemStack = plugin.getSpawnerFileManager().getSpawner(spawnerEntity);
                            Location loc = spawner.getLocation();
                            event.setExpToDrop(0);
                            loc.getWorld().dropItem(loc, itemStack);

                        }
                    }
                }
            }
        }
    }
}
