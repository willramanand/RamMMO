package com.gmail.willramanand.RamMMO.commands;

import com.gmail.willramanand.RamMMO.RamMMO;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ForagingCommand extends SubCommand {

    RamMMO plugin;

    public ForagingCommand(RamMMO plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onCommand(Player player, String[] args) {
        if (!(player instanceof Player)) {
            return;
        }

        String arg0 = plugin.getCommandManager().foraging;
        String arg1 = "";
        try {
            if (args.length >= 1) {
                if (args[1].equalsIgnoreCase("haste")) {
                    arg1 = "haste";
                } else {
                    player.sendMessage("§4Valid agility arguments: haste");
                }

                if (plugin.getConfig().getBoolean("player." + player.getUniqueId() + "." + arg0 + "." + arg1)) {
                    plugin.getConfig().set("player." + player.getUniqueId() + "." + arg0 + "." + arg1, false);
                    player.sendMessage("§eAbility effect for §d" + arg1 + " §ein skill §d" + arg0 + " §ehas been §cdisabled");
                } else {
                    plugin.getConfig().set("player." + player.getUniqueId() + "." + arg0 + "." + arg1, true);
                    player.sendMessage("§eAbility effect for §d" + arg1 + " §ein skill §d" + arg0 + " §ehas been §aenabled");
                }
                plugin.saveConfig();
            } else {
                player.sendMessage("§e/mmo §d<skill> §a<effect>");
            }
        } catch (IllegalArgumentException e) {
            player.sendMessage(ChatColor.DARK_RED + "Not valid argument!");
        }
        return;
    }

    @Override
    public String name() {
        return plugin.getCommandManager().foraging;
    }

    @Override
    public String info() {
        return "Enable and disabled passive effects for Foraging.";
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }

    @Override
    public List<String> getSubCommandArguments() {
        List<String> args = new ArrayList<>();

        args.add("haste");

        return args;
    }
}
