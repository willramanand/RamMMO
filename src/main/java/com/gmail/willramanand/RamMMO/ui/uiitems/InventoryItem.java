package com.gmail.willramanand.RamMMO.ui.uiitems;

import com.gmail.willramanand.RamMMO.RamMMO;
import com.gmail.willramanand.RamMMO.enums.Passive;
import com.gmail.willramanand.RamMMO.item.Item;
import com.gmail.willramanand.RamMMO.item.ItemManager;
import com.gmail.willramanand.RamMMO.player.MMOPlayer;
import com.gmail.willramanand.RamMMO.utils.ColorUtils;
import com.gmail.willramanand.RamMMO.utils.DataUtils;
import com.gmail.willramanand.RamMMO.utils.Formatter;
import com.gmail.willramanand.RamSkills.RamSkills;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class InventoryItem {

    public static ItemStack getCustomItem(Item item) {
        return new ItemStack(ItemManager.getItem(item));
    }

    public static ItemStack getPassiveItem(Passive passive, Material material, Player player) {
        MMOPlayer mmoPlayer = RamMMO.getInstance().getPlayerManager().getPlayerData(player);
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text(ColorUtils.colorMessage("&b" + passive.getDisplayName())));
        DataUtils.set(meta, "passive_item", PersistentDataType.STRING, passive.getSkill() + "_" + passive.getPassive());
        List<Component> lore = new ArrayList<>();
        lore.add(Component.empty());
        lore.add(Component.text(ColorUtils.colorMessage("&6Toggle:")));
        if (mmoPlayer.getPassives(passive)) {
            lore.add(Component.text(ColorUtils.colorMessage("&a[Enabled]")));
            meta.addEnchant(Enchantment.MENDING, 1, true);
        } else if (!mmoPlayer.getPassives(passive)) {
            lore.add(Component.text(ColorUtils.colorMessage("&c[Disabled]")));
        }
        lore.add(Component.empty());
        lore.add(Component.text(ColorUtils.colorMessage("&6Related Skill:")));
        lore.add(Component.text(ColorUtils.colorMessage("&3" + Formatter.nameFormat(passive.getSkill()))));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        meta.lore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack getHead(Player player) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(player.getUniqueId());
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skull = (SkullMeta) item.getItemMeta();
        skull.displayName(Component.text(player.getName()).decoration(TextDecoration.ITALIC, false));
        skull.setOwningPlayer(offlinePlayer);
        item.setItemMeta(skull);
        return item;
    }


    public static ItemStack getClose() {
        ItemStack item = new ItemStack(Material.BARRIER);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text(ColorUtils.colorMessage("&4Close")).decoration(TextDecoration.ITALIC, false));
        meta.getPersistentDataContainer().set(new NamespacedKey(RamMMO.getInstance(), "close_button"), PersistentDataType.INTEGER, 0);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack getBlank() {
        ItemStack item = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.empty());
        meta.getPersistentDataContainer().set(new NamespacedKey(RamMMO.getInstance(), "empty"), PersistentDataType.INTEGER, 0);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return item;
    }
}
