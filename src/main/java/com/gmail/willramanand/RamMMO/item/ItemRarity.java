package com.gmail.willramanand.RamMMO.item;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;

public enum ItemRarity {
    COMMON(TextColor.color(255, 255, 255), Component.text("COMMON").color(TextColor.color(255, 255, 255)).decoration(TextDecoration.ITALIC, false).decoration(TextDecoration.BOLD, true)),
    UNCOMMON(TextColor.color(0, 170, 0), Component.text("UNCOMMON").color(TextColor.color(0, 170, 0)).decoration(TextDecoration.ITALIC, false).decoration(TextDecoration.BOLD, true)),
    RARE(TextColor.color(0, 0, 170), Component.text("RARE").color(TextColor.color(0, 0, 170)).decoration(TextDecoration.ITALIC, false).decoration(TextDecoration.BOLD, true)),
    EPIC(TextColor.color(170, 0, 170), Component.text("EPIC").color(TextColor.color(170, 0, 170)).decoration(TextDecoration.ITALIC, false).decoration(TextDecoration.BOLD, true)),
    LEGENDARY(TextColor.color(255, 170, 0), Component.text("LEGENDARY").color(TextColor.color(255, 170, 0)).decoration(TextDecoration.ITALIC, false).decoration(TextDecoration.BOLD, true)),
    MYTHICAL(TextColor.color(170, 0, 0), Component.text("MYTHICAL").color(TextColor.color(170, 0, 0)).decoration(TextDecoration.ITALIC, false).decoration(TextDecoration.BOLD, true))

    ;

    private final TextColor color;
    private final Component rarityText;

    ItemRarity(TextColor color, Component rarityText) {
        this.color = color;
        this.rarityText = rarityText;
    }

    public TextColor color() {
        return color;
    }

    public Component rarity() {
        return rarityText;
    }
}
