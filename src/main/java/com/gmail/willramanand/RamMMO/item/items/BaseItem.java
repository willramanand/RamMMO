package com.gmail.willramanand.RamMMO.item.items;

import com.gmail.willramanand.RamMMO.item.Item;
import com.gmail.willramanand.RamMMO.item.ItemRarity;
import com.gmail.willramanand.RamMMO.utils.DataUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseItem {

    protected Item item;
    protected ItemStack itemStack;
    protected ItemMeta meta;
    protected Recipe recipe;
    protected ItemRarity rarity;
    protected int version;

    public BaseItem(Item item, Material material, ItemRarity rarity, int version) {
        this.item = item;
        this.itemStack = new ItemStack(material);
        this.rarity = rarity;
        this.version = version;
        this.meta = itemStack.getItemMeta();

        meta.displayName(item.getName().color(rarity.color()).decoration(TextDecoration.ITALIC, false));

        setAttributes();
        setVersion();
        setEnchantments();
        setTags();
    }

    protected abstract void setAttributes();

    protected void setLore(String... args) {
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text(""));

        for (String arg : args) {
            lore.add(Component.text(arg).decoration(TextDecoration.ITALIC, false));
        }

        lore.add(Component.text(""));
        lore.add(rarity.rarity());

        meta.lore(lore);
    }

    protected abstract void setEnchantments();

    protected abstract void setTags();

    protected abstract void setRecipe();

    protected void setVersion() {
        DataUtils.set(meta, item.getClassName(), PersistentDataType.INTEGER, version);
    }

    protected void setFinal() {
        itemStack.setItemMeta(meta);
        setRecipe();
    }

    public ItemStack get()
    {
        return itemStack;
    }

    public Recipe recipe() {
        return recipe;
    }
}
