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
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NetherfireElytra extends BaseItem {

    public NetherfireElytra() {
        super(Item.NETHERFIRE_ELYTRA, Material.ELYTRA, ItemRarity.MYTHICAL, Item.NETHERFIRE_ELYTRA.version());
    }

    @Override
    public void setAttributes() {
        ItemMeta meta = itemStack.getItemMeta();

        AttributeModifier armorModifier = new AttributeModifier(UUID.randomUUID(), "armor", 8.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR, armorModifier);
        AttributeModifier armorToughModifier = new AttributeModifier(UUID.randomUUID(), "armor_toughness", 3.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, armorToughModifier);
        AttributeModifier knockModifier = new AttributeModifier(UUID.randomUUID(), "armor", 0.1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);
        meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, knockModifier);

        itemStack.setItemMeta(meta);
    }

    @Override
    public void setLore() {
        ItemMeta meta = itemStack.getItemMeta();

        meta.displayName(item.getName().color(rarity.color()).decoration(TextDecoration.ITALIC, false));

        List<Component> lore = new ArrayList<>();
        lore.add(Component.text(""));
        lore.add(Component.text("Glide infinitely").decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("Grants immunity to fire").decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("Set Bonus: -5% Damage Taken").decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text(""));
        lore.add(rarity.rarity());

        meta.lore(lore);

        itemStack.setItemMeta(meta);
    }

    @Override
    public void setEnchantments() {
        ItemMeta meta = itemStack.getItemMeta();

        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
        meta.addEnchant(Enchantment.DURABILITY, 5, true);

        itemStack.setItemMeta(meta);
    }

    @Override
    public void setTags() {
        ItemMeta meta = itemStack.getItemMeta();

        meta.getPersistentDataContainer().set(new NamespacedKey(RamMMO.getInstance(), item.getClassName()), PersistentDataType.INTEGER, version);
        meta.getPersistentDataContainer().set(new NamespacedKey(RamMMO.getInstance(), "infinite_flight"), PersistentDataType.INTEGER, 1);
        meta.getPersistentDataContainer().set(new NamespacedKey(RamMMO.getInstance(), "fire_immunity"), PersistentDataType.INTEGER, 1);
        meta.getPersistentDataContainer().set(new NamespacedKey(RamMMO.getInstance(), "cannot_burn"), PersistentDataType.INTEGER, 1);
        meta.getPersistentDataContainer().set(new NamespacedKey(RamMMO.getInstance(), "reduced_dmg_set"), PersistentDataType.INTEGER, 1);

        itemStack.setItemMeta(meta);
    }

    @Override
    public void setRecipe() {
        ShapelessRecipe newRecipe = new ShapelessRecipe(new NamespacedKey(RamMMO.getInstance(), item.getRecipeKey()), itemStack);
        newRecipe.addIngredient(ItemManager.getItem(Item.NETHERFIRE_CHEST));
        newRecipe.addIngredient(ItemManager.getItem(Item.EMPOWERED_ELYTRA));
        recipe = newRecipe;
    }
}
