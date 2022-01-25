package com.gmail.willramanand.RamMMO.enums;

import org.bukkit.potion.PotionEffectType;

public enum Passives implements Passive {
    MINING_HASTE("mining", "haste", PotionEffectType.FAST_DIGGING),
    WOODCUTTING_HASTE("woodcutting", "haste", PotionEffectType.FAST_DIGGING),
    EXCAVATION_HASTE("excavation", "haste", PotionEffectType.FAST_DIGGING),
    AGILITY_JUMP_BOOST("agility", "jump_boost", PotionEffectType.JUMP),
    FISHING_CONDUIT_POWER("fishing", "conduit_power", PotionEffectType.CONDUIT_POWER),
    FISHING_DOLPHINS_GRACE("fishing", "dolphins_grace", PotionEffectType.DOLPHINS_GRACE);

    private final String skill;
    private final String passive;
    private final PotionEffectType effect;

    Passives(String skill, String passive, PotionEffectType effect) {
        this.skill = skill;
        this.passive = passive;
        this.effect = effect;
    }

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
