package com.gmail.willramanand.RamMMO.enums;

import org.bukkit.potion.PotionEffectType;

public enum Passives implements Passive {
    MINING_HASTE("Mining Haste", "mining", "haste", PotionEffectType.FAST_DIGGING),
    WOODCUTTING_HASTE("Woodcutting Haste", "woodcutting", "haste", PotionEffectType.FAST_DIGGING),
    EXCAVATION_HASTE("Excavation Haste", "excavation", "haste", PotionEffectType.FAST_DIGGING),
    AGILITY_JUMP_BOOST("Agility Jump Boost", "agility", "jump_boost", PotionEffectType.JUMP),
    FISHING_CONDUIT_POWER("Fishing Conduit Power", "fishing", "conduit_power", PotionEffectType.CONDUIT_POWER),
    FISHING_DOLPHINS_GRACE("Fishing Dolphin's Grace", "fishing", "dolphins_grace", PotionEffectType.DOLPHINS_GRACE);

    private final String displayName;
    private final String skill;
    private final String passive;
    private final PotionEffectType effect;

    Passives(String displayName, String skill, String passive, PotionEffectType effect) {
        this.displayName = displayName;
        this.skill = skill;
        this.passive = passive;
        this.effect = effect;
    }

    public String getDisplayName() { return displayName; }

    public String getSkill() {
        return skill;
    }

    public String getPassive() {
        return passive;
    }

    public PotionEffectType getEffect() {
        return effect;
    }

}
