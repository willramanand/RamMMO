package com.gmail.willramanand.RamMMO.item.items.materials;

import com.gmail.willramanand.RamMMO.RamMMO;
import com.gmail.willramanand.RamMMO.item.Item;
import com.gmail.willramanand.RamMMO.item.ItemRarity;
import com.gmail.willramanand.RamMMO.item.items.BaseItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ShapedRecipe;

public class LeviathansHeart extends BaseItem {

    public LeviathansHeart() {
        super(Item.LEVIATHANS_HEART, Material.HEART_OF_THE_SEA, ItemRarity.MYTHICAL, Item.LEVIATHANS_HEART.version());
        setLore("Beats with the power of a god");
        setFinal();
    }

    @Override
    protected void setAttributes() {
    }

    @Override
    protected void setEnchantments() {
        meta.addEnchant(Enchantment.MENDING, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    }

    @Override
    protected void setTags() {
    }

    @Override
    protected void setRecipe() {
        recipe = null;
    }
}
