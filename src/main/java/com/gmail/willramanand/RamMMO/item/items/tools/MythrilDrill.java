package com.gmail.willramanand.RamMMO.item.items.tools;

import com.gmail.willramanand.RamMMO.RamMMO;
import com.gmail.willramanand.RamMMO.item.Item;
import com.gmail.willramanand.RamMMO.item.ItemManager;
import com.gmail.willramanand.RamMMO.item.ItemRarity;
import com.gmail.willramanand.RamMMO.item.items.BaseItem;
import com.gmail.willramanand.RamMMO.utils.DataUtils;
import com.gmail.willramanand.RamSkills.RamSkills;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.persistence.PersistentDataType;

public class MythrilDrill extends BaseItem {

    public MythrilDrill() {
        super(Item.MYTHRIL_DRILL, Material.PRISMARINE_SHARD, ItemRarity.MYTHICAL, Item.MYTHRIL_DRILL.version());
        setLore("The ultimate mining tool!");
        setFinal();
    }

    @Override
    protected void setAttributes() {

    }

    @Override
    protected void setEnchantments() {
        meta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 5, true);
        meta.addEnchant(Enchantment.DIG_SPEED, 10, true);
    }

    @Override
    protected void setTags() {
        meta.getPersistentDataContainer().set(new NamespacedKey(RamSkills.getInstance(), "vein_miner_applicable"), PersistentDataType.INTEGER, 0);
    }

    @Override
    protected void setRecipe() {
        ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(RamMMO.getInstance(), item.getRecipeKey()), itemStack);
        shapedRecipe.shape(" f ", "nhn", "nsn");
        shapedRecipe.setIngredient('h', ItemManager.getItem(Item.LEVIATHANS_HEART));
        shapedRecipe.setIngredient('s', ItemManager.getItem(Item.ENCHANTED_NETHERSTAR));
        shapedRecipe.setIngredient('f', ItemManager.getItem(Item.FIERY_SCALE));
        shapedRecipe.setIngredient('n', ItemManager.getItem(Item.ENCHANTED_NETHERITE_BLOCK));
        recipe = shapedRecipe;
    }
}
