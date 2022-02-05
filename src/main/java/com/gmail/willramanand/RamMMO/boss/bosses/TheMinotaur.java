package com.gmail.willramanand.RamMMO.boss.bosses;

import com.gmail.willramanand.RamMMO.boss.BaseBoss;
import com.gmail.willramanand.RamMMO.boss.Bosses;
import com.gmail.willramanand.RamMMO.utils.ColorUtils;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Evoker;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Ravager;

public class TheMinotaur extends BaseBoss {

    public TheMinotaur() {
        super(Bosses.THE_MINOTAUR, Ravager.class);
    }

    @Override
    public void setAttributes(LivingEntity entity) {
        entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(750);
        entity.setHealth(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
        entity.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(100);
        entity.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(30);
        entity.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(1);
        entity.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).setBaseValue(15);
        entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(75);

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
        for (int i = 0; i < 5; i++) {
            entity.getLocation().getWorld().spawn(entity.getLocation(), Evoker.class, evoker -> {
                evoker.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(80);
                evoker.setHealth(evoker.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
                evoker.setCustomName(ColorUtils.colorMessage("&3Minotaur's Servant"));
                evoker.setRemoveWhenFarAway(false);
            });
        }
    }
}
