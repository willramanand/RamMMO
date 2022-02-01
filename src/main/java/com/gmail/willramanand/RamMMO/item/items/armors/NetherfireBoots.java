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

public class NetherfireBoots extends BaseItem {

    public NetherfireBoots() {
        super(Item.NETHERFIRE_BOOTS, Material.NETHERITE_BOOTS, ItemRarity.MYTHICAL, Item.NETHERFIRE_BOOTS.version());
        setLore("Grant immunity to fall damage", "Set Bonus: -5% Damage Taken");
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
        DataUtils.set(meta, "fall_immunity", PersistentDataType.INTEGER, 1);
        DataUtils.set(meta, "reduced_dmg_set", PersistentDataType.INTEGER, 1);
    }

    @Override
    protected void setRecipe() {
        SmithingRecipe newRecipe;
        newRecipe = new SmithingRecipe(new NamespacedKey(RamMMO.getInstance(), item.getRecipeKey()), itemStack,
                new RecipeChoice.MaterialChoice(Material.NETHERITE_BOOTS), new RecipeChoice.ExactChoice(ItemManager.getItem(Item.FIERY_SCALE)), false);
        recipe = newRecipe;
    }
}
