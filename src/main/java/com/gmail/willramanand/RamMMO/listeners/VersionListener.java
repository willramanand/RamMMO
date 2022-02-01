package com.gmail.willramanand.RamMMO.listeners;

import com.gmail.willramanand.RamMMO.RamMMO;
import com.gmail.willramanand.RamMMO.item.Item;
import com.gmail.willramanand.RamMMO.item.ItemManager;
import com.gmail.willramanand.RamMMO.utils.ColorUtils;
import com.gmail.willramanand.RamMMO.utils.DataUtils;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
                if (item != null && item.getItemMeta() != null && DataUtils.has(item.getItemMeta(), customItem.getClassName())) {
                    int versionChecked = DataUtils.get(item.getItemMeta(), customItem.getClassName(), PersistentDataType.INTEGER);
                    if (versionChecked != customItem.version()) {
                        Map<Enchantment, Integer> newEnchants = item.getItemMeta().getEnchants();
                        int amount = item.getAmount();
                        changeItems++;
                        converted = true;
                        player.getInventory().remove(item);

                        ItemStack newItem = new ItemStack(ItemManager.getItem(customItem));
                        ItemMeta meta = newItem.getItemMeta();

                        for (Enchantment enchantment : newEnchants.keySet()) {
                            if (meta.hasEnchant(enchantment)) continue;
                            meta.addEnchant(enchantment, newEnchants.get(enchantment), true);
                        }

                        newItem.setItemMeta(meta);
                        newItem.setAmount(amount);

                        player.getInventory().addItem(newItem);
                    }
                }
            }
        }
        if (converted)
            player.sendMessage(ColorUtils.colorMessage("&eConverted &d" + changeItems + " &eitems to new versions!"));
    }

    @EventHandler
    public void onJoinCheckArmor(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        int changeItems = 0;
        boolean converted = false;

        List<ItemStack> extraSlots = new ArrayList<>();
        extraSlots.add(player.getInventory().getHelmet());
        extraSlots.add(player.getInventory().getChestplate());
        extraSlots.add(player.getInventory().getLeggings());
        extraSlots.add(player.getInventory().getBoots());
        extraSlots.add(player.getInventory().getItemInOffHand());


        int slot = 0;
        for (ItemStack item : extraSlots) {
            for (Item customItem : Item.values()) {
                if (item != null && item.getItemMeta() != null && DataUtils.has(item.getItemMeta(), customItem.getClassName())) {
                    int versionChecked = DataUtils.get(item.getItemMeta(), customItem.getClassName(), PersistentDataType.INTEGER);
                    if (versionChecked != customItem.version()) {
                        Map<Enchantment, Integer> newEnchants = item.getItemMeta().getEnchants();

                        ItemStack newItem = new ItemStack(ItemManager.getItem(customItem));
                        ItemMeta meta = newItem.getItemMeta();

                        for (Enchantment enchantment : newEnchants.keySet()) {
                            if (meta.hasEnchant(enchantment)) continue;
                            meta.addEnchant(enchantment, newEnchants.get(enchantment), true);
                        }
                        newItem.setItemMeta(meta);

                        changeItems++;
                        converted = true;
                        player.getInventory().remove(item);
                        switch (slot) {
                            case 0 -> player.getInventory().setHelmet(newItem);
                            case 1 -> player.getInventory().setChestplate(newItem);
                            case 2 -> player.getInventory().setLeggings(newItem);
                            case 3 -> player.getInventory().setBoots(newItem);
                            case 4 -> player.getInventory().setItemInOffHand(newItem);
                        }
                    }
                }
            }
            slot++;
        }

        if (converted)
            player.sendMessage(ColorUtils.colorMessage("&eConverted &d" + changeItems + " &earmor slot items to new versions!"));
    }
}