package com.gmail.willramanand.RamMMO.item.items.materials;

import com.gmail.willramanand.RamMMO.item.Item;
import com.gmail.willramanand.RamMMO.item.ItemRarity;
import com.gmail.willramanand.RamMMO.item.items.BaseItem;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

public class PhantomPlate extends BaseItem {

    public PhantomPlate() {
        super(Item.PHANTOM_PLATE, Material.PHANTOM_MEMBRANE, ItemRarity.LEGENDARY, Item.PHANTOM_PLATE.version());
        setLore("This item feels as though it could float.");
        setFinal();
    }

    @Override
    public void setAttributes() {
    }

    @Override
    public void setEnchantments() {
        meta.addEnchant(Enchantment.PROTECTION_FALL, 10, true);
    }

    @Override
    public void setTags() {
    }

    @Override
    public void setRecipe() {
        recipe = null;
    }
}
