package net.zerek.featherspawners.listeners;

import net.zerek.featherspawners.FeatherSpawners;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BlockBreakListener implements Listener {

    private final FeatherSpawners plugin;

    public BlockBreakListener(FeatherSpawners plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){

        // Check if the block broken is a spawner.
        if (event.getBlock().getType() == Material.SPAWNER) {

            Block block = event.getBlock();

            ItemStack tool = event.getPlayer().getInventory().getItemInMainHand();

            // Check if the tool being used is a pickaxe.
            if (plugin.getConfigManager().getSilkTouchTools().contains(tool.getType())) {

                // Check if the approved tool has silk touch enchantment
                if (tool.getEnchantments().containsKey(Enchantment.SILK_TOUCH)) {

                    ItemStack itemStack = new ItemStack(Material.SPAWNER,1);

                    BlockState blockState = block.getState();

                    itemStack.setItemMeta((ItemMeta) blockState);

                    block.getDrops().add(itemStack);
                }
            }
        }
    }
}
