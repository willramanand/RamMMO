package com.gmail.willramanand.RamMMO.listeners;

import com.gmail.willramanand.RamMMO.RamMMO;
import com.gmail.willramanand.RamMMO.utils.IndicatorUtils;
import com.gmail.willramanand.RamSkills.events.CriticalStrikeEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DamageIndicatorListener implements Listener {

    private final RamMMO plugin;
    private final List<UUID> isCritMap;

    public DamageIndicatorListener(RamMMO plugin) {
        this.plugin = plugin;
        this.isCritMap = new ArrayList<>();
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void showDamageByNonEntity(EntityDamageEvent event) {
        if (event.isCancelled()) return;
        if (event.getEntity() instanceof ArmorStand) return;
        if (event.getEntity().getType() == EntityType.DROPPED_ITEM) return;

        if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK || event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION
                || event.getCause() == EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK || event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) return;

        if (checkVisible(event.getEntity())) {
            IndicatorUtils.spawnArmorStand(event.getEntity().getLocation(), false, event.getFinalDamage(), true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void showEntityDamage(EntityDamageByEntityEvent event) {
        if (event.isCancelled()) return;
        if (event.getEntity() instanceof ArmorStand) return;

        boolean isCrit = false;

        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            if (isCritMap.contains(player.getUniqueId())) {
                isCrit = true;
                isCritMap.remove(player.getUniqueId());
            }
        } else if (event.getDamager() instanceof Projectile projectile && projectile.getShooter() instanceof Player player) {
            if (isCritMap.contains(player.getUniqueId())) {
                isCrit = true;
                isCritMap.remove(player.getUniqueId());
            }
        }

        if (checkVisible(event.getEntity())) {
            IndicatorUtils.spawnArmorStand(event.getEntity().getLocation(), isCrit, event.getFinalDamage(), true);
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void showCritical(CriticalStrikeEvent event) {
        isCritMap.add(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void showHeal(EntityRegainHealthEvent event) {
        if (event.getEntity() instanceof ArmorStand) return;
        if (!(event.getEntity() instanceof LivingEntity)) return;

        if (checkVisible(event.getEntity())) {
            IndicatorUtils.spawnArmorStand(event.getEntity().getLocation(), false, event.getAmount(), false);
        }
    }

    private boolean checkVisible(Entity entity) {
        boolean isVisible = false;
        for (Player player : Bukkit.getOnlinePlayers()) {
            isVisible = player.hasLineOfSight(entity) || player == entity;
            if (isVisible) break;
        }
        return isVisible;
    }
}
