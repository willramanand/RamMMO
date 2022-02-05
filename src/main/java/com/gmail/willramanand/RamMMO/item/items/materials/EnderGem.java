package com.gmail.willramanand.RamMMO.item.items.materials;

import com.gmail.willramanand.RamMMO.item.Item;
import com.gmail.willramanand.RamMMO.item.ItemRarity;
import com.gmail.willramanand.RamMMO.item.items.BaseItem;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;

public class EnderGem extends BaseItem {

    public EnderGem() {
        super(Item.ENDER_GEM, Material.ENDER_PEARL, ItemRarity.MYTHICAL, Item.ENDER_GEM.version());
        setLore("This item vibrates with energy.");
        setFinal();
    }

    @Override
    public void setAttributes() {
    }

    @Override
    public void setEnchantments() {
        meta.addEnchant(Enchantment.MENDING, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    }

    @Override
    public void setTags() {
    }

    @Override
    public void setRecipe() {
        recipe = null;
    }
}
