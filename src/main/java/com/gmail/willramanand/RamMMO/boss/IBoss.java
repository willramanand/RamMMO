package com.gmail.willramanand.RamMMO.boss;

public interface IBoss {

    public static final String BOSS_PREFIX = "&4";

    public abstract void bossName();

    public abstract void setAttributes();

    public abstract void clearEffects();

    public abstract void nearbyEntities();
}
