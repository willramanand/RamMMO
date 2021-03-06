package com.gmail.willramanand.RamMMO.item;

import net.kyori.adventure.text.Component;

public enum Item {

    PHANTOM_PLATE("Phantom Plate", "phantomplate", "phantom_plate", 0),
    FIERY_SCALE("Fiery Scale", "fieryscale", "fiery_scale", 2),
    MINOTAURS_HIDE("Minotaur's Hide", "minotaurhide", "minotaur_hide", 0),
    APOCALYPSE_HORSE_EGG("Horse of the Apocalypse Egg", "apocalypseegg", "apocalypse_egg", 0),
    ENDER_GEM("Ender Gem", "endergem", "ender_gem", 1),
    ACTIVE_ENDER_GEM("Ender Gem", "activeendergem", "active_ender_gem", 0),
    LEVIATHANS_HEART("Leviathan's Heart", "leviathansheart", "leviathans_heart", 0),

    ENCHANTED_NETHERSTAR("Enchanted Netherstar", "enchantednetherstar", "enchanted_netherstar", 1),
    ENCHANTED_GOLD("Enchanted Gold", "enchantedgold", "enchanted_gold", 1),
    ENCHANTED_DIAMOND("Enchanted Diamond", "enchanteddiamond", "enchanted_diamond", 1),
    ENCHANTED_EMERALD("Enchanted Emerald", "enchantedemerald", "enchanted_emerald", 1),
    ENCHANTED_NETHERITE("Enchanted Netherite", "enchantednetherite", "enchanted_netherite", 1),

    ENCHANTED_GOLD_BLOCK("Enchanted Gold Block", "enchantedgoldblock", "enchanted_gold_block", 1),
    ENCHANTED_DIAMOND_BLOCK("Enchanted Diamond Block", "enchanteddiamondblock", "enchanted_diamond_block", 1),
    ENCHANTED_EMERALD_BLOCK("Enchanted Emerald Block", "enchantedemeraldblock", "enchanted_emerald_block", 1),
    ENCHANTED_NETHERITE_BLOCK("Enchanted Netherite Block", "enchantednetheriteblock", "enchanted_netherite_block", 2),

    SILK_TOUCHER("Silk Toucher", "silktoucher", "silk_toucher", 0),
    VULCANS_PICK("Vulcan's Pick", "vulcanspick", "vulcans_pick", 0),
    APOLLOS_BOW("Apollo's Bow", "apollobow", "apollo_bow", 1),
    FENRIRS_FANG("Fenrir's Fang", "fenrirsfang", "fenrir_fang", 1),
    BUILDERS_WAND("Builder's Wand", "builderwand", "builder_wand", 0),
    POSEIDONS_TRIDENT("Poseidon's Trident", "poseidontrident", "poseidons_trident", 0),
    CHAACS_AXE("Chaac's Axe", "chaacaxe", "chaacs_axe", 1),
    MYTHRIL_DRILL("Mythril Drill", "mythrildrill", "mythril_drill", 1),

    EMPOWERED_ELYTRA("Empowered Elytra", "empoweredelytra", "empowered_elytra", 0),

    NETHERFIRE_HELM("Netherfire Helmet", "netherfirehelm", "netherfire_helm", 0),
    NETHERFIRE_CHEST("Netherfire Chestplate", "netherfirechest", "netherfire_chest", 0),
    NETHERFIRE_LEGS("Netherfire Leggings", "netherfirelegs", "netherfire_legs", 0),
    NETHERFIRE_BOOTS("Netherfire Boots", "netherfireboots", "netherfire_boots", 0),

    NETHERFIRE_ELYTRA("Netherfire Elytra", "netherfireelytra", "netherfire_elytra", 0),

    ;

    private final String name;
    private final String className;
    private final String recipeKey;
    private final int version;

    Item(String name, String className, String recipeKey, int version) {
        this.name = name;
        this.className = className;
        this.version = version;
        this.recipeKey = recipeKey;
    }

    public Component getName() {
        return Component.newline().content(this.name);
    }

    public String getClassName() { return className; }

    public int version() { return version; }

    public String getRecipeKey() { return recipeKey; }
}
