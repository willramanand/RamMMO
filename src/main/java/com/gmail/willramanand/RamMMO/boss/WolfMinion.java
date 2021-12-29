package com.gmail.willramanand.RamMMO.boss;

import com.gmail.willramanand.RamMMO.RamMMO;
import com.gmail.willramanand.RamMMO.utils.ColorUtils;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Wolf;

public class WolfMinion {
    private final RamMMO plugin;
    private final Wolf minWolf;

    public WolfMinion(RamMMO plugin, Location loc, int counter, LivingEntity livingEntity) {
        this.plugin = plugin;
        this.minWolf = loc.getBlock().getWorld().spawn(loc.getBlock().getLocation().add(counter, 0.5, counter), Wolf.class);

        this.minWolf.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(50);
        this.minWolf.setHealth(50.0);

        this.minWolf.setAngry(true);

        this.minWolf.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(4);
        this.minWolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(25);
        minWolf.setTarget(livingEntity);

        this.minWolf.setCustomName(ColorUtils.colorMessage("&bBeta Wolf"));
        this.minWolf.setCustomNameVisible(true);
    }
}
