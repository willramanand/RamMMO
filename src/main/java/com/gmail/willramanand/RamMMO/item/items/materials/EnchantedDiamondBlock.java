package com.gmail.willramanand.RamMMO.item.items.materials;

import com.gmail.willramanand.RamMMO.RamMMO;
import com.gmail.willramanand.RamMMO.item.Item;
import com.gmail.willramanand.RamMMO.item.ItemManager;
import com.gmail.willramanand.RamMMO.item.ItemRarity;
import com.gmail.willramanand.RamMMO.item.items.BaseItem;
import com.gmail.willramanand.RamMMO.utils.DataUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.persistence.PersistentDataType;

public class EnchantedDiamondBlock extends BaseItem {

    public EnchantedDiamondBlock() {
        super(Item.ENCHANTED_DIAMOND_BLOCK, Material.DIAMOND_BLOCK, ItemRarity.LEGENDARY, Item.ENCHANTED_DIAMOND_BLOCK.version());
        setLore("Used to craft magical item!", "Cannot be placed");
        setFinal();
    }

    @Override
    protected void setAttributes() {

    }

    @Override
    protected void setEnchantments() {
        meta.addEnchant(Enchantment.MENDING, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    }

    @Override
    protected void setTags() {
        DataUtils.set(meta, "cannot_place", PersistentDataType.INTEGER, 0);
    }

    @Override
    protected void setRecipe() {
        ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(RamMMO.getInstance(), item.getRecipeKey()), itemStack);
        shapedRecipe.shape(" d ", "ddd", " d ");

        shapedRecipe.setIngredient('d', ItemManager.getItem(Item.ENCHANTED_DIAMOND));
        recipe = shapedRecipe;
    }
}
