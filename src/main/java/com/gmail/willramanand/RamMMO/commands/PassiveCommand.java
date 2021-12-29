package com.gmail.willramanand.RamMMO.commands;

import com.gmail.willramanand.RamMMO.RamMMO;
import com.gmail.willramanand.RamMMO.enums.Commands;
import com.gmail.willramanand.RamMMO.enums.Passive;
import com.gmail.willramanand.RamMMO.enums.Passives;
import com.gmail.willramanand.RamMMO.player.MMOPlayer;
import com.gmail.willramanand.RamMMO.utils.ColorUtils;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PassiveCommand extends SubCommand {

    private final RamMMO plugin;
    private List<String> secondArgs;

    public PassiveCommand(RamMMO plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onCommand(Player player, String[] args) {
        if (player == null) {
            return;
        }

        MMOPlayer mmoPlayer = plugin.getPlayerManager().getPlayerData(player);

        Passive passive = null;
        if (args.length == 3) {
            try {
                if (args[1].equalsIgnoreCase("agility")) {

                    if (args[2].equalsIgnoreCase("speed")) {
                        passive = Passives.AGILITY_SPEED;
                    } else if (args[2].equalsIgnoreCase("jump_boost")) {
                        passive = Passives.AGILITY_JUMP_BOOST;
                    } else {
                        player.sendMessage(ColorUtils.colorMessage("&4Valid agility arguments: speed, jump_boost"));
                    }

                } else if (args[1].equalsIgnoreCase("fishing")) {

                    if (args[2].equalsIgnoreCase("dolphins_grace")) {
                        passive = Passives.FISHING_DOLPHINS_GRACE;
                    } else if (args[2].equalsIgnoreCase("conduit_power")) {
                        passive = Passives.FISHING_CONDUIT_POWER;
                    } else {
                        player.sendMessage(ColorUtils.colorMessage("&4Valid agility arguments: dolphins_grace, conduit_power"));
                    }

                } else if (args[1].equalsIgnoreCase("excavation")) {

                    if (args[2].equalsIgnoreCase("haste")) {
                        passive = Passives.EXCAVATION_HASTE;
                    } else {
                        player.sendMessage(ColorUtils.colorMessage("&4Valid agility arguments: haste"));
                    }

                } else if (args[1].equalsIgnoreCase("foraging")) {

                    if (args[2].equalsIgnoreCase("haste")) {
                        passive = Passives.FORAGING_HASTE;
                    } else {
                        player.sendMessage(ColorUtils.colorMessage("&4Valid agility arguments: haste"));
                    }

                } else if (args[1].equalsIgnoreCase("mining")) {

                    if (args[2].equalsIgnoreCase("haste")) {
                        passive = Passives.MINING_HASTE;
                    } else {
                        player.sendMessage(ColorUtils.colorMessage("&4Valid agility arguments: haste"));
                    }

                }

            } catch (IllegalArgumentException e) {
                player.sendMessage(ColorUtils.colorMessage("&4Not valid argument!"));
            }
            if (passive != null) {
                if (mmoPlayer.getPassives(passive)) {
                    mmoPlayer.setPassives(passive, false);
                    player.sendMessage(ColorUtils.colorMessage("&d" + passive.getPassive() + " &efor &d" + passive.getSkill() + " &ehas been §cdisabled"));
                } else {
                    mmoPlayer.setPassives(passive, true);
                    player.sendMessage(ColorUtils.colorMessage("&d" + passive.getPassive() + " &efor &d" + passive.getSkill() + " &ehas been §aenabled"));
                }
            }
        } else {
            player.sendMessage(ColorUtils.colorMessage("&b/mmo passive &d<skill> §a<effect>"));
        }
    }

    @Override
    public String name() {
        return Commands.PASSIVE.getName();
    }

    @Override
    public String info() {
        return "Toggle passive effects for various skills.";
    }

    @Override
    public List<String> aliases() {
        List<String> aliases = new ArrayList<>();
        aliases.add("p");
        return aliases;
    }

    @Override
    public List<String> getPrimaryArguments() {
        List<String> args = new ArrayList<>();

        args.add("agility");
        args.add("excavation");
        args.add("fishing");
        args.add("foraging");
        args.add("mining");

        return args;
    }

    @Override
    public List<String> getSecondaryArguments(String[] args) {
        List<String> arg = new ArrayList<>();

        if (args[1].equalsIgnoreCase("agility")) {
            arg.add("speed");
            arg.add("jump_boost");
        } else if (args[1].equalsIgnoreCase("fishing")) {
            arg.add("conduit_power");
            arg.add("dolphins_grace");
        } else {
            arg.add("haste");
        }
        return arg;
    }
}
