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

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SpawnerCommand implements CommandExecutor {

    private final FeatherSpawners plugin;

    private final List<String> entityTypes = Arrays.stream(EntityType.values()).map(Enum::toString).collect(Collectors.toList());

    public SpawnerCommand(FeatherSpawners plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        switch (args[0]) {

            case "designate":

                // Check if sender is a player and if sender has permission
                if (sender instanceof Player && sender.hasPermission("feather.spawners.designate")) {

                    ItemStack itemStack = ((Player) sender).getEquipment().getItemInMainHand();

                    // Check if sender is holding a spawner
                    if (itemStack.getType() == Material.SPAWNER) {

                        //check if sender has specified a valid entity type
                        if (entityTypes.contains(args[1].toUpperCase())) {

                            if (plugin.getSpawnerFileManager().saveSpawner(args[1].toUpperCase(),itemStack)) {

                                //inform sender that the specified spawner was designated
                            }
                            else {

                                //inform sender that the specified spawner
                            }
                        }
                    }
                }

                break;

            case "set":

                // Check if sender is a player and if sender has permission
                if (sender instanceof Player && sender.hasPermission("feather.spawners.set")) {

                    ItemStack itemStack = ((Player) sender).getEquipment().getItemInMainHand();

                    // Check if sender is holding a spawner
                    if (itemStack.getType() == Material.SPAWNER) {

                        //check if sender has specified a valid entity type
                        if (entityTypes.contains(args[1].toUpperCase())) {

                            // Check if sender has specified an approved entity type for setting
                            if (plugin.getConfigManager().getSettableEntityTypes().contains(EntityType.valueOf(args[1].toUpperCase()))) {

                                ((Player) sender).getEquipment().setItemInMainHand(plugin.getSpawnerFileManager().getSpawner(args[1].toUpperCase()));
                            }
                        }
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
