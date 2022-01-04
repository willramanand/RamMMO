package com.gmail.willramanand.RamMMO.boss;

import com.gmail.willramanand.RamMMO.RamMMO;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.LivingEntity;
import org.bukkit.persistence.PersistentDataType;

public class Boss extends MMOBoss {

    public boolean isBoss (LivingEntity entity) {
        if (entity.getPersistentDataContainer().has(new NamespacedKey(RamMMO.getInstance(), "isBoss"), PersistentDataType.INTEGER)
                && entity.getPersistentDataContainer().get(new NamespacedKey(RamMMO.getInstance(), "isBoss"), PersistentDataType.INTEGER) == 1) return true;
        return false;
    }

    @Override
    public void bossName() {

    }

    @Override
    public void setAttributes() {

    }

    @Override
    public void clearEffects() {

    }

    @Override
    public void nearbyEntities() {

    }
}
