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
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class NetherfireElytra extends BaseItem {

    public NetherfireElytra() {
        super(Item.NETHERFIRE_ELYTRA, Material.ELYTRA, ItemRarity.MYTHICAL, Item.NETHERFIRE_ELYTRA.version());
        setLore("Glide infinitely", "Grants immunity to fire", "Set Bonus: -5% Damage Taken");
        setFinal();
    }

    @Override
    protected void setAttributes() {
        AttributeModifier armorModifier = new AttributeModifier(UUID.randomUUID(), "armor", 8.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR, armorModifier);
        AttributeModifier armorToughModifier = new AttributeModifier(UUID.randomUUID(), "armor_toughness", 3.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, armorToughModifier);
        AttributeModifier knockModifier = new AttributeModifier(UUID.randomUUID(), "armor", 0.1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);
        meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, knockModifier);
    }

    @Override
    protected void setEnchantments() {
        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
        meta.addEnchant(Enchantment.DURABILITY, 5, true);
    }

    @Override
    protected void setTags() {
        DataUtils.set(meta, "infinite_flight", PersistentDataType.INTEGER, 1);
        DataUtils.set(meta, "fire_immunity", PersistentDataType.INTEGER, 1);
        DataUtils.set(meta, "cannot_burn", PersistentDataType.INTEGER, 1);
        DataUtils.set(meta, "reduced_dmg_set", PersistentDataType.INTEGER, 1);
    }

    @Override
    protected void setRecipe() {
        ShapelessRecipe newRecipe = new ShapelessRecipe(new NamespacedKey(RamMMO.getInstance(), item.getRecipeKey()), itemStack);
        newRecipe.addIngredient(ItemManager.getItem(Item.NETHERFIRE_CHEST));
        newRecipe.addIngredient(ItemManager.getItem(Item.EMPOWERED_ELYTRA));
        recipe = newRecipe;
    }
}
