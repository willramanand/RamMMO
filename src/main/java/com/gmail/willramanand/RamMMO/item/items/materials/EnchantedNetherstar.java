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

public class EnchantedNetherstar extends BaseItem {

    public EnchantedNetherstar() {
        super(Item.ENCHANTED_NETHERSTAR, Material.NETHER_STAR, ItemRarity.LEGENDARY, Item.ENCHANTED_NETHERSTAR.version());
        setLore("Used to craft magical items!");
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
        ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(RamMMO.getInstance(), item.getRecipeKey()), itemStack);
        shapedRecipe.shape(" n ", "nnn", " n ");

        shapedRecipe.setIngredient('n', Material.NETHER_STAR);
        recipe = shapedRecipe;
    }
}
