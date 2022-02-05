package com.gmail.willramanand.RamMMO.item.items.eggs;

import com.gmail.willramanand.RamMMO.item.Item;
import com.gmail.willramanand.RamMMO.item.ItemRarity;
import com.gmail.willramanand.RamMMO.item.items.BaseItem;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

public class ApocalypseHorseEgg extends BaseItem {

    public ApocalypseHorseEgg() {
        super(Item.APOCALYPSE_HORSE_EGG, Material.SKELETON_HORSE_SPAWN_EGG, ItemRarity.MYTHICAL, Item.APOCALYPSE_HORSE_EGG.version());
        setLore("Prepare to ride death itself.");
        setFinal();
    }

    @Override
    public void setAttributes() {
    }

    @Override
    public void setEnchantments() {
    }

    @Override
    public void setTags() {
    }

    @Override
    public void setRecipe() {
        recipe = null;
    }
}
