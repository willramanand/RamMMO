package com.gmail.willramanand.RamMMO.commands;

import com.gmail.willramanand.RamMMO.RamMMO;
import com.gmail.willramanand.RamMMO.boss.WolfBoss;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class BossCommand extends SubCommand {

    private RamMMO plugin;

    public BossCommand(RamMMO plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onCommand(Player player, String[] args) {
        if (!(player instanceof Player)) {
            return;
        }

        Location loc = player.getLocation();

        try {
            if (args.length == 2) {
                if (args[1].equalsIgnoreCase("wolf")) {
                    new WolfBoss(plugin, loc);
                } else {
                    player.sendMessage("§4Valid agility arguments: wolf");
                }
            } else {
                player.sendMessage("§e/mmo §d<boss> §a<type>");
            }
        } catch (IllegalArgumentException e) {
            player.sendMessage(ChatColor.DARK_RED + "Not valid argument!");
        }
        return;
    }

    @Override
    public String name() {
        return plugin.getCommandManager().boss;
    }

    @Override
    public String info() {
        return "Allows the spawning of boss creatures by Admins";
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }

    @Override
    public List<String> getSubCommandArguments() {
        List<String> args = new ArrayList<>();

        args.add("wolf");
        return args;
    }
}
