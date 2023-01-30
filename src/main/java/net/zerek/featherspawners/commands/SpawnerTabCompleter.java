package net.zerek.featherspawners.commands;

import net.zerek.featherspawners.FeatherSpawners;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class SpawnerTabCompleter implements TabCompleter {

    private final FeatherSpawners plugin;
    private final List<String> entityTypes = Arrays.stream(EntityType.values()).map(Enum::toString).collect(Collectors.toList());


    public SpawnerTabCompleter(FeatherSpawners plugin) {
        this.plugin = plugin;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> options = new ArrayList<>();

        if (sender instanceof Player && sender.isOp()) {
            if (sender.hasPermission("feather.spawners.designate") || sender instanceof ConsoleCommandSender) options.add(0, "designate");
            if (sender.hasPermission("feather.spawners.give") || sender instanceof ConsoleCommandSender) options.add(0, "give");
            if (sender.hasPermission("feather.spawners.set") || sender instanceof ConsoleCommandSender) options.add(0, "set");

            if (args.length == 1) {
                List<String> match = new ArrayList<>();
                for (String option : options) {
                    if (option.toLowerCase().startsWith(args[0].toLowerCase())) match.add(option);
                }
                return match;
            }

            else if (args.length == 2 && args[0].equals("designate")) {

                List<String> match = new ArrayList<>();

                for (String entityType : entityTypes) if (entityType.toUpperCase().startsWith(args[1].toUpperCase())) match.add(entityType);

                return match;
            }

            else if (args.length == 2 && args[0].equals("set")) {

                List<String> match = new ArrayList<>();

                for (String entityType : plugin.getSpawnerFileManager().getSpawnerList()) if (entityType.toUpperCase().startsWith(args[1].toUpperCase())) match.add(entityType);

                return match;
            }

            else if (args.length == 2 && args[0].equals("give")) return null;

            else if (args.length == 3 && args[0].equals("give")) {

                List<String> match = new ArrayList<>();

                for (String entityType : plugin.getSpawnerFileManager().getSpawnerList()) if (entityType.toUpperCase().startsWith(args[1].toUpperCase())) match.add(entityType);

                return match;
            }


            else return new ArrayList<>();
        }
        return options;
    }
}
