package com.gmail.willramanand.RamMMO.commands;

import com.gmail.willramanand.RamMMO.RamMMO;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FishingCommand extends SubCommand {

    RamMMO plugin;

    public FishingCommand(RamMMO plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onCommand(Player player, String[] args) {
        if (player == null) {
            return;
        }

        FileConfiguration config = plugin.getConfigManager().load(player.getUniqueId());

        String arg0 = plugin.getCommandManager().fishing;
        String arg1 = "";
        try {
            if (args.length == 2) {
                if (args[1].equalsIgnoreCase("dolphins_grace")) {
                    arg1 = "dolphins_grace";
                } else if (args[1].equalsIgnoreCase("conduit_power")) {
                    arg1 = "conduit_power";
                } else {
                    player.sendMessage("§4Valid agility arguments: dolphins_grace, conduit_power");
                }
                if (config.getBoolean(arg0 + "." + arg1)) {
                    config.set(arg0 + "." + arg1, false);
                    player.sendMessage("§eAbility effect for §d" + arg1 + " §ein skill §d" + arg0 + " §ehas been §cdisabled");
                } else {
                    config.set(arg0 + "." + arg1, true);
                    player.sendMessage("§eAbility effect for §d" + arg1 + " §ein skill §d" + arg0 + " §ehas been §aenabled");
                }
                config.save(new File(plugin.getDataFolder() + "/playerdata/" + player.getUniqueId() + ".yml"));
            } else {
                player.sendMessage("§e/mmo §dfishing §a<effect>");
            }
        } catch (IllegalArgumentException e) {
            player.sendMessage(ChatColor.DARK_RED + "Not valid argument!");
        } catch (IOException e) {
            player.sendMessage(ChatColor.DARK_RED + "I do not know!");
        }
    }

    @Override
    public String name() {
        return plugin.getCommandManager().fishing;
    }

    @Override
    public String info() {
        return "Toggle passive effects for Fishing.";
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }

    @Override
    public List<String> getSubCommandArguments() {
        List<String> args = new ArrayList<>();

        args.add("dolphins_grace");
        args.add("conduit_power");

        return args;
    }
}
