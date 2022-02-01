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

public class EnchantedNetherite extends BaseItem {

    public EnchantedNetherite() {
        super(Item.ENCHANTED_NETHERITE, Material.NETHERITE_INGOT, ItemRarity.LEGENDARY, Item.ENCHANTED_NETHERITE.version());
        setLore("Used to craft magical items!");
        setFinal();
    }

    @Override
    protected void setAttributes() {}

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
        ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(RamMMO.getInstance(), item.getRecipeKey()), itemStack);
        shapedRecipe.shape(" n ", "nnn", " n ");

        shapedRecipe.setIngredient('n', Material.NETHERITE_INGOT);
        recipe = shapedRecipe;
    }
}
