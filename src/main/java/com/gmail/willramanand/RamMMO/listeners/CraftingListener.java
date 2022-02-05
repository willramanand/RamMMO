package com.gmail.willramanand.RamMMO.listeners;

import com.gmail.willramanand.RamMMO.RamMMO;
import com.gmail.willramanand.RamMMO.item.Item;
import com.gmail.willramanand.RamMMO.item.ItemManager;
import com.gmail.willramanand.RamMMO.utils.DataUtils;
import com.google.common.collect.ImmutableList;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.checkerframework.checker.units.qual.C;

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
        boolean isUnbreakable = false;

        Set<ItemStack> itemStackSet = new HashSet<>();
        Set<Enchantment> newEnchants = new HashSet<>();
        Map<Enchantment, Integer> enchantLvls = new HashMap<>();

        for (ItemStack item : event.getInventory().getMatrix()) {
            if (item == null) continue;
            if (DataUtils.has(item.getItemMeta(), Item.NETHERFIRE_CHEST.getClassName())) {
                item.getItemMeta().getEnchants().keySet().forEach(enchantment -> {
                    newEnchants.add(enchantment);
                    enchantLvls.put(enchantment, item.getItemMeta().getEnchantLevel(enchantment));
                });
                isUnbreakable = item.getItemMeta().isUnbreakable();
                chestplate = true;
            } else if (item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, Item.EMPOWERED_ELYTRA.getClassName()))) {
                item.getItemMeta().getEnchants().keySet().forEach(enchantment -> {
                    newEnchants.add(enchantment);
                    enchantLvls.put(enchantment, item.getItemMeta().getEnchantLevel(enchantment));
                });
                isUnbreakable = item.getItemMeta().isUnbreakable();
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

            if (isUnbreakable) {
                meta.setUnbreakable(true);
            }

            ItemStack newItem = new ItemStack(ItemManager.getItem(Item.NETHERFIRE_ELYTRA));
            newItem.setItemMeta(meta);
            event.getInventory().setResult(newItem);
        }
    }

    @EventHandler
    public void smithingRecipes(PrepareSmithingEvent event) {
        if (event.getInventory().getRecipe() == null) return;
        Item selected = null;
        for (Item item : validSmithItems) {
            SmithingRecipe recipe = (SmithingRecipe) event.getInventory().getRecipe();
            if (recipe.getKey().getKey().equalsIgnoreCase(item.getRecipeKey())) {
                selected = item;
            }
            if (selected != null) break;
        }

        if (selected == null) return;

        Set<Enchantment> newEnchants = new HashSet<>();
        Map<Enchantment, Integer> enchantLvls = new HashMap<>();

        if (event.getInventory().getInputEquipment() == null || event.getInventory().getInputMineral() == null) return;
        event.getInventory().getInputEquipment().getItemMeta().getEnchants().keySet().forEach(enchantment -> {
            newEnchants.add(enchantment);
            enchantLvls.put(enchantment, event.getInventory().getInputEquipment().getItemMeta().getEnchantLevel(enchantment));
        });


        ItemStack newItem = new ItemStack(ItemManager.getItem(selected));
        ItemMeta meta = newItem.getItemMeta();

        if (event.getInventory().getInputEquipment().getItemMeta().isUnbreakable()) {
            meta.setUnbreakable(true);
        }

        for (Enchantment enchantment : newEnchants) {
            if (meta.hasEnchant(enchantment)) continue;
            meta.addEnchant(enchantment, enchantLvls.get(enchantment), true);
        }
        newItem.setItemMeta(meta);
        event.setResult(newItem);
    }

    @EventHandler
    public void unbreakableRecipes(PrepareSmithingEvent event) {
        if (event.getInventory().getInputMineral() == null || !(DataUtils.has(event.getInventory().getInputMineral().getItemMeta(), "minotaurhide")))
            return;
        if (event.getInventory().getInputEquipment() == null) return;
        if (event.getInventory().getInputEquipment().getItemMeta().isUnbreakable()) return;
        ItemStack item = new ItemStack(event.getInventory().getInputEquipment());
        ItemMeta meta = item.getItemMeta();
        meta.setUnbreakable(true);
        item.setItemMeta(meta);
        event.setResult(item);
    }

    @EventHandler
    public void checkUnbreakableSmith(PrepareSmithingEvent event) {
        if (event.getInventory().getRecipe() == null || event.getResult() == null) return;
        if (event.getInventory().getInputEquipment().getItemMeta().isUnbreakable()) {
            ItemStack item = new ItemStack(event.getResult());
            ItemMeta meta = item.getItemMeta();
            meta.setUnbreakable(true);
            event.setResult(item);
        }
    }

    @EventHandler
    public void smithItem(InventoryClickEvent e) {
        if (e.getClickedInventory() == null || e.getClickedInventory().getType() != InventoryType.SMITHING) return;
        if (e.getSlotType() != InventoryType.SlotType.RESULT) return;
        if (e.getCurrentItem() == null) return;
        SmithingInventory inv = (SmithingInventory) e.getClickedInventory();

        if (!(DataUtils.has(inv.getInputMineral().getItemMeta(), "minotaurhide"))) return;

        ItemStack item = new ItemStack(e.getCurrentItem());
        e.getWhoClicked().getInventory().addItem(item);
        inv.setInputEquipment(null);
        inv.getInputMineral().setAmount(inv.getInputMineral().getAmount() - 1);
        inv.setResult(null);
    }
}
