package com.gmail.willramanand.RamMMO.boss.bosses;

import com.gmail.willramanand.RamMMO.boss.BaseBoss;
import com.gmail.willramanand.RamMMO.boss.Bosses;
import com.gmail.willramanand.RamMMO.utils.ColorUtils;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;

public class TheLeviathan extends BaseBoss {

    public TheLeviathan() {
        super(Bosses.THE_LEVIATHAN, ElderGuardian.class);
    }

    @Override
    public void setAttributes(LivingEntity entity) {
        entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(500);
        entity.setHealth(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
        entity.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(100);
        entity.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(1);
        entity.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).setBaseValue(15);
        entity.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(20);

        entity.setRemoveWhenFarAway(false);
    }

    @Override
    public void setTags(LivingEntity entity) {
    }

    @Override
    public void setEquipment(LivingEntity entity) {
    }

    @Override
    public void setExtras(LivingEntity entity) {
        for (int i = 0; i < 7; i++) {
            entity.getLocation().getWorld().spawn(entity.getLocation(), Guardian.class, guardian -> {
                guardian.setCustomName(ColorUtils.colorMessage("&bSea Serpent"));
                guardian.setCustomNameVisible(true);
                guardian.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(10.0);
                guardian.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(150);
                guardian.setHealth(guardian.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
                guardian.setRemoveWhenFarAway(false);
            });
        }
    }
}
