package com.gmail.willramanand.RamMMO.boss;

import com.gmail.willramanand.RamMMO.utils.BossUtils;
import com.gmail.willramanand.RamMMO.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public abstract class BaseBoss {

    protected final String displayName;
    protected final Bosses type;
    protected BossBar bossBar;
    protected Class entityClass;

    public <T> BaseBoss(Bosses type, Class<T> entityClass) {
        this.type = type;
        this.displayName = type.displayName();
        this.entityClass = entityClass;
    }

    public Entity spawn(Location loc) {
            return loc.getWorld().spawn(loc, entityClass, entity -> {
                LivingEntity livingEntity = (LivingEntity) entity;
                BossUtils.setBoss(livingEntity, type);
                setAttributes(livingEntity);
                setDisplayName(livingEntity);
                setTags(livingEntity);
                setEquipment(livingEntity);
                setBossBar();
                setExtras(livingEntity);
            });
    }

    public void setDisplayName(LivingEntity entity) {
        entity.setCustomName(ColorUtils.colorMessage(displayName));
        entity.setCustomNameVisible(true);
    }

    public abstract void setAttributes(LivingEntity entity);

    public abstract void setTags(LivingEntity entity);

    public abstract void setEquipment(LivingEntity entity);

    public void setBossBar() {
        bossBar = Bukkit.createBossBar(ColorUtils.colorMessage(displayName), BarColor.PURPLE, BarStyle.SOLID, BarFlag.CREATE_FOG, BarFlag.DARKEN_SKY);
    }

    public abstract void setExtras(LivingEntity entity);

    public BossBar getBossBar() { return bossBar; }
}
