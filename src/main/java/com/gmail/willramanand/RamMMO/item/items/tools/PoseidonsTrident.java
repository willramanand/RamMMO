package com.gmail.willramanand.RamMMO.item.items.tools;

import com.gmail.willramanand.RamMMO.RamMMO;
import com.gmail.willramanand.RamMMO.item.Item;
import com.gmail.willramanand.RamMMO.item.ItemManager;
import com.gmail.willramanand.RamMMO.item.ItemRarity;
import com.gmail.willramanand.RamMMO.item.items.BaseItem;
import com.gmail.willramanand.RamMMO.utils.DataUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class PoseidonsTrident extends BaseItem {

    public PoseidonsTrident() {
        super(Item.POSEIDONS_TRIDENT, Material.TRIDENT, ItemRarity.MYTHICAL, Item.POSEIDONS_TRIDENT.version());
        setLore("Feel the power of Poseidon!");
        setFinal();
    }

    @Override
    protected void setAttributes() {
        AttributeModifier damageModifer = new AttributeModifier(UUID.randomUUID(), "poseidon_damage", 40.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, damageModifer);
    }


    @Override
    protected void setEnchantments() {
        meta.addEnchant(Enchantment.IMPALING, 10, true);
        meta.addEnchant(Enchantment.LOYALTY, 3, true);
    }

    @Override
    protected void setTags() {
        DataUtils.set(meta, "downpour", PersistentDataType.INTEGER, 1);
    }

    @Override
    protected void setRecipe() {
        ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(RamMMO.getInstance(), item.getRecipeKey()), itemStack);
        shapedRecipe.shape("sss", " t ", " n ");
        shapedRecipe.setIngredient('s', ItemManager.getItem(Item.ENCHANTED_NETHERSTAR));
        shapedRecipe.setIngredient('n', ItemManager.getItem(Item.ENCHANTED_NETHERITE));
        shapedRecipe.setIngredient('t', Material.TRIDENT);
        recipe = shapedRecipe;
    }
}
