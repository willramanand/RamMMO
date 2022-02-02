package com.gmail.willramanand.RamMMO.commands;

import com.gmail.willramanand.RamMMO.RamMMO;
import com.gmail.willramanand.RamMMO.enums.Passive;
import com.gmail.willramanand.RamMMO.enums.Passives;
import com.gmail.willramanand.RamMMO.player.MMOPlayer;
import com.gmail.willramanand.RamMMO.ui.PassivesScreen;
import com.gmail.willramanand.RamMMO.utils.ColorUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PassiveCommand extends SubCommand {

    private final RamMMO plugin;

    public PassiveCommand(RamMMO plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onCommand(Player player, String[] args) {
        if (player == null) {
            return;
        }

        PassivesScreen passivesScreen = new PassivesScreen(plugin, player);
        player.openInventory(passivesScreen.getInventory());
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
        return args;
    }

    @Override
    public List<String> getSecondaryArguments(String[] args) {
        List<String> arg = new ArrayList<>();
        return arg;
    }
}
