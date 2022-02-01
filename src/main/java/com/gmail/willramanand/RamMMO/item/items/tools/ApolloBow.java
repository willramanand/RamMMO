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

public class ApolloBow extends BaseItem {

    public ApolloBow() {
        super(Item.APOLLOS_BOW, Material.BOW, ItemRarity.MYTHICAL, Item.APOLLOS_BOW.version());
        setLore("Projectiles from this weapon do 2x damage", "Projectiles are imbued with the power of the Sun");
        setFinal();
    }

    @Override
    protected void setAttributes() {

    }

    @Override
    protected void setEnchantments() {
        meta.addEnchant(Enchantment.ARROW_FIRE, 1, true);
        meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        meta.addEnchant(Enchantment.MENDING, 1, true);
    }

    @Override
    protected void setTags() {
        DataUtils.set(meta, "cannot_burn", PersistentDataType.INTEGER, 1);
    }

    @Override
    protected void setRecipe() {
        ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(RamMMO.getInstance(), item.getRecipeKey()), itemStack);
        shapedRecipe.shape("dn ", "dsn", "dn ");
        shapedRecipe.setIngredient('d', ItemManager.getItem(Item.ENCHANTED_DIAMOND));
        shapedRecipe.setIngredient('n', ItemManager.getItem(Item.ENCHANTED_NETHERITE));
        shapedRecipe.setIngredient('s', Material.NETHER_STAR);
        recipe = shapedRecipe;
    }
}
