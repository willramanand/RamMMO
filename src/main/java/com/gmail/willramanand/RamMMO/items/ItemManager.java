package com.gmail.willramanand.RamMMO.items;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class ItemManager {

    private static final Map<Item, ItemStack> items = new HashMap<>();

    public static void createItems() {
        for (Item item: Item.values()) {
            ItemStack newItem = new ItemStack(item.getBaseItem());
            ItemMeta meta = newItem.getItemMeta();

            meta.displayName(item.getName().color(TextColor.color(255, 170, 0)));

            List<Component> lore = new ArrayList<>();
            for (Component cp : item.getLore() ) {
                cp = cp.color(TextColor.color(255, 85, 255));
                lore.add(cp);
            }
            meta.lore(lore);

            for (int i = 0; i < item.getEnchantments().length; i++) {
                meta.addEnchant(item.getEnchantments()[i], item.getEnchantLvls()[i], true);
            }

            if (item == Item.NETHERFIRE_ELYTRA) {
                AttributeModifier armorModifier = new AttributeModifier(UUID.randomUUID(), "armor", 8.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);
                meta.addAttributeModifier(Attribute.GENERIC_ARMOR, armorModifier);
                AttributeModifier armorToughModifier = new AttributeModifier(UUID.randomUUID(), "armor_toughness", 3.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);
                meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, armorToughModifier);
                AttributeModifier knockModifier = new AttributeModifier(UUID.randomUUID(), "armor", 0.1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);
                meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, knockModifier);
            }

            newItem.setItemMeta(meta);

            items.put(item, newItem);
        }
    }

    public static ItemStack getItem(Item item) {
        return items.get(item);
    }
}
