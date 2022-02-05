package com.gmail.willramanand.RamMMO.listeners;

import com.gmail.willramanand.RamMMO.RamMMO;
import com.gmail.willramanand.RamMMO.boss.BossManager;
import com.gmail.willramanand.RamMMO.boss.Bosses;
import com.gmail.willramanand.RamMMO.item.Item;
import com.gmail.willramanand.RamMMO.item.ItemManager;
import com.gmail.willramanand.RamMMO.utils.BossUtils;
import com.gmail.willramanand.RamMMO.utils.ColorUtils;
import com.gmail.willramanand.RamMMO.utils.DataUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class BossListener implements Listener {

    private RamMMO plugin;

    public BossListener(RamMMO plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBossSpawn(CreatureSpawnEvent event) {
        if (BossUtils.isBoss(event.getEntity()) && !(DataUtils.has(event.getEntity(), "prevent_regen"))) {
            event.getEntity().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 99999, 2, false));
        }
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
                Bosses bosses = Bosses.matchBoss(DataUtils.get(boss, "boss_type", PersistentDataType.STRING));

                // Always rewarded
                player.sendMessage(ColorUtils.colorMessage("&6Your heroics have been rewarded!"));
                player.addPotionEffect(new PotionEffect(PotionEffectType.HERO_OF_THE_VILLAGE, 36000, 4, false));
                player.getInventory().addItem(new ItemStack(ItemManager.getItem(Item.ENCHANTED_NETHERITE_BLOCK)));
                if (RamMMO.getEconomy().hasAccount(player)) {
                    RamMMO.getEconomy().depositPlayer(player, 100000);
                }

                // RNG Rewards
                int rng = new Random().nextInt(101);
                int upperBound = 80 - (2 * plugin.getDifficultyUtils().getDifficultyModifier());
                if (rng >= upperBound) {
                    if (bosses == Bosses.HEADLESS_HORSEMAN) {
                        player.getInventory().addItem(new ItemStack(ItemManager.getItem(Item.APOCALYPSE_HORSE_EGG)));
                    } else if (bosses == Bosses.THE_MINOTAUR) {
                        player.getInventory().addItem(new ItemStack(ItemManager.getItem(Item.MINOTAURS_HIDE)));
                    } else if (bosses == Bosses.THE_GHOST) {
                        player.getInventory().addItem(new ItemStack(ItemManager.getItem(Item.PHANTOM_PLATE)));
                    } else if (bosses == Bosses.ENDER_PRIEST) {
                        player.getInventory().addItem(new ItemStack(ItemManager.getItem(Item.ENDER_GEM)));
                    } else {
                        player.getInventory().addItem(new ItemStack(ItemManager.getItem(Item.ENCHANTED_DIAMOND_BLOCK)));
                    }
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
