package com.gmail.willramanand.RamMMO.listeners;

import com.gmail.willramanand.RamMMO.RamMMO;
import com.gmail.willramanand.RamMMO.item.Item;
import com.gmail.willramanand.RamMMO.item.ItemManager;
import com.gmail.willramanand.RamMMO.utils.ColorUtils;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class VersionListener implements Listener {

    private final RamMMO plugin;

    public VersionListener(RamMMO plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoinCheck(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        int changeItems = 0;
        boolean converted = false;
        for (ItemStack item : player.getInventory().getStorageContents()) {
            for (Item customItem : Item.values()) {
                if (item != null && item.getItemMeta() != null && item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, customItem.getClassName()))) {
                    int versionChecked = item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, customItem.getClassName()), PersistentDataType.INTEGER);
                    if (versionChecked != customItem.version()) {
                        int amount = item.getAmount();
                        changeItems++;
                        converted = true;
                        player.getInventory().remove(item);
                        for (int i = 0; i < amount; i++) {
                            player.getInventory().addItem(ItemManager.getItem(customItem));
                        }
                    }
                }
            }
        }
        if (converted) player.sendMessage(ColorUtils.colorMessage("&eConverted &d" + changeItems + " &eitems to new versions!"));
    }
}