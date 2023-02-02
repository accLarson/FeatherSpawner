package net.zerek.featherspawners.listeners;

import net.zerek.featherspawners.FeatherSpawners;
import org.bukkit.GameMode;
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
        if (event.getBlock().getType() != Material.SPAWNER) return;

        Player player = event.getPlayer();

        // Check if player is in survival mode.
        if (player.getGameMode() != GameMode.SURVIVAL) return;

        // Check if player has permission.
        if (!player.hasPermission("feather.spawners.silktouch")) return;

        ItemStack tool = player.getInventory().getItemInMainHand();

        // Check if the tool being used is a pickaxe.
        if (!plugin.getConfigManager().getSilkTouchTools().contains(tool.getType())) return;

        // Check if the approved tool has silk touch enchantment
        if (!tool.getEnchantments().containsKey(Enchantment.SILK_TOUCH)) return;

        CreatureSpawner spawner = (CreatureSpawner) event.getBlock().getState();

        String spawnerEntity = String.valueOf(spawner.getSpawnedType());

        //check if spawner being broken is stored in spawnersMap for dropping.
        if (!plugin.getSpawnerFileManager().isSpawnerSaved(spawnerEntity)) return;

        // Checks passed ----------------------------------------------------------------

        ItemStack itemStack = plugin.getSpawnerFileManager().getSpawner(spawnerEntity);

        event.setExpToDrop(0);

        spawner.getLocation().getWorld().dropItem(spawner.getLocation(), itemStack);

        plugin.getLogger().info(player.getName() + "Mined a " + spawnerEntity + " spawner at: "
                + spawner.getLocation().getBlockX() + " "
                + spawner.getLocation().getBlockY() + " "
                + spawner.getLocation().getBlockZ() + ".");
    }
}
