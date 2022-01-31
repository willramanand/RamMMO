package com.gmail.willramanand.RamMMO.listeners;

import com.gmail.willramanand.RamMMO.RamMMO;
import com.gmail.willramanand.RamMMO.utils.BossUtils;
import com.gmail.willramanand.RamMMO.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
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

        LivingEntity boss = event.getEntity();

        if (!(BossUtils.isBoss(boss))) return;

        for (LivingEntity ent : boss.getLocation().getWorld().getLivingEntities()) {
            if (BossUtils.isBoss(ent) && ent != boss) {
                ent.remove();
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.sendMessage(ColorUtils.colorMessage(String.format("%s &6has gone into hiding!", ent.getCustomName())));
                }
            }
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(ColorUtils.colorMessage(String.format("%s &6has been spotted in World:&d %s &6at coords:&d %b, %b, %b"
                    , boss.getCustomName(), boss.getWorld().getName(), boss.getLocation().getBlockX(), boss.getLocation().getBlockY(), boss.getLocation().getBlockZ())));
        }

        boss.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 99999, 3, false));
    }

    @EventHandler
    public void onFireBoss(EntityDamageEvent event) {
        if (!(BossUtils.isBoss((LivingEntity) event.getEntity()))) return;

        if (isFireEffect(event.getCause())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void removeDamagingEffect(EntityDamageEvent event) {
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

        for (Entity entity : nearbyPlayers) {
            Player player = (Player) entity;

            player.addPotionEffect(new PotionEffect(PotionEffectType.HERO_OF_THE_VILLAGE, 36000, 4, false));
            if (RamMMO.getEconomy().hasAccount(player)) {
                RamMMO.getEconomy().depositPlayer(player, 500);
            }
        }
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
