package net.zerek.featherspawners.commands;

import net.zerek.featherspawners.FeatherSpawners;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class SpawnerCommand implements CommandExecutor {

    private final FeatherSpawners plugin;

    public SpawnerCommand(FeatherSpawners plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        switch (args[0]) {

            case "designate":

                if (sender instanceof Player && sender.hasPermission("feather.spawners.designate")) {

                    ItemStack itemStack = ((Player) sender).getEquipment().getItemInMainHand();

                    if (itemStack.getType() == Material.SPAWNER) {

                        try {
                            EntityType entityType = EntityType.valueOf(args[1]);
                        } catch (IllegalArgumentException exp) {
                            plugin.getLogger().info("IllegalArgumentException - " + args[1]);
                            return false;
                        }

                        plugin.getSpawnerFileManager().saveSpawner(args[1],itemStack);

                    }
                }

                break;

            case "set":

                if (sender instanceof Player && sender.hasPermission("feather.spawners.set")) {

                    ItemStack itemStack = ((Player) sender).getEquipment().getItemInMainHand();

                    if (itemStack.getType() == Material.SPAWNER) {


                    }

                }

                break;

            case "give":

                if (sender.hasPermission("feather.spawners.give")) {

                    if (plugin.getServer().getOfflinePlayer(args[1]).isOnline()) {

                        if (plugin.getSpawnerFileManager().isSpawnerSaved(args[2])) {

                            plugin.getServer().getPlayer(args[1]).getInventory().addItem(plugin.getSpawnerFileManager().getSpawner(args[2]));

                            plugin.getLogger().info(sender + " gave " + args[1] + " a " + args[2] + "spawner");
                        }
                        else; //inform sender that the specified spawner type is not designated in spawners.yml.
                    }
                    else; //inform sender that the specified player is not online.
                }
                else; //inform sender that they don't have permission to run this command.

                break;
        }

        return false;
    }
}
