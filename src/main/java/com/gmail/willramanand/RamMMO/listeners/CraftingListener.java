package com.gmail.willramanand.RamMMO.listeners;

import com.gmail.willramanand.RamMMO.RamMMO;
import com.gmail.willramanand.RamMMO.item.Item;
import com.gmail.willramanand.RamMMO.item.ItemManager;
import com.google.common.collect.ImmutableList;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.inventory.PrepareSmithingEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.SmithingRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class CraftingListener implements Listener {

    private final RamMMO plugin;
    private final List<Item> validSmithItems = ImmutableList.of(Item.NETHERFIRE_HELM, Item.NETHERFIRE_CHEST, Item.NETHERFIRE_LEGS, Item.NETHERFIRE_BOOTS,
            Item.EMPOWERED_ELYTRA);

    public CraftingListener(RamMMO plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void netherfireElytra(PrepareItemCraftEvent event) {
        boolean chestplate = false;
        boolean elytra = false;

        Set<ItemStack> itemStackSet = new HashSet<>();
        Set<Enchantment> newEnchants = new HashSet<>();
        Map<Enchantment, Integer> enchantLvls = new HashMap<>();

        for (ItemStack item : event.getInventory().getMatrix()) {
            if (item == null) continue;
            if (item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, Item.NETHERFIRE_CHEST.getClassName()))) {
                item.getItemMeta().getEnchants().keySet().forEach(enchantment -> {
                    newEnchants.add(enchantment);
                    enchantLvls.put(enchantment, item.getItemMeta().getEnchantLevel(enchantment));
                });
                chestplate = true;
            } else if (item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, Item.EMPOWERED_ELYTRA.getClassName()))) {
                item.getItemMeta().getEnchants().keySet().forEach(enchantment -> {
                    newEnchants.add(enchantment);
                    enchantLvls.put(enchantment, item.getItemMeta().getEnchantLevel(enchantment));
                });
                elytra = true;
            }
            itemStackSet.add(item);
        }

        if (chestplate && elytra && itemStackSet.size() == 2) {
            ItemMeta meta = ItemManager.getItem(Item.NETHERFIRE_ELYTRA).getItemMeta();
            for (Enchantment enchantment : newEnchants) {
                if (meta.hasEnchant(enchantment)) continue;
                meta.addEnchant(enchantment, enchantLvls.get(enchantment), true);
            }
            ItemStack newItem = new ItemStack(ItemManager.getItem(Item.NETHERFIRE_ELYTRA));
            newItem.setItemMeta(meta);
            event.getInventory().setResult(newItem);
        }
    }

    @EventHandler
    public void smithingRecipes(PrepareSmithingEvent event) {
        Player p  = (Player) Bukkit.getOnlinePlayers().toArray()[0];

        if (event.getInventory().getRecipe() == null) return;
        Item selected = null;
        for (Item item : validSmithItems) {
            SmithingRecipe recipe = (SmithingRecipe) event.getInventory().getRecipe();
            if (recipe.getKey().getKey().equalsIgnoreCase(item.getRecipeKey())) {
                selected = item;
                p.sendMessage("item selected");
            }
            if (selected != null) break;
        }

        if (selected == null) return;

        Set<Enchantment> newEnchants = new HashSet<>();
        Map<Enchantment, Integer> enchantLvls = new HashMap<>();

        p.sendMessage("checking inputs");
        if (event.getInventory().getInputEquipment() == null || event.getInventory().getInputMineral() == null) return;
        p.sendMessage("inputs good");
        event.getInventory().getInputEquipment().getItemMeta().getEnchants().keySet().forEach(enchantment -> {
            newEnchants.add(enchantment);
            enchantLvls.put(enchantment, event.getInventory().getInputEquipment().getItemMeta().getEnchantLevel(enchantment));
        });

        if (newEnchants.size() == 0) p.sendMessage("empty enchants");

        ItemStack newItem = new ItemStack(ItemManager.getItem(selected));
        ItemMeta meta = newItem.getItemMeta();
        for (Enchantment enchantment : newEnchants) {
            if (meta.hasEnchant(enchantment)) continue;
            p.sendMessage("Enchantment" + enchantment.getName());
            meta.addEnchant(enchantment, enchantLvls.get(enchantment), true);
        }
        newItem.setItemMeta(meta);
        event.setResult(newItem);
    }
}
