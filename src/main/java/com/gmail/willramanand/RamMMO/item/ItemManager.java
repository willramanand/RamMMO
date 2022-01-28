package com.gmail.willramanand.RamMMO.item;

import com.gmail.willramanand.RamMMO.item.items.*;
import com.gmail.willramanand.RamMMO.item.items.materials.*;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ItemManager {

    private static final Map<Item, BaseItem> items = new HashMap<>();

    public static void registerItems() {
        // Special Materials
        items.put(Item.PHANTOM_PLATE, new PhantomPlate());
        items.put(Item.FIERY_SCALE, new FieryScale());

        // Upgraded Materials
        items.put(Item.ENCHANTED_GOLD, new EnchantedGold());
        items.put(Item.ENCHANTED_DIAMOND, new EnchantedDiamond());
        items.put(Item.ENCHANTED_EMERALD, new EnchantedEmerald());
        items.put(Item.ENCHANTED_NETHERITE, new EnchantedNetherite());

        // Tools
        items.put(Item.SILK_TOUCHER, new SilkToucher());
        items.put(Item.APOLLOS_BOW, new ApolloBow());
        items.put(Item.FENRIRS_FANG, new FenrirsFang());

        // Elytra
        items.put(Item.EMPOWERED_ELYTRA, new EmpoweredElytra());

        // Armor
        items.put(Item.NETHERFIRE_HELM, new NetherfireHelm());
        items.put(Item.NETHERFIRE_CHEST, new NetherfireChest());
        items.put(Item.NETHERFIRE_LEGS, new NetherfireLegs());
        items.put(Item.NETHERFIRE_BOOTS, new NetherfireBoots());

        // Combined items
        items.put(Item.NETHERFIRE_ELYTRA, new NetherfireElytra());

        // Build Recipes
        for (Item item : Item.values()) {
            Bukkit.addRecipe(items.get(item).recipe());
        }
    }
    public static ItemStack getItem(Item item) {
        return items.get(item).get();
    }
}
