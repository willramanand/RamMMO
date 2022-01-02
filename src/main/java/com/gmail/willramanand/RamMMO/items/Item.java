package com.gmail.willramanand.RamMMO.items;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import java.util.ArrayList;
import java.util.List;

public enum Item {
    PHANTOM_PLATE("Phantom Plate", Material.PHANTOM_MEMBRANE
            , new String[]{"This item feels as though it could float."}
            , new Enchantment[]{Enchantment.PROTECTION_FALL}
            , new int[]{10}),

    EMPOWERED_ELYTRA("Empowered Elytra", Material.ELYTRA
            , new String[]{"Glide infinitely"}
            , new Enchantment[]{Enchantment.DURABILITY}
            , new int[]{5}),
    
    FIERY_SCALE("Fiery Scale", Material.FLINT 
            , new String[]{"This item is warm to the touch."}
            , new Enchantment[]{Enchantment.PROTECTION_FIRE, Enchantment.DURABILITY}
            , new int[]{5, 5}),
    
    NETHERFIRE_HELM("Netherfire Helmet", Material.NETHERITE_HELMET
            , new String[]{"Grants immunity to harmful effects.", "Set Bonus: -20% Damage Taken"}
            , new Enchantment[]{Enchantment.DURABILITY, Enchantment.PROTECTION_ENVIRONMENTAL}
            , new int[]{5, 5}),
    
    NETHERFIRE_CHEST("Netherfire Chestplate", Material.NETHERITE_CHESTPLATE
            , new String[]{"Grants immunity to fire.", "Set Bonus: -20% Damage Taken"}
            , new Enchantment[]{Enchantment.DURABILITY, Enchantment.PROTECTION_ENVIRONMENTAL}
            , new int[]{5, 5}),
    
    NETHERFIRE_LEGS("Netherfire Leggings", Material.NETHERITE_LEGGINGS
            , new String[]{"Grants increased speed", "Set Bonus: -20% Damage Taken"}
            , new Enchantment[]{Enchantment.DURABILITY, Enchantment.PROTECTION_ENVIRONMENTAL}
            , new int[]{5, 5}),
    
    NETHERFIRE_BOOTS("Netherfire Boots", Material.NETHERITE_BOOTS
            , new String[]{"Grants immunity to fall damage", "Set Bonus: -20% Damage Taken"}
            , new Enchantment[]{Enchantment.DURABILITY, Enchantment.PROTECTION_ENVIRONMENTAL}
            , new int[]{5, 5}),

    NETHERFIRE_ELYTRA("Netherfire Elytra", Material.ELYTRA
            , new String[] {"Glide infinitely", "Grants immunity to fire.", "Set Bonus: -20% Damage Taken"}
            , new Enchantment[] {Enchantment.DURABILITY, Enchantment.PROTECTION_ENVIRONMENTAL}
            , new int[] {5, 5}),
    ;

    private String name;
    private String[] lore;
    private Material baseItem;
    private Enchantment[] enchantments;
    private int[] enchantLvls;

    Item(String name, Material baseItem, String[] lore, Enchantment[] enchantments, int[] enchantLvls) {
        this.name = name;
        this.baseItem = baseItem;
        this.lore = lore;
        this.enchantments = enchantments;
        this.enchantLvls = enchantLvls;
    }

    public Component getName() {
        Component nameComp = Component.newline().content(this.name);
        return nameComp;
    }

    public Material getBaseItem() {
        return baseItem;
    }

    public List<Component> getLore() {
        List<Component> loreComps = new ArrayList<>();
        for (int i = 0; i < lore.length; i++) {
            loreComps.add(Component.newline().content(lore[i]));
        }
        return loreComps;
    }
    
    public Enchantment[] getEnchantments() {
        return enchantments;
    }

    public int[] getEnchantLvls() {
        return enchantLvls;
    }
}
