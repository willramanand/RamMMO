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
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ChaacsAxe extends BaseItem {

    public ChaacsAxe() {
        super(Item.CHAACS_AXE, Material.NETHERITE_AXE, ItemRarity.MYTHICAL, Item.CHAACS_AXE.version());
    }

    @Override
    public void setAttributes() {
        ItemMeta meta = itemStack.getItemMeta();

        AttributeModifier damageModifer = new AttributeModifier(UUID.randomUUID(), "chaac_damage", 25.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, damageModifer);
        AttributeModifier speedModifer = new AttributeModifier(UUID.randomUUID(), "chaac_speed", -3.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, speedModifer);

        itemStack.setItemMeta(meta);
    }

    @Override
    public void setLore() {
        ItemMeta meta = itemStack.getItemMeta();

        meta.displayName(item.getName().color(rarity.color()).decoration(TextDecoration.ITALIC, false));

        List<Component> lore = new ArrayList<>();
        lore.add(Component.text(""));
        lore.add(Component.text("Bring down the lightning!").decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text(""));
        lore.add(rarity.rarity());

        meta.lore(lore);

        itemStack.setItemMeta(meta);
    }

    @Override
    public void setEnchantments() {
        ItemMeta meta = itemStack.getItemMeta();

        meta.addEnchant(Enchantment.DAMAGE_ALL, 6, true);

        itemStack.setItemMeta(meta);
    }

    @Override
    public void setTags() {
        ItemMeta meta = itemStack.getItemMeta();

        meta.getPersistentDataContainer().set(new NamespacedKey(RamMMO.getInstance(), item.getClassName()), PersistentDataType.INTEGER, version);
        meta.getPersistentDataContainer().set(new NamespacedKey(RamMMO.getInstance(), "strike_lightning"), PersistentDataType.INTEGER, 1);
        itemStack.setItemMeta(meta);
    }

    @Override
    public void setRecipe() {
        ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(RamMMO.getInstance(), item.getRecipeKey()), itemStack);
        shapedRecipe.shape("ss ", "sn ", " n ");
        shapedRecipe.setIngredient('s', ItemManager.getItem(Item.ENCHANTED_NETHERSTAR));
        shapedRecipe.setIngredient('n', ItemManager.getItem(Item.ENCHANTED_NETHERITE));
        recipe = shapedRecipe;
    }
}
