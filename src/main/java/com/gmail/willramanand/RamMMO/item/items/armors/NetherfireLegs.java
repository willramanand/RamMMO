package com.gmail.willramanand.RamMMO.item.items.armors;

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
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.SmithingRecipe;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class NetherfireLegs extends BaseItem {

    public NetherfireLegs() {
        super(Item.NETHERFIRE_LEGS, Material.NETHERITE_LEGGINGS, ItemRarity.MYTHICAL, Item.NETHERFIRE_LEGS.version());
        setLore("Reduces Knockback", "Set Bonus: -5% Damage Taken");
        setFinal();
    }

    @Override
    protected void setAttributes() {
        AttributeModifier armorModifier = new AttributeModifier(UUID.randomUUID(), "armor", 6.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS);
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR, armorModifier);
        AttributeModifier armorToughModifier = new AttributeModifier(UUID.randomUUID(), "armor_toughness", 3.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS);
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, armorToughModifier);
        AttributeModifier knockModifier = new AttributeModifier(UUID.randomUUID(), "armor", 0.3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS);
        meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, knockModifier);
    }

    @Override
    protected void setEnchantments() {
        meta.addEnchant(Enchantment.DURABILITY, 5, true);
    }

    @Override
    protected void setTags() {
        DataUtils.set(meta, "reduced_dmg_set", PersistentDataType.INTEGER, 1);
    }

    @Override
    protected void setRecipe() {
        SmithingRecipe newRecipe;
        newRecipe = new SmithingRecipe(new NamespacedKey(RamMMO.getInstance(), item.getRecipeKey()), itemStack,
                new RecipeChoice.MaterialChoice(Material.NETHERITE_LEGGINGS), new RecipeChoice.ExactChoice(ItemManager.getItem(Item.FIERY_SCALE)), false);
        recipe = newRecipe;
    }
}
