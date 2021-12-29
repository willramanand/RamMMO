package com.gmail.willramanand.RamMMO.enums;

public enum Passives implements Passive {
    MINING_HASTE("mining", "haste"),
    FORAGING_HASTE("foraging", "haste"),
    EXCAVATION_HASTE("excavation", "haste"),
    AGILITY_JUMP_BOOST("agility", "jump_boost"),
    AGILITY_SPEED("agility", "speed"),
    FISHING_CONDUIT_POWER("fishing", "conduit_power"),
    FISHING_DOLPHINS_GRACE("fishing", "dolphins_grace");

    private final String skill;
    private final String passive;

    Passives(String skill, String passive) {
        this.skill = skill;
        this.passive = passive;
    }

    public String getSkill() {
        return skill;
    }

    public String getPassive() {
        return passive;
    }

}
