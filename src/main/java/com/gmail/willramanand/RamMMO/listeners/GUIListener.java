package com.gmail.willramanand.RamMMO.listeners;

import com.gmail.willramanand.RamMMO.RamMMO;
import com.gmail.willramanand.RamMMO.enums.Passive;
import com.gmail.willramanand.RamMMO.enums.Passives;
import com.gmail.willramanand.RamMMO.player.MMOPlayer;
import com.gmail.willramanand.RamMMO.ui.ItemsScreen;
import com.gmail.willramanand.RamMMO.ui.PassivesScreen;
import com.gmail.willramanand.RamMMO.ui.uiitems.InventoryItem;
import com.gmail.willramanand.RamMMO.utils.DataUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class GUIListener implements Listener {

    private final RamMMO plugin;

    public GUIListener(RamMMO plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onItemsScreenClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null) return;
        if (e.getClickedInventory().getHolder() instanceof ItemsScreen) {
            Player player = (Player) e.getWhoClicked();

            if (e.getCurrentItem() == null) return;
            if (!(DataUtils.has(e.getCurrentItem().getItemMeta(), "close_button")) && !(DataUtils.has(e.getCurrentItem().getItemMeta(), "empty"))) {
                if (e.isLeftClick() && !(e.isShiftClick())) {
                    ItemStack item = new ItemStack(e.getCurrentItem());
                    player.getInventory().addItem(item);
                } else if (e.isRightClick() || e.isShiftClick()) {
                    ItemStack item = new ItemStack(e.getCurrentItem());
                    item.setAmount(item.getMaxStackSize());
                    player.getInventory().addItem(item);
                }
            } else if (DataUtils.has(e.getCurrentItem().getItemMeta(), "close_button")) {
                player.closeInventory();
            } else if (DataUtils.has(e.getCurrentItem().getItemMeta(), "empty") && e.getCursor() != null) {
                e.getCursor().setAmount(0);
            }
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPassivesScreenClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null) return;
        if (e.getClickedInventory().getHolder() instanceof PassivesScreen) {
            Player player = (Player) e.getWhoClicked();
            MMOPlayer mmoPlayer = plugin.getPlayerManager().getPlayerData(player);

            if (e.getCurrentItem() == null) return;
            if (DataUtils.has(e.getCurrentItem().getItemMeta(), "passive_item")) {
                Passive selectPass = null;
                for (Passive passive : Passives.values()) {
                    if (DataUtils.get(e.getCurrentItem().getItemMeta(), "passive_item", PersistentDataType.STRING).equalsIgnoreCase(passive.name())) selectPass = passive;
                }
                if (selectPass == null) return;
                if (mmoPlayer.getPassives(selectPass)) {
                    mmoPlayer.setPassives(selectPass, false);
                } else if (!(mmoPlayer.getPassives(selectPass))) {
                    mmoPlayer.setPassives(selectPass, true);
                }
                int slot = e.getSlot();
                Material type = e.getCurrentItem().getType();
                e.getClickedInventory().removeItem(e.getCurrentItem());
                e.getClickedInventory().setItem(slot, InventoryItem.getPassiveItem(selectPass, type, player));
            } else if (DataUtils.has(e.getCurrentItem().getItemMeta(), "close_button")) {
                player.closeInventory();
            }
            e.setCancelled(true);
        }
    }
}
