package com.gmail.willramanand.RamMMO.item.items;

import com.gmail.willramanand.RamMMO.item.Item;
import com.gmail.willramanand.RamMMO.item.ItemRarity;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public abstract class BaseItem {

    protected Item item;
    protected ItemStack itemStack;
    protected Recipe recipe;
    protected ItemRarity rarity;
    protected int version;

    public BaseItem(Item item, Material material, ItemRarity rarity, int version) {
        this.item = item;
        this.itemStack = new ItemStack(material);
        this.rarity = rarity;
        this.version = version;

        setAttributes();
        setLore();
        setEnchantments();
        setTags();
        setRecipe();
    }

    public abstract void setAttributes();

    public abstract void setLore();

    public abstract void setEnchantments();

    public abstract void setTags();

    public abstract void setRecipe();

    public ItemStack get()
    {
        return itemStack;
    }

    public Recipe recipe() {
        return recipe;
    }
}
