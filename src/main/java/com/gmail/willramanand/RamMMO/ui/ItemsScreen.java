package com.gmail.willramanand.RamMMO.ui;

import com.gmail.willramanand.RamMMO.RamMMO;
import com.gmail.willramanand.RamMMO.item.Item;
import com.gmail.willramanand.RamMMO.ui.uiitems.InventoryItem;
import com.gmail.willramanand.RamMMO.utils.ColorUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class ItemsScreen implements InventoryHolder {

    private final RamMMO plugin;
    private final Inventory inv;

    public ItemsScreen(RamMMO plugin) {
        this.plugin = plugin;
        this.inv = Bukkit.createInventory(this, 54, Component.text(ColorUtils.colorMessage("&bRamMMO Custom Items")));
        init();
    }

    private void init() {
        int i = 0;

        for (Item item : Item.values()) {
            inv.setItem(i, InventoryItem.getCustomItem(item));
            i++;
        }

        inv.setItem(53, InventoryItem.getClose());

        for (int k = 0; k < inv.getContents().length; k++) {
            if (inv.getItem(k) != null) continue;
            inv.setItem(k, InventoryItem.getBlank());
        }
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inv;
    }
}
