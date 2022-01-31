package com.gmail.willramanand.RamMMO.item.items;

import com.gmail.willramanand.RamMMO.RamMMO;
import com.gmail.willramanand.RamMMO.item.Item;
import com.gmail.willramanand.RamMMO.item.ItemManager;
import com.gmail.willramanand.RamMMO.item.ItemRarity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.SmithingRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NetherfireLegs extends BaseItem {

    public NetherfireLegs() {
        super(Item.NETHERFIRE_LEGS, Material.NETHERITE_LEGGINGS, ItemRarity.MYTHICAL, Item.NETHERFIRE_LEGS.version());
    }

    @Override
    public void setAttributes() {
        ItemMeta meta = itemStack.getItemMeta();

        AttributeModifier armorModifier = new AttributeModifier(UUID.randomUUID(), "armor", 6.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS);
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR, armorModifier);
        AttributeModifier armorToughModifier = new AttributeModifier(UUID.randomUUID(), "armor_toughness", 3.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS);
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, armorToughModifier);
        AttributeModifier knockModifier = new AttributeModifier(UUID.randomUUID(), "armor", 0.3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS);
        meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, knockModifier);

        itemStack.setItemMeta(meta);
    }

    @Override
    public void setLore() {
        ItemMeta meta = itemStack.getItemMeta();

        meta.displayName(item.getName().color(rarity.color()).decoration(TextDecoration.ITALIC, false));

        List<Component> lore = new ArrayList<>();
        lore.add(Component.text(""));
        lore.add(Component.text("Reduces knockback").decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("Set Bonus: -5% Damage Taken").decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("").decoration(TextDecoration.ITALIC, false));
        lore.add(rarity.rarity());

        meta.lore(lore);

        itemStack.setItemMeta(meta);
    }

    @Override
    public void setEnchantments() {
        ItemMeta meta = itemStack.getItemMeta();

        meta.addEnchant(Enchantment.DURABILITY, 5, true);

        itemStack.setItemMeta(meta);
    }

    @Override
    public void setTags() {
        ItemMeta meta = itemStack.getItemMeta();

        meta.getPersistentDataContainer().set(new NamespacedKey(RamMMO.getInstance(), item.getClassName()), PersistentDataType.INTEGER, version);
        meta.getPersistentDataContainer().set(new NamespacedKey(RamMMO.getInstance(), "reduced_dmg_set"), PersistentDataType.INTEGER, 1);

        itemStack.setItemMeta(meta);
    }

    @Override
    public void setRecipe() {
        SmithingRecipe newRecipe;
        newRecipe = new SmithingRecipe(new NamespacedKey(RamMMO.getInstance(), item.getRecipeKey()), itemStack,
                new RecipeChoice.MaterialChoice(Material.NETHERITE_LEGGINGS), new RecipeChoice.ExactChoice(ItemManager.getItem(Item.FIERY_SCALE)), false);
        recipe = newRecipe;
    }
}
