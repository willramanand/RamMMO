package com.gmail.willramanand.RamMMO.item;

import net.kyori.adventure.text.Component;

public enum Item {

    PHANTOM_PLATE("Phantom Plate", "phantomplate", 0),
    FIERY_SCALE("Fiery Scale", "fieryscale", 0),

    ENCHANTED_GOLD("Enchanted Gold", "enchantedgold", 0),
    ENCHANTED_DIAMOND("Enchanted Diamond", "enchanteddiamond", 0),
    ENCHANTED_EMERALD("Enchanted Emerald", "enchantedemerald", 0),
    ENCHANTED_NETHERITE("Enchanted Netherite", "enchantednetherite", 0),

    SILK_TOUCHER("Silk Toucher", "silktoucher", 0),
    APOLLOS_BOW("Apollo's Bow", "apollobow", 0),
    FENRIRS_FANG("Fenrir's Fang", "fenrirsfang", 0),

    EMPOWERED_ELYTRA("Empowered Elytra", "empoweredelytra", 0),

    NETHERFIRE_HELM("Netherfire Helmet", "netherfirehelm", 0),
    NETHERFIRE_CHEST("Netherfire Chestplate", "netherfirechest", 0),
    NETHERFIRE_LEGS("Netherfire Leggings", "netherfirelegs", 0),
    NETHERFIRE_BOOTS("Netherfire Boots", "netherfireboots", 0),

    NETHERFIRE_ELYTRA("Netherfire Elytra", "netherfireelytra", 0),

    ;

    private String name;
    private String className;
    private int version;

    Item(String name, String className, int version) {
        this.name = name;
        this.className = className;
        this.version = version;
    }

    public Component getName() {
        Component nameComp = Component.newline().content(this.name);
        return nameComp;
    }

    public String getClassName() { return className; }

    public int version() { return version; }
}
