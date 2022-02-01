package com.gmail.willramanand.RamMMO.item.items.materials;

import com.gmail.willramanand.RamMMO.RamMMO;
import com.gmail.willramanand.RamMMO.item.Item;
import com.gmail.willramanand.RamMMO.item.ItemRarity;
import com.gmail.willramanand.RamMMO.item.items.BaseItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.persistence.PersistentDataType;

public class FieryScale extends BaseItem {

    public FieryScale() {
        super(Item.FIERY_SCALE, Material.FLINT, ItemRarity.MYTHICAL, Item.FIERY_SCALE.version());
        setLore("This item is warm to the touch.");
        setFinal();
    }

    @Override
    public void setAttributes() {

    }

    @Override
    public void setEnchantments() {
        meta.addEnchant(Enchantment.PROTECTION_FIRE, 5, true);
        meta.addEnchant(Enchantment.DURABILITY, 5, true);
    }

    @Override
    public void setTags() {
        meta.getPersistentDataContainer().set(new NamespacedKey(RamMMO.getInstance(), "cannot_burn"), PersistentDataType.INTEGER, 1);
    }

    @Override
    public void setRecipe() {
        recipe = null;
    }
}
