package com.gmail.willramanand.RamMMO.utils;

import com.gmail.willramanand.RamMMO.RamMMO;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class IndicatorUtils {

    private static final String DAMAGE_PREFIX = "&c";
    private static final String HEAL_PREFIX = "&a";
    private static final String CRIT_PREFIX = "&6";

    public static void spawnArmorStand(Location loc, boolean isCrit, double amount, boolean isDamage) {
        loc.setX(loc.getX() + new Random().nextDouble(-0.5, 0.5));
        loc.setZ(loc.getZ() + new Random().nextDouble(-0.5, 0.5));
        loc.setY(loc.getY() + new Random().nextDouble(0.5, 1.5));
        String damage = Formatter.decimalFormat(amount, 1);
        String name = "";

        if (isDamage) {
            name = ColorUtils.colorMessage(DAMAGE_PREFIX + damage);
            if (isCrit) name = ColorUtils.colorMessage(CRIT_PREFIX + damage);
        } else {
            name = ColorUtils.colorMessage(HEAL_PREFIX + damage);
        }

        String finalName = name;
        ArmorStand armorStand = loc.getWorld().spawn(loc, ArmorStand.class, stand -> {
            stand.setCustomName(finalName);
            setAttributes(stand);
        });
        new BukkitRunnable() {
            @Override
            public void run() {
                armorStand.remove();
            }
        }.runTaskLater(RamMMO.getInstance(), 30L);
    }

    private static void setAttributes(ArmorStand armorStand) {
        armorStand.setVisible(false);
        armorStand.setInvulnerable(true);
        armorStand.setSmall(true);
        armorStand.setCanPickupItems(false);
        armorStand.setMarker(true);
        armorStand.setCanTick(false);
        armorStand.setCustomNameVisible(true);
        armorStand.setDisabledSlots(EquipmentSlot.CHEST, EquipmentSlot.FEET, EquipmentSlot.HEAD, EquipmentSlot.LEGS,
                EquipmentSlot.OFF_HAND, EquipmentSlot.HAND);
    }
}
