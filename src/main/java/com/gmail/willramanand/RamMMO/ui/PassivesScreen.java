package com.gmail.willramanand.RamMMO.ui;

import com.gmail.willramanand.RamMMO.RamMMO;
import com.gmail.willramanand.RamMMO.enums.Passives;
import com.gmail.willramanand.RamMMO.ui.uiitems.InventoryItem;
import com.gmail.willramanand.RamMMO.utils.ColorUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class PassivesScreen implements InventoryHolder {

    private final RamMMO plugin;
    private final Player player;
    private final Inventory inv;

    public PassivesScreen(RamMMO plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        this.inv = Bukkit.createInventory(this, 36, Component.text(ColorUtils.colorMessage("&aToggle Passives")));
        init();
    }

    private void init() {
        inv.setItem(0, InventoryItem.getHead(player));
        inv.setItem(11, InventoryItem.getPassiveItem(Passives.AGILITY_JUMP_BOOST, Material.RABBIT_FOOT, player));
        inv.setItem(12, InventoryItem.getPassiveItem(Passives.EXCAVATION_HASTE, Material.DIAMOND_SHOVEL, player));
        inv.setItem(13, InventoryItem.getPassiveItem(Passives.FISHING_CONDUIT_POWER, Material.HEART_OF_THE_SEA, player));
        inv.setItem(14, InventoryItem.getPassiveItem(Passives.FISHING_DOLPHINS_GRACE, Material.TROPICAL_FISH, player));
        inv.setItem(15, InventoryItem.getPassiveItem(Passives.MINING_HASTE, Material.IRON_PICKAXE, player));
        inv.setItem(22, InventoryItem.getPassiveItem(Passives.WOODCUTTING_HASTE, Material.GOLDEN_AXE, player));
        inv.setItem(35, InventoryItem.getClose());

        for (int i = 0; i < inv.getContents().length; i++) {
            if (inv.getItem(i) != null) continue;
            inv.setItem(i, InventoryItem.getBlank());
        }
    }




    @Override
    public @NotNull Inventory getInventory() {
        return inv;
    }
}
