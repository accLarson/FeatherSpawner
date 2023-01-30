package net.zerek.featherspawners.commands;

import net.zerek.featherspawners.FeatherSpawners;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class SpawnerTabCompleter implements TabCompleter {

    private final FeatherSpawners plugin;
    private final List<String> allEntityTypes = Arrays.stream(EntityType.values()).map(Enum::toString).collect(Collectors.toList());
    private final List<String> approvedEntityTypes = new ArrayList<>();


    public SpawnerTabCompleter(FeatherSpawners plugin) {
        this.plugin = plugin;
        plugin.getConfigManager().getSettableEntityTypes().forEach(entityType -> approvedEntityTypes.add(entityType.name()));
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> options = new ArrayList<>();

        if (sender.hasPermission("feather.spawners.designate")) options.add(0, "designate");

        if (sender.hasPermission("feather.spawners.give")) options.add(0, "give");

        if (sender.hasPermission("feather.spawners.set")) options.add(0, "set");

        if (args.length == 1) {

            List<String> match = new ArrayList<>();

            for (String option : options) if (option.toLowerCase().startsWith(args[0].toLowerCase())) match.add(option);

            return match;
        }

        List<String> match = new ArrayList<>();

        switch (args[0]) {

            case "designate":

                if (args.length == 2 && sender.hasPermission("feather.spawners.designate")) {

                    for (String entityType : allEntityTypes) if (entityType.toUpperCase().startsWith(args[1].toUpperCase())) match.add(entityType);

                    return match;
                }

            case "set":

                if (args.length == 2 && sender.hasPermission("feather.spawners.set")) {

                    for (String entityType : approvedEntityTypes) if (entityType.toUpperCase().startsWith(args[1].toUpperCase())) match.add(entityType);

                    return match;
                }

            case "give":

                if (args.length == 2 && sender.hasPermission("feather.spawners.give")) {

                    return null;
                }

                if (args.length == 3 && sender.hasPermission("feather.spawners.give")) {

                    for (String entityType : plugin.getSpawnerFileManager().getSpawnerOnFileList()) if (entityType.toUpperCase().startsWith(args[2].toUpperCase())) match.add(entityType);

                    return match;
                }
        }
        return new ArrayList<>();
    }
}
