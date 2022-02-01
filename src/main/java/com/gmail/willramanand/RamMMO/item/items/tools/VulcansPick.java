package com.gmail.willramanand.RamMMO.item.items.tools;

import com.gmail.willramanand.RamMMO.RamMMO;
import com.gmail.willramanand.RamMMO.item.Item;
import com.gmail.willramanand.RamMMO.item.ItemManager;
import com.gmail.willramanand.RamMMO.item.ItemRarity;
import com.gmail.willramanand.RamMMO.item.items.BaseItem;
import com.gmail.willramanand.RamMMO.utils.DataUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.persistence.PersistentDataType;

public class VulcansPick extends BaseItem {

    public VulcansPick() {
        super(Item.VULCANS_PICK, Material.NETHERITE_PICKAXE, ItemRarity.MYTHICAL, Item.VULCANS_PICK.version());
        setLore("Breaks all pickaxe materials with a single swing");
        setFinal();
    }

    @Override
    protected void setAttributes() {

    }

    @Override
    protected void setEnchantments() {
        meta.addEnchant(Enchantment.DURABILITY, 5, true);
        meta.addEnchant(Enchantment.DIG_SPEED, 10, true);
    }

    @Override
    protected void setTags() {
        DataUtils.set(meta, "insta_break", PersistentDataType.INTEGER, 1);
    }

    @Override
    protected void setRecipe() {
        ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(RamMMO.getInstance(), item.getRecipeKey()), itemStack);
        shapedRecipe.shape("fpf", " s ", " n ");
        shapedRecipe.setIngredient('p', Material.NETHERITE_PICKAXE);
        shapedRecipe.setIngredient('s', ItemManager.getItem(Item.ENCHANTED_NETHERSTAR));
        shapedRecipe.setIngredient('f', ItemManager.getItem(Item.FIERY_SCALE));
        shapedRecipe.setIngredient('n', ItemManager.getItem(Item.ENCHANTED_NETHERITE));
        recipe = shapedRecipe;
    }
}
