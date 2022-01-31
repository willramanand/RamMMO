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

public class FenrirsFang extends BaseItem {

    public FenrirsFang() {
        super(Item.FENRIRS_FANG, Material.NETHERITE_SWORD, ItemRarity.MYTHICAL, Item.FENRIRS_FANG.version());
    }

    @Override
    public void setAttributes() {
        ItemMeta meta = itemStack.getItemMeta();

        AttributeModifier damageModifer = new AttributeModifier(UUID.randomUUID(), "fang_damage", 16.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, damageModifer);

        itemStack.setItemMeta(meta);
    }

    @Override
    public void setLore() {
        ItemMeta meta = itemStack.getItemMeta();

        meta.displayName(item.getName().color(rarity.color()).decoration(TextDecoration.ITALIC, false));

        List<Component> lore = new ArrayList<>();
        lore.add(Component.text(""));
        lore.add(Component.text("Let your foes feel Fenrir's might.").decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text(""));
        lore.add(rarity.rarity());

        meta.lore(lore);

        itemStack.setItemMeta(meta);
    }

    @Override
    public void setEnchantments() {
        ItemMeta meta = itemStack.getItemMeta();

        meta.addEnchant(Enchantment.DAMAGE_ALL, 10, true);

        itemStack.setItemMeta(meta);
    }

    @Override
    public void setTags() {
        ItemMeta meta = itemStack.getItemMeta();

        meta.getPersistentDataContainer().set(new NamespacedKey(RamMMO.getInstance(), item.getClassName()), PersistentDataType.INTEGER, version);
        itemStack.setItemMeta(meta);
    }

    @Override
    public void setRecipe() {
        ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(RamMMO.getInstance(), item.getRecipeKey()), itemStack);
        shapedRecipe.shape(" f ", "nsn", " e ");
        shapedRecipe.setIngredient('e', ItemManager.getItem(Item.ENCHANTED_EMERALD));
        shapedRecipe.setIngredient('n', ItemManager.getItem(Item.ENCHANTED_NETHERITE));
        shapedRecipe.setIngredient('f', ItemManager.getItem(Item.FIERY_SCALE));
        shapedRecipe.setIngredient('s', Material.NETHERITE_SWORD);
        recipe = shapedRecipe;
    }
}
