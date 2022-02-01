package com.gmail.willramanand.RamMMO.item.items.armors;

import com.gmail.willramanand.RamMMO.RamMMO;
import com.gmail.willramanand.RamMMO.item.Item;
import com.gmail.willramanand.RamMMO.item.ItemManager;
import com.gmail.willramanand.RamMMO.item.ItemRarity;
import com.gmail.willramanand.RamMMO.item.items.BaseItem;
import com.gmail.willramanand.RamMMO.utils.DataUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.SmithingRecipe;
import org.bukkit.persistence.PersistentDataType;

public class EmpoweredElytra extends BaseItem {

    public EmpoweredElytra() {
        super(Item.EMPOWERED_ELYTRA, Material.ELYTRA, ItemRarity.LEGENDARY, Item.EMPOWERED_ELYTRA.version());
        setLore("Glide infinitely");
        setFinal();
    }

    @Override
    protected void setAttributes() {
    }

    @Override
    protected void setEnchantments() {
        meta.addEnchant(Enchantment.DURABILITY, 5, true);
    }

    @Override
    protected void setTags() {
        DataUtils.set(meta, "infinite_flight", PersistentDataType.INTEGER, 1);
    }

    @Override
    protected void setRecipe() {
        SmithingRecipe newRecipe;
        newRecipe = new SmithingRecipe(new NamespacedKey(RamMMO.getInstance(), item.getRecipeKey()), itemStack,
                new RecipeChoice.MaterialChoice(Material.ELYTRA), new RecipeChoice.ExactChoice(ItemManager.getItem(Item.PHANTOM_PLATE)), false);
        recipe = newRecipe;
    }
}
