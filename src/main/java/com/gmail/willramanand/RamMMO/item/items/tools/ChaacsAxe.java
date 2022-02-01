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

public class ChaacsAxe extends BaseItem {

    public ChaacsAxe() {
        super(Item.CHAACS_AXE, Material.NETHERITE_AXE, ItemRarity.MYTHICAL, Item.CHAACS_AXE.version());
        setLore("Bring down the lightning!");
        setFinal();
    }

    @Override
    protected void setAttributes() {
        AttributeModifier damageModifer = new AttributeModifier(UUID.randomUUID(), "chaac_damage", 35.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, damageModifer);
        AttributeModifier speedModifer = new AttributeModifier(UUID.randomUUID(), "chaac_speed", -6.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, speedModifer);
    }

    @Override
    protected void setEnchantments() {
        meta.addEnchant(Enchantment.DAMAGE_ALL, 10, true);
    }

    @Override
    protected void setTags() {
        DataUtils.set(meta, "strike_lightning", PersistentDataType.INTEGER, 1);
    }

    @Override
    protected void setRecipe() {
        ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(RamMMO.getInstance(), item.getRecipeKey()), itemStack);
        shapedRecipe.shape("ss ", "sn ", " n ");
        shapedRecipe.setIngredient('s', ItemManager.getItem(Item.ENCHANTED_NETHERSTAR));
        shapedRecipe.setIngredient('n', ItemManager.getItem(Item.ENCHANTED_NETHERITE));
        recipe = shapedRecipe;
    }
}
