package com.gmail.willramanand.RamMMO.utils;

import com.gmail.willramanand.RamMMO.boss.Bosses;
import org.bukkit.entity.LivingEntity;
import org.bukkit.persistence.PersistentDataType;

public class BossUtils {

    public static void setBoss(LivingEntity entity, Bosses boss) {
        if (DataUtils.has(entity, "boss")) return;
        DataUtils.set(entity, "boss", PersistentDataType.STRING, boss.toString());
    }

    public static Bosses getBoss(LivingEntity entity) {
        if (!(DataUtils.has(entity, "boss"))) return null;
        return Bosses.valueOf(DataUtils.get(entity, "boss", PersistentDataType.STRING));
    }

    public static boolean isBoss (LivingEntity entity) {
        return DataUtils.has(entity, "boss");
    }



}
