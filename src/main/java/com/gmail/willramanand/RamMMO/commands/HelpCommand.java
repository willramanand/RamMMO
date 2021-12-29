package com.gmail.willramanand.RamMMO.commands;

import com.gmail.willramanand.RamMMO.RamMMO;
import com.gmail.willramanand.RamMMO.enums.Commands;
import com.gmail.willramanand.RamMMO.utils.ColorUtils;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class HelpCommand extends SubCommand {

    private final RamMMO plugin;

    public HelpCommand(RamMMO plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onCommand(Player player, String[] args) {

        List<SubCommand> commands = plugin.getCommandManager().getCommandList();
        String prefix = Commands.MAIN.getName();

        player.sendMessage(ColorUtils.colorMessage("&6---- &b" + plugin.getName() + " Help &6----"));

        commands.forEach(command ->{
                String commandAlias = command.name();
                for (String alias: command.aliases()) {
                    if (command.aliases() != null) {
                        commandAlias += "," + alias;
                    }
                }
                player.sendMessage(ColorUtils.colorMessage("&b/" + prefix + " " + commandAlias + " &e" + command.info()));
        });
    }

    @Override
    public String name() {
        return Commands.HELP.getName();
    }

    @Override
    public String info() {
        return "Information on the commands of RamMMO.";
    }

    @Override
    public List<String> aliases() {
        List<String> alias = new ArrayList<>();
        alias.add("h");
        return alias;
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
