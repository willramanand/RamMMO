package com.gmail.willramanand.RamMMO.commands;

import com.gmail.willramanand.RamMMO.enums.Commands;
import com.gmail.willramanand.RamMMO.items.Item;
import com.gmail.willramanand.RamMMO.items.ItemManager;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ItemTestCommand extends SubCommand {
    @Override
    public void onCommand(Player player, String[] args) {
        if (args.length == 2) {
            for (Item item : Item.values()) {
                if (args[1].equalsIgnoreCase(item.name().toLowerCase())) {
                    player.getInventory().addItem(ItemManager.getItem(item));
                }
            }
        } else if (args.length == 3) {
            for (Item item : Item.values()) {
                if (args[1].equalsIgnoreCase(item.name().toLowerCase()) && args[2] != null) {
                    for (int i = 0; i < Integer.parseInt(args[2]); i++) {
                        player.getInventory().addItem(ItemManager.getItem(item));
                    }
                }
            }
        }

    }

    @Override
    public String name() {
        return Commands.ITEM.getName();
    }

    @Override
    public String info() {
        return "Test custom items";
    }

    @Override
    public List<String> aliases() {
        List<String> alias = new ArrayList<>();
        alias.add("i");
        return alias;
    }

    @Override
    public List<String> getPrimaryArguments() {
        List<String> args = new ArrayList<>();
        for (Item item : Item.values()) {
            args.add(item.name().toLowerCase());
        }
        return args;
    }

    @Override
    public List<String> getSecondaryArguments(String[] args) {
        return null;
    }
}
