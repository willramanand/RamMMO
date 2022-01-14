package com.gmail.willramanand.RamMMO.listeners;

import com.gmail.willramanand.RamMMO.RamMMO;
import com.gmail.willramanand.RamMMO.utils.IndicatorUtils;
import com.gmail.willramanand.RamSkills.events.CriticalStrikeEvent;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class DamageIndicatorListener implements Listener {

    private final RamMMO plugin;
    private boolean isCrit = false;

    public DamageIndicatorListener(RamMMO plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void showDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof ArmorStand) return;
        IndicatorUtils.spawnArmorStand(event.getEntity().getLocation(), isCrit ,event.getFinalDamage(), true);
        isCrit = false;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void showCritical(CriticalStrikeEvent event) {
        isCrit = true;
    }

    @EventHandler
    public void showHeal(EntityRegainHealthEvent event) {
        if (event.getEntity() instanceof ArmorStand) return;
        if (!(event.getEntity() instanceof LivingEntity)) return;

        IndicatorUtils.spawnArmorStand(event.getEntity().getLocation(), false, event.getAmount(), false);
    }
}
