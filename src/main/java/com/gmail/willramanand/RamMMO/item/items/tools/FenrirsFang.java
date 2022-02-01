package com.gmail.willramanand.RamMMO.item.items.tools;

import com.gmail.willramanand.RamMMO.RamMMO;
import com.gmail.willramanand.RamMMO.item.Item;
import com.gmail.willramanand.RamMMO.item.ItemManager;
import com.gmail.willramanand.RamMMO.item.ItemRarity;
import com.gmail.willramanand.RamMMO.item.items.BaseItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ShapedRecipe;

import java.util.UUID;

public class FenrirsFang extends BaseItem {

    public FenrirsFang() {
        super(Item.FENRIRS_FANG, Material.NETHERITE_SWORD, ItemRarity.MYTHICAL, Item.FENRIRS_FANG.version());
        setLore("Let your foes feel Fenrir's might!");
        setFinal();
    }

    @Override
    protected void setAttributes() {
        AttributeModifier damageModifer = new AttributeModifier(UUID.randomUUID(), "fang_damage", 25.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, damageModifer);
    }

    @Override
    protected void setEnchantments() {
        meta.addEnchant(Enchantment.DAMAGE_ALL, 10, true);
    }

    @Override
    protected void setTags() {
    }

    @Override
    protected void setRecipe() {
        ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(RamMMO.getInstance(), item.getRecipeKey()), itemStack);
        shapedRecipe.shape(" f ", "nsn", " e ");
        shapedRecipe.setIngredient('e', ItemManager.getItem(Item.ENCHANTED_EMERALD));
        shapedRecipe.setIngredient('n', ItemManager.getItem(Item.ENCHANTED_NETHERITE));
        shapedRecipe.setIngredient('f', ItemManager.getItem(Item.FIERY_SCALE));
        shapedRecipe.setIngredient('s', Material.NETHERITE_SWORD);
        recipe = shapedRecipe;
    }
}
