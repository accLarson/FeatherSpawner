package net.zerek.featherspawners.commands;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.zerek.featherspawners.FeatherSpawners;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SpawnerCommand implements CommandExecutor {

    private final FeatherSpawners plugin;
    private final List<String> entityTypes = Arrays.stream(EntityType.values()).map(Enum::toString).collect(Collectors.toList());
    MiniMessage mm = MiniMessage.miniMessage();
    Map<String,String> messages;


    public SpawnerCommand(FeatherSpawners plugin) {
        this.plugin = plugin;
        this.messages = plugin.getMessagesFileManager().getMessagesMap();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        switch (args[0]) {

            case "designate":

                // Check if sender is a player
                if (sender instanceof Player) {

                    // Check if sender has permission
                    if (sender.hasPermission("feather.spawners.designate")) {

                        // Check if player provided correct amount of arguments
                        if (args.length == 2) {

                            ItemStack itemStack = ((Player) sender).getEquipment().getItemInMainHand();

                            // Check if sender is holding a spawner
                            if (itemStack.getType() == Material.SPAWNER) {

                                //check if sender has specified a valid entity type
                                if (entityTypes.contains(args[1].toUpperCase())) {

                                    if (plugin.getSpawnerFileManager().saveSpawner(args[1].toUpperCase(),itemStack)) {

                                       sender.sendMessage(mm.deserialize(messages.get("SpawnerDesignated"), Placeholder.unparsed("entitytype",args[1].toUpperCase())));
                                    }

                                    //inform sender that the specified spawner was not designated.
                                    else sender.sendMessage(mm.deserialize(messages.get("ErrorSpawnerNotDesignated")));
                                }
                                // Inform player they have provided an invalid entity type.
                                else sender.sendMessage(mm.deserialize(messages.get("ErrorEntityTypeInvalid")));
                            }
                            // Inform player they are not holding a spawner.
                            else sender.sendMessage(mm.deserialize(messages.get("ErrorNoSpawnerInHand")));
                        }
                        // Inform player that they did not provide the correct amount of arguments.
                        else sender.sendMessage(mm.deserialize(messages.get("ErrorInvalidArgumentCount")));
                    }
                    // Inform player they do not have permission.
                    else sender.sendMessage(mm.deserialize(messages.get("ErrorNoPermission")));
                }
                // Inform sender that this is a player command.
                else sender.sendMessage(mm.deserialize(messages.get("ErrorPlayerCommand")));




                break;

            case "set":

                // Check if sender is a player
                if (sender instanceof Player) {

                    // Check if sender has permission
                    if (sender.hasPermission("feather.spawners.set")) {

                        // Check if player provided correct amount of arguments
                        if (args.length == 2) {

                            ItemStack itemStack = ((Player) sender).getEquipment().getItemInMainHand();

                            // Check if sender is holding a spawner
                            if (itemStack.getType() == Material.SPAWNER) {

                                //check if sender has specified a valid entity type
                                if (entityTypes.contains(args[1].toUpperCase())) {

                                    // Check if sender has specified a settable spawner type
                                    if (plugin.getConfigManager().getSettableEntityTypes().contains(EntityType.valueOf(args[1].toUpperCase()))) {

                                        ((Player) sender).getEquipment().setItemInMainHand(plugin.getSpawnerFileManager().getSpawner(args[1].toUpperCase()));

                                        sender.sendMessage(mm.deserialize(messages.get("SpawnerSet"), Placeholder.unparsed("entitytype",args[1].toUpperCase())));

                                    }
                                    // Inform player they have provided an entity type which is not permitted to set.
                                    else sender.sendMessage(mm.deserialize(messages.get("ErrorEntityTypeNotAllowed")));
                                }
                                // Inform player they have provided an invalid entity type.
                                else sender.sendMessage(mm.deserialize(messages.get("ErrorEntityTypeInvalid")));
                            }
                            // Inform player they are not holding a spawner.
                            else sender.sendMessage(mm.deserialize(messages.get("ErrorNoSpawnerInHand")));
                        }
                        // Inform player that they did not provide the correct amount of arguments.
                        else sender.sendMessage(mm.deserialize(messages.get("ErrorInvalidArgumentCount")));
                    }
                    // Inform player they do not have permission.
                    else sender.sendMessage(mm.deserialize(messages.get("ErrorNoPermission")));
                }
                // Inform sender that this is a player command.
                else sender.sendMessage(mm.deserialize(messages.get("ErrorPlayerCommand")));

                break;

            case "give":

                // Check if sender has permission
                if (sender.hasPermission("feather.spawners.give")) {

                    // Check if player provided correct amount of arguments
                    if (args.length == 3) {

                        // Check if player specified to give the spawner to, is online.
                        if (plugin.getServer().getOfflinePlayer(args[1]).isOnline()) {

                            // Check if the entity type specified is saved in spawners.yml
                            if (plugin.getSpawnerFileManager().isSpawnerSaved(args[2])) {

                                plugin.getServer().getPlayer(args[1]).getInventory().addItem(plugin.getSpawnerFileManager().getSpawner(args[2]));

                                plugin.getLogger().info(sender + " gave " + args[1] + " a " + args[2] + "spawner");
                            }
                            // Inform sender that the specified spawner type is not on file. (suggest designate)
                            else sender.sendMessage(mm.deserialize(messages.get("ErrorEntityTypeNotOnFile")));
                        }
                        // Inform sender that the specified player is not online.
                        else sender.sendMessage(mm.deserialize(messages.get("ErrorPlayerOffline")));
                    }
                    // Inform player that they did not provide the correct amount of arguments.
                    else sender.sendMessage(mm.deserialize(messages.get("ErrorInvalidArgumentCount")));
                }
                // Inform sender they do not have permission.
                else sender.sendMessage(mm.deserialize(messages.get("ErrorNoPermission")));

                break;
        }

        return false;
    }
}