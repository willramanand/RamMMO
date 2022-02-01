package com.gmail.willramanand.RamMMO.boss.bosses;

import com.gmail.willramanand.RamMMO.boss.BaseBoss;
import com.gmail.willramanand.RamMMO.boss.Bosses;
import com.gmail.willramanand.RamMMO.utils.ColorUtils;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;

public class TheGhost extends BaseBoss {

    public TheGhost() {
        super(Bosses.THE_GHOST, Phantom.class);
    }

    @Override
    public void setAttributes(LivingEntity entity) {
        entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(250);
        entity.setHealth(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
        entity.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(20);
        entity.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(1);
        entity.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).setBaseValue(10);
        entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(100);
        Phantom phantom = (Phantom) entity;
        phantom.setShouldBurnInDay(false);
        phantom.setSize(5);
    }

    @Override
    public void setTags(LivingEntity entity) {
    }

    @Override
    public void setEquipment(LivingEntity entity) {
    }

    @Override
    public void setExtras(LivingEntity entity) {
        for (int i = 0; i < 10; i++) {
            entity.getLocation().getWorld().spawn(entity.getLocation(), Vex.class, vex -> {
                vex.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
                vex.setHealth(vex.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
                vex.setCustomName(ColorUtils.colorMessage("&bSickly Soul"));
                vex.getEquipment().setItemInMainHand(new ItemStack(Material.NETHERITE_SWORD));
                vex.setSummoner((Mob) entity);
            });
        }
    }
}
