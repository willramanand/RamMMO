package com.gmail.willramanand.RamMMO.commands;

import org.bukkit.entity.Player;

import java.util.List;

public abstract class SubCommand {

    public SubCommand() {

    }

    public abstract void onCommand(Player player, String[] args);

    public abstract String name();

    public abstract String info();

    public abstract String[] aliases();

    public abstract List<String> getSubCommandArguments();
}
