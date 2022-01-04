package com.gmail.willramanand.RamMMO.commands;

import com.gmail.willramanand.RamMMO.RamMMO;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class BossCommand extends SubCommand {

    private final RamMMO plugin;

    public BossCommand(RamMMO plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onCommand(Player player, String[] args) {
        if (player == null) {
            return;
        }

        if (player.hasPermission("rammmo.boss")) {
            Location loc = player.getLocation();

            try {
                if (args.length == 2) {
                    if (args[1].equalsIgnoreCase("wolf")) {
                    } else {
                        player.sendMessage("§4Valid agility arguments: wolf");
                    }
                } else {
                    player.sendMessage("§e/mmo §d<boss> §a<type>");
                }
            } catch (IllegalArgumentException e) {
                player.sendMessage(ChatColor.DARK_RED + "Not valid argument!");
            }
        } else {
            player.sendMessage(ChatColor.DARK_RED + "You do not have that permission!");
        }
    }

    @Override
    public String name() {
        return Commands.BOSS.getName();
    }

    @Override
    public String info() {
        return "Allows the spawning of boss creatures by Admins";
    }

    @Override
    public List<String> aliases() {
        return new ArrayList<>();
    }

    @Override
    public List<String> getPrimaryArguments() {
        List<String> args = new ArrayList<>();

        args.add("wolf");
        return args;
    }

    @Override
    public List<String> getSecondaryArguments(String[] args) {
        return null;
    }
}
