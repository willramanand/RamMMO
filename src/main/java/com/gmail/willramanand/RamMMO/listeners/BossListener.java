package com.gmail.willramanand.RamMMO.listeners;

import com.gmail.willramanand.RamMMO.RamMMO;
import com.gmail.willramanand.RamMMO.boss.BossManager;
import com.gmail.willramanand.RamMMO.utils.BossUtils;
import com.gmail.willramanand.RamMMO.utils.ColorUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BossListener implements Listener {

    private RamMMO plugin;

    public BossListener(RamMMO plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBossSpawn(CreatureSpawnEvent event) {
        if (BossUtils.isBoss(event.getEntity()))
            event.getEntity().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 99999, 2, false));
    }

    @EventHandler
    public void updateBossBar(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof LivingEntity)) return;
        if (!(BossUtils.isBoss((LivingEntity) event.getEntity()))) return;
        if (BossManager.bossBar() == null) return;

        double maxHealth = ((LivingEntity) event.getEntity()).getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
        double currentHealth = ((LivingEntity) event.getEntity()).getHealth();
        double progress = currentHealth / maxHealth;
        BossManager.bossBar().setProgress(progress);
    }

    @EventHandler
    public void updateBossBarRegen(EntityRegainHealthEvent event) {
        if (!(event.getEntity() instanceof LivingEntity)) return;
        if (!(BossUtils.isBoss((LivingEntity) event.getEntity()))) return;
        if (BossManager.bossBar() == null) return;

        double maxHealth = ((LivingEntity) event.getEntity()).getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
        double currentHealth = ((LivingEntity) event.getEntity()).getHealth();
        double progress = currentHealth / maxHealth;
        BossManager.bossBar().setProgress(progress);
    }


    @EventHandler
    public void onFireBoss(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof LivingEntity)) return;
        if (!(BossUtils.isBoss((LivingEntity) event.getEntity()))) return;

        if (isFireEffect(event.getCause())) {
            event.getEntity().setFireTicks(0);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void removeDamagingEffect(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof LivingEntity)) return;
        if (!(BossUtils.isBoss((LivingEntity) event.getEntity()))) return;

        List<EntityDamageEvent.DamageCause> potionDamage = new ArrayList<>();
        potionDamage.add(EntityDamageEvent.DamageCause.POISON);
        potionDamage.add(EntityDamageEvent.DamageCause.WITHER);
        potionDamage.add(EntityDamageEvent.DamageCause.DRAGON_BREATH);
        if (potionDamage.contains(event.getCause())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBossDeath(EntityDeathEvent event) {
        if (!(BossUtils.isBoss(event.getEntity()))) return;
        LivingEntity boss = event.getEntity();
        Collection<Entity> nearbyPlayers = boss.getLocation().getNearbyEntities(20, 20, 20);
        Bukkit.broadcast(Component.text(ColorUtils.colorMessage(boss.getCustomName() + " &6has been slain!")));
        BossManager.setActive(false);

        if (BossManager.bossBar() != null) {
            BossManager.bossBar().removeAll();
            BossManager.bossBar(null);
        }
        for (Entity entity : nearbyPlayers) {
            if (entity instanceof Player) {
                Player player = (Player) entity;

                player.sendMessage(ColorUtils.colorMessage("&6Your heroics have been rewarded!"));
                player.addPotionEffect(new PotionEffect(PotionEffectType.HERO_OF_THE_VILLAGE, 36000, 4, false));
                if (RamMMO.getEconomy().hasAccount(player)) {
                    RamMMO.getEconomy().depositPlayer(player, 100000);
                }
            }
        }
        event.setDroppedExp(12000);
    }

    @EventHandler
    public void increaseProjectile(ProjectileLaunchEvent event) {
        if (!(event.getEntity() instanceof AbstractArrow)) return;
        if (!(event.getEntity().getShooter() instanceof LivingEntity)) return;
        AbstractArrow arrow = (AbstractArrow) event.getEntity();
        LivingEntity entity = (LivingEntity) event.getEntity().getShooter();

        if (!(BossUtils.isBoss(entity))) return;
        double damage = (arrow.getDamage() * 5) + 40;
        arrow.setDamage(damage);
    }

    private boolean isFireEffect(EntityDamageEvent.DamageCause cause) {
        List<EntityDamageEvent.DamageCause> fireDamage = new ArrayList<>();
        fireDamage.add(EntityDamageEvent.DamageCause.FIRE);
        fireDamage.add(EntityDamageEvent.DamageCause.FIRE_TICK);
        fireDamage.add(EntityDamageEvent.DamageCause.LAVA);
        fireDamage.add(EntityDamageEvent.DamageCause.HOT_FLOOR);

        return fireDamage.contains(cause);
    }
}
