package com.gmail.willramanand.RamMMO.items.recipe;

import com.gmail.willramanand.RamMMO.items.Item;
import com.gmail.willramanand.RamMMO.items.ItemManager;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum Recipe {
    EMPOWERED_ELYTRA(new ItemStack[]{ItemManager.getItem(Item.PHANTOM_PLATE), new ItemStack(Material.ELYTRA)}
            , new int[]{1, 1}
            , ItemManager.getItem(Item.EMPOWERED_ELYTRA)),

    NETHERFIRE_HELM(new ItemStack[]{ItemManager.getItem(Item.FIERY_SCALE), new ItemStack(Material.NETHERITE_HELMET)}
            , new int[]{1, 1}
            , ItemManager.getItem(Item.NETHERFIRE_HELM)),

    NETHERFIRE_CHEST(new ItemStack[]{ItemManager.getItem(Item.FIERY_SCALE), new ItemStack(Material.NETHERITE_CHESTPLATE)}
            , new int[]{1, 1}
            , ItemManager.getItem(Item.NETHERFIRE_CHEST)),

    NETHERFIRE_LEGS(new ItemStack[]{ItemManager.getItem(Item.FIERY_SCALE), new ItemStack(Material.NETHERITE_LEGGINGS)}
            , new int[]{1, 1}
            , ItemManager.getItem(Item.NETHERFIRE_LEGS)),

    NETHERFIRE_BOOTS(new ItemStack[]{ItemManager.getItem(Item.FIERY_SCALE), new ItemStack(Material.NETHERITE_BOOTS)}
            , new int[]{1, 1}
            , ItemManager.getItem(Item.NETHERFIRE_BOOTS)),

    NETHERFIRE_ELYTRA(new ItemStack[]{ItemManager.getItem(Item.NETHERFIRE_CHEST), ItemManager.getItem(Item.EMPOWERED_ELYTRA)}
            , new int[]{1, 1}
            , ItemManager.getItem(Item.NETHERFIRE_ELYTRA)),

    ;

    private ItemStack[] items;
    private int[] amounts;
    private ItemStack result;


    Recipe(ItemStack[] items, int[] amounts, ItemStack result) {
        this.items = items;
        this.amounts = amounts;
        this.result = result;
    }

    public ItemStack[] getItems() {
        return items;
    }

    public int[] getAmounts() {
        return amounts;
    }

    public ItemStack getResult() {
        return result;
    }

}
