package com.gmail.willramanand.RamMMO.mobs;

public enum MobTier {

    COMMON(0, 0, 0, 0, 1, ""),
    UNCOMMON(1, 1, 2, 1, 1.5, "&2Uncommon "),
    RARE(2, 1, 2, 2, 1.75, "&1Rare "),
    EPIC(3, 2, 4, 5, 0.8, "&5Epic "),
    LEGENDARY(4, 3, 4, 7, 0.8, "&6Legendary ")

    ;

    private final int metaValue;
    private final int healthMult;
    private final int armor;
    private final int damageMult;
    private final double speedMult;
    private final String prefix;

    MobTier(int metaValue, int healthMult, int armor, int damageMult, double speedMult, String prefix) {
        this.metaValue = metaValue;
        this.healthMult = healthMult;
        this.armor = armor;
        this.damageMult = damageMult;
        this.speedMult = speedMult;
        this.prefix = prefix;
    }

    public int getMetaValue() { return metaValue; }

    public int getHealthMult() { return healthMult; }

    public int getArmor() { return armor; }

    public int getDamageMult() { return damageMult; }

    public double getSpeedMult() { return speedMult; }

    public String getPrefix() { return prefix; }
}
