package com.gmail.willramanand.RamMMO.commands;

import com.gmail.willramanand.RamMMO.RamMMO;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AgilityCommand extends SubCommand {

    RamMMO plugin;

    public AgilityCommand(RamMMO plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onCommand(Player player, String[] args) {
        if (player == null) {
            return;
        }

        FileConfiguration config = plugin.getConfigManager().load(player.getUniqueId());

        String arg0 = plugin.getCommandManager().agility;
        String arg1 = "";
        try {
            if (args.length == 2) {
                if (args[1].equalsIgnoreCase("speed")) {
                    arg1 = "speed";
                } else if (args[1].equalsIgnoreCase("jump_boost")) {
                    arg1 = "jump_boost";
                } else {
                    player.sendMessage("§4Valid agility arguments: speed, jump_boost");
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
                player.sendMessage("§e/mmo §dagility §a<effect>");
            }
        } catch (IllegalArgumentException e) {
            player.sendMessage(ChatColor.DARK_RED + "Not valid argument!");
        } catch (IOException e) {
            player.sendMessage(ChatColor.DARK_RED + "I do not know!");
        }
    }

    @Override
    public String name() {
        return plugin.getCommandManager().agility;
    }

    @Override
    public String info() {
        return "Toggle passive effects for Agility.";
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }

    @Override
    public List<String> getSubCommandArguments() {
        List<String> args = new ArrayList<>();

        args.add("jump_boost");
        args.add("speed");

        return args;
    }
}
