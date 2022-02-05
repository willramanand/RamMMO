package com.gmail.willramanand.RamMMO.item.items.materials;

import com.gmail.willramanand.RamMMO.item.Item;
import com.gmail.willramanand.RamMMO.item.ItemRarity;
import com.gmail.willramanand.RamMMO.item.items.BaseItem;
import com.gmail.willramanand.RamMMO.utils.ColorUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

public class MinotaursHide extends BaseItem {

    public MinotaursHide() {
        super(Item.MINOTAURS_HIDE, Material.LEATHER, ItemRarity.MYTHICAL, Item.MINOTAURS_HIDE.version());
        setLore("This item is quite durable.", ColorUtils.colorMessage("&cWARNING: The unbreakable tag will not carry over on some recipes."));
        setFinal();
    }

    @Override
    public void setAttributes() {
    }

    @Override
    public void setEnchantments() {
        meta.addEnchant(Enchantment.DURABILITY, 10, true);
    }

    @Override
    public void setTags() {
    }

    @Override
    public void setRecipe() {
        recipe = null;
    }
}
