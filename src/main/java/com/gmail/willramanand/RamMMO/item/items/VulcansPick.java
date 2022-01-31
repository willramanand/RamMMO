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
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class VulcansPick extends BaseItem {

    public VulcansPick() {
        super(Item.VULCANS_PICK, Material.NETHERITE_PICKAXE, ItemRarity.MYTHICAL, Item.VULCANS_PICK.version());
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
        lore.add(Component.text("Break all pickaxe materials with a single swing").decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text(""));
        lore.add(rarity.rarity());

        meta.lore(lore);

        itemStack.setItemMeta(meta);
    }

    @Override
    public void setEnchantments() {
        ItemMeta meta = itemStack.getItemMeta();

        meta.addEnchant(Enchantment.DURABILITY, 5, true);
        meta.addEnchant(Enchantment.DIG_SPEED, 10, true);

        itemStack.setItemMeta(meta);
    }

    @Override
    public void setTags() {
        ItemMeta meta = itemStack.getItemMeta();

        meta.getPersistentDataContainer().set(new NamespacedKey(RamMMO.getInstance(), item.getClassName()), PersistentDataType.INTEGER, version);
        meta.getPersistentDataContainer().set(new NamespacedKey(RamMMO.getInstance(), "insta_break"), PersistentDataType.INTEGER, 1);

        itemStack.setItemMeta(meta);
    }

    @Override
    public void setRecipe() {
        ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(RamMMO.getInstance(), item.getRecipeKey()), itemStack);
        shapedRecipe.shape("fpf",
                            " s ",
                            " n ");
        shapedRecipe.setIngredient('p', Material.NETHERITE_PICKAXE);
        shapedRecipe.setIngredient('s', ItemManager.getItem(Item.ENCHANTED_NETHERSTAR));
        shapedRecipe.setIngredient('f', ItemManager.getItem(Item.FIERY_SCALE));
        shapedRecipe.setIngredient('n', ItemManager.getItem(Item.ENCHANTED_NETHERITE));
        recipe = shapedRecipe;
    }
}
