package com.gmail.willramanand.RamMMO.item.items;

import com.gmail.willramanand.RamMMO.RamMMO;
import com.gmail.willramanand.RamMMO.item.Item;
import com.gmail.willramanand.RamMMO.item.ItemManager;
import com.gmail.willramanand.RamMMO.item.ItemRarity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class BuilderWand extends BaseItem {

    public BuilderWand() {
        super(Item.BUILDERS_WAND, Material.BLAZE_ROD, ItemRarity.MYTHICAL, Item.BUILDERS_WAND.version());
    }

    @Override
    public void setAttributes() {

    }

    @Override
    public void setLore() {
        ItemMeta meta = itemStack.getItemMeta();

        meta.displayName(item.getName().color(rarity.color()).decoration(TextDecoration.ITALIC, false));

        List<Component> lore = new ArrayList<>();
        lore.add(Component.text(""));
        lore.add(Component.text("YES WE CAN!").decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text(""));
        lore.add(rarity.rarity());

        meta.lore(lore);

        itemStack.setItemMeta(meta);
    }

    @Override
    public void setEnchantments() {
        ItemMeta meta = itemStack.getItemMeta();

        meta.addEnchant(Enchantment.DURABILITY, 5, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

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
        shapedRecipe.shape(" n ",
                            " g ",
                            " g ");

        shapedRecipe.setIngredient('g', ItemManager.getItem(Item.ENCHANTED_NETHERSTAR));
        shapedRecipe.setIngredient('n', ItemManager.getItem(Item.ENCHANTED_NETHERITE));
        recipe = shapedRecipe;
    }
}
