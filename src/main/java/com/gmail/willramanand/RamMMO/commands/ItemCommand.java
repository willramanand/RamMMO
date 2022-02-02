package com.gmail.willramanand.RamMMO.commands;

import com.gmail.willramanand.RamMMO.RamMMO;
import com.gmail.willramanand.RamMMO.item.Item;
import com.gmail.willramanand.RamMMO.ui.ItemsScreen;
import com.gmail.willramanand.RamMMO.utils.ColorUtils;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ItemCommand extends SubCommand {
    @Override
    public void onCommand(Player player, String[] args) {
        if (player.hasPermission("rammmo.item")) {
            ItemsScreen itemsScreen = new ItemsScreen(RamMMO.getInstance());
            player.openInventory(itemsScreen.getInventory());
        } else {
            player.sendMessage(ColorUtils.colorMessage("&4You do not permission to do that!"));
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
