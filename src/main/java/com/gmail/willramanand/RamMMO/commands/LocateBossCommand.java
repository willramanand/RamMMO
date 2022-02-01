package com.gmail.willramanand.RamMMO.commands;

import com.gmail.willramanand.RamMMO.RamMMO;
import com.gmail.willramanand.RamMMO.boss.BossManager;
import com.gmail.willramanand.RamMMO.boss.Bosses;
import com.gmail.willramanand.RamMMO.utils.ColorUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class LocateBossCommand extends SubCommand {

    private final RamMMO plugin;

    public LocateBossCommand(RamMMO plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onCommand(Player player, String[] args) {
        if (player == null) {
            return;
        }
        try {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("locateboss")) {
                    Location bossLoc = BossManager.getBossLocation();
                    if (bossLoc == null) {
                        player.sendMessage(ColorUtils.colorMessage("&6No boss in the world!"));
                    } else {
                        player.sendMessage(ColorUtils.colorMessage(String.format("%s &6has been spotted in World:&d %s &6at coords:&d %d, %d, %d", BossManager.getCurrentBoss().getCustomName(), bossLoc.getWorld().
                                getName(), bossLoc.getBlockX(), bossLoc.getBlockY(), bossLoc.getBlockZ())));
                    }
                }
            } else {
                player.sendMessage("§e/mmo §dlocateboss");
            }
        } catch (IllegalArgumentException e) {
            player.sendMessage(ChatColor.DARK_RED + "Not valid argument!");
        }
    }

    @Override
    public String name() {
        return Commands.LOCATEBOSS.getName();
    }

    @Override
    public String info() {
        return "Allows players to locate the current boss.";
    }

    @Override
    public List<String> aliases() {
        return new ArrayList<>();
    }

    @Override
    public List<String> getPrimaryArguments() {
        List<String> args = new ArrayList<>();
        return args;
    }

    @Override
    public List<String> getSecondaryArguments(String[] args) {
        return null;
    }
}
