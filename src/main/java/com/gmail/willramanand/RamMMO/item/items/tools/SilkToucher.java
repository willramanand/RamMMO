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

public class SilkToucher extends BaseItem {

    public SilkToucher() {
        super(Item.SILK_TOUCHER, Material.DIAMOND_PICKAXE, ItemRarity.LEGENDARY, Item.SILK_TOUCHER.version());
        setLore("Breaks even the softest of materials.");
        setFinal();
    }

    @Override
    protected void setAttributes() {

    }

    @Override
    protected void setEnchantments() {
        meta.addEnchant(Enchantment.SILK_TOUCH, 1, false);
    }

    @Override
    protected void setTags() {
        DataUtils.set(meta, "silk_touch", PersistentDataType.INTEGER, 1);
    }

    @Override
    protected void setRecipe() {
        ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(RamMMO.getInstance(), item.getRecipeKey()), itemStack);
        shapedRecipe.shape("ddd",
                            " n ",
                            " n ");
        shapedRecipe.setIngredient('d', ItemManager.getItem(Item.ENCHANTED_DIAMOND));
        shapedRecipe.setIngredient('n', ItemManager.getItem(Item.ENCHANTED_NETHERITE));
        recipe = shapedRecipe;
    }
}
