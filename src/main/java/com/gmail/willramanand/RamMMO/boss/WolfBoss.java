package com.gmail.willramanand.RamMMO.boss;

import com.gmail.willramanand.RamMMO.RamMMO;
import com.gmail.willramanand.RamMMO.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;
import java.util.List;

public class WolfBoss extends MMOBoss {

    private RamMMO plugin;
    private Wolf bossWolf;
    private List<WolfMinion> minions;

    public WolfBoss(RamMMO plugin, Location loc) {
        this.plugin = plugin;
        this.bossWolf = loc.getBlock().getWorld().spawn(loc.getBlock().getLocation().add(0.5, 0.5, 0.5), Wolf.class);

        this.name();

        this.setAttributes();
        this.setEffects();

        this.spawnMinions();

        new BukkitRunnable() {
            public void run() {
                clearEffects();
                nearbyEntities();
                healthBar();
            }
        }.runTaskTimer(plugin, 0L, 2L);
    }

    public void name() {
        bossWolf.setCustomName(ColorUtils.colorMessage("&4&lFenrir, The Sigma Wolf"));
        bossWolf.setCustomNameVisible(true);
    }

    public void setAttributes() {

        bossWolf.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(1000);
        bossWolf.setHealth(1000.0);

        bossWolf.setAngry(true);

        bossWolf.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(30);
        bossWolf.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).setBaseValue(20);
        bossWolf.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(1);

        bossWolf.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.6);

        bossWolf.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(200);
    }

    public void healthBar() {

        BarFlag[] flag = {BarFlag.CREATE_FOG, BarFlag.DARKEN_SKY};
        BossBar bossBar = plugin.getServer().createBossBar(bossWolf.getCustomName(), BarColor.RED, BarStyle.SEGMENTED_6, flag);
        bossBar.setVisible(true);

        double progress = bossWolf.getHealth() / bossWolf.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
        bossBar.setProgress(progress);

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (bossWolf.hasLineOfSight(p)) {
                bossBar.addPlayer(p);
            } else if (bossBar.getPlayers().contains(p)) {
                bossBar.removePlayer(p);
            }
        }
    }

    public void setEffects() {
        bossWolf.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 99999, 1));
        bossWolf.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 99999, 2));
    }

    public void clearEffects() {
        if (bossWolf.hasPotionEffect(PotionEffectType.WITHER)) {
            bossWolf.removePotionEffect(PotionEffectType.WITHER);
        } else if (bossWolf.hasPotionEffect(PotionEffectType.POISON)) {
            bossWolf.removePotionEffect(PotionEffectType.POISON);
        } else if (bossWolf.isTamed()) {
            bossWolf.setTamed(false);
            // bossWolf.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(1000);
            // bossWolf.setHealth(1000.0);
        }
    }

    public void nearbyEntities() {
        Collection<Entity> nearbyEntities = bossWolf.getLocation().getWorld().getNearbyEntities(bossWolf.getLocation(), 10, 10, 10);

        for (Entity entity : nearbyEntities) {
            if (entity instanceof LivingEntity) {
                if (!(entity instanceof Wolf)) {
                    bossWolf.setTarget((LivingEntity) entity);
                }
            }
        }
    }

    public void howl() {
        new BukkitRunnable() {
            public void run() {
                List<Entity> playersNearby = bossWolf.getLocation().getWorld().getNearbyEntities(bossWolf.getLocation(), 15, 15, 15).stream().filter(player -> player instanceof Player).toList();

                for (Entity p : playersNearby) {
                    LivingEntity le = (LivingEntity) p;

                    le.hasLineOfSight(bossWolf);
                    le.sendMessage(ColorUtils.colorMessage(bossWolf.getName() + " &ebegins his howl!"));
                }
            }
        }.runTaskTimer(plugin, 600L, 600L);
    }

    private void spawnMinions() {
        for (int i = 0; i < 7; i++) {
            minions.add(new WolfMinion(plugin, bossWolf.getLocation(), i, bossWolf.getTarget()));
        }
    }
}
