package com.gmail.willramanand.RamMMO.utils;

import com.gmail.willramanand.RamMMO.RamMMO;
import com.google.common.collect.ImmutableList;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class BossUtils {

    private final List<EntityType> validBossTypes = ImmutableList.of(EntityType.BLAZE, EntityType.ZOMBIE, EntityType.WITHER_SKELETON, EntityType.SKELETON, EntityType.PILLAGER, EntityType.SLIME, EntityType.VINDICATOR,
            EntityType.VEX, EntityType.RAVAGER, EntityType.VINDICATOR, EntityType.ENDERMAN, EntityType.DROWNED, EntityType.STRAY, EntityType.PHANTOM);
    private LivingEntity currentBoss;

    public static void setBoss(LivingEntity entity) {
        if (entity.getPersistentDataContainer().has(new NamespacedKey(RamMMO.getInstance(), "boss"))) return;
        entity.getPersistentDataContainer().set(new NamespacedKey(RamMMO.getInstance(),"boss"), PersistentDataType.INTEGER, 1);
    }

    public static boolean isBoss (LivingEntity entity) {
        if (entity.getPersistentDataContainer().has(new NamespacedKey(RamMMO.getInstance(), "boss"))) return true;
        return false;
    }

    public static void removeBoss(LivingEntity entity) {
        if (!(entity.isDead())) {
            entity.damage(9999999);
        }
    }


}
