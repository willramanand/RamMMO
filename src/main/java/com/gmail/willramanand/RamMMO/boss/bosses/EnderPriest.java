package com.gmail.willramanand.RamMMO.boss.bosses;

import com.gmail.willramanand.RamMMO.boss.BaseBoss;
import com.gmail.willramanand.RamMMO.boss.Bosses;
import com.gmail.willramanand.RamMMO.item.Item;
import com.gmail.willramanand.RamMMO.item.ItemManager;
import com.gmail.willramanand.RamMMO.utils.ColorUtils;
import com.gmail.willramanand.RamMMO.utils.DataUtils;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.SkeletonHorse;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class EnderPriest extends BaseBoss {

    public EnderPriest() {
        super(Bosses.ENDER_PRIEST, Enderman.class);
    }

    @Override
    public void setAttributes(LivingEntity entity) {
        entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(600);
        entity.setHealth(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
        entity.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(100);
        entity.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(25);
        entity.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(1);
        entity.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).setBaseValue(10);
        entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(125);

        entity.setRemoveWhenFarAway(false);
    }

    @Override
    public void setTags(LivingEntity entity) {
        DataUtils.set(entity, "prevent_regen", PersistentDataType.INTEGER, 0);
    }

    @Override
    public void setEquipment(LivingEntity entity) {
    }

    @Override
    public void setExtras(LivingEntity entity) {
        for (int i = 0; i < 7; i++) {
            entity.getLocation().getWorld().spawn(entity.getLocation(), Enderman.class, enderman -> {
                enderman.setCustomName(ColorUtils.colorMessage("&bEnder Deacon"));
                enderman.setCustomNameVisible(true);
                enderman.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(10.0);
                enderman.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(150);
                enderman.setHealth(enderman.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
                enderman.setRemoveWhenFarAway(false);
            });
        }
    }
}
