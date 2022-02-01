package com.gmail.willramanand.RamMMO.commands;

import com.gmail.willramanand.RamMMO.RamMMO;
import com.gmail.willramanand.RamMMO.boss.BossManager;
import com.gmail.willramanand.RamMMO.boss.Bosses;
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
            try {
                if (args.length == 2) {
                    if (args[1].equalsIgnoreCase("skeleton")) {
                        BossManager.spawnBoss(Bosses.HEADLESS_HORSEMAN, player.getLocation());
                    } else if (args[1].equalsIgnoreCase("ravager")) {
                        BossManager.spawnBoss(Bosses.THE_MINOTAUR, player.getLocation());
                    } else if (args[1].equalsIgnoreCase("phantom")) {
                        BossManager.spawnBoss(Bosses.THE_GHOST, player.getLocation());
                    } else {
                        player.sendMessage("§4Valid agility arguments: skeleton, minotaur, phantom");
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

        args.add("skeleton");
        return args;
    }

    @Override
    public List<String> getSecondaryArguments(String[] args) {
        return null;
    }
}
