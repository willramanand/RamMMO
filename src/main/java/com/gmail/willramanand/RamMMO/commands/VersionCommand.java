package com.gmail.willramanand.RamMMO.commands;

import com.gmail.willramanand.RamMMO.RamMMO;
import com.gmail.willramanand.RamMMO.utils.ColorUtils;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class VersionCommand extends SubCommand {

    private final RamMMO plugin;

    public VersionCommand(RamMMO plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onCommand(Player player, String[] args) {
        player.sendMessage(ColorUtils.colorMessage("&6---- &b" + plugin.getName() + "&6----"));
        player.sendMessage(ColorUtils.colorMessage("&dAuthor: &eWillRam"));
        player.sendMessage(ColorUtils.colorMessage("&dVersion: &e" + plugin.getDescription().getVersion()));
        player.sendMessage(ColorUtils.colorMessage("&e" + plugin.getDescription().getDescription()));
    }

    @Override
    public String name() {
        return Commands.VERSION.getName();
    }

    @Override
    public String info() {
        return "Displays plugin information and version.";
    }

    @Override
    public List<String> aliases() {
        List<String> aliases = new ArrayList<>();
        aliases.add("v");
        return aliases;
    }

    @Override
    public List<String> getPrimaryArguments() {
        return null;
    }

    @Override
    public List<String> getSecondaryArguments(String[] args) {
        return null;
    }
}
