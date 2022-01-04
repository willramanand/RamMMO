package com.gmail.willramanand.RamMMO.listeners;

import com.gmail.willramanand.RamMMO.RamMMO;
import com.gmail.willramanand.RamMMO.boss.Boss;
import com.gmail.willramanand.RamMMO.utils.ColorUtils;
import com.gmail.willramanand.RamMMO.utils.Formatter;
import io.papermc.paper.event.entity.EntityMoveEvent;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class HealthListener implements Listener {

    private final RamMMO plugin;
    private Boss mmoBoss = new Boss();

    public HealthListener(RamMMO plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {

        Player player = event.getPlayer();

        List<Entity> from = event.getFrom().getWorld().getNearbyEntities(event.getFrom(), 5, 5, 5).stream().filter(ent -> ent instanceof LivingEntity && !(ent instanceof Boss
                || ent instanceof Player || mmoBoss.isBoss((LivingEntity) ent) || ent instanceof ArmorStand)).toList();
        List<Entity> to = event.getTo().getWorld().getNearbyEntities(event.getTo(), 5, 5, 5).stream().filter(ent -> ent instanceof LivingEntity && !(ent instanceof Boss
                || ent instanceof Player || mmoBoss.isBoss((LivingEntity) ent) || ent instanceof ArmorStand)).toList();

        to.forEach(ent -> {
            LivingEntity entity = (LivingEntity) ent;

            String maxHealth = Formatter.decimalFormat(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue(), 1);
            String currHealth = Formatter.decimalFormat(entity.getHealth(), 1);
            String mobName = Formatter.nameFormat(entity.getType().name());
            String nickContain = entity.getPersistentDataContainer().get(new NamespacedKey(plugin, "Nickname"), PersistentDataType.STRING);


            if ((entity.getName().equalsIgnoreCase(mobName) || entity.getName().contains("❤")) && player.hasLineOfSight(entity)) {
                entity.setCustomName(ColorUtils.colorMessage(mobName + "&8 | &c&l❤ &f" + currHealth + "&8/&f" + maxHealth));
                entity.setCustomNameVisible(true);
            } else {
                if (entity.getName().contains("☠") && nickContain != null && player.hasLineOfSight(entity)) {
                    mobName = nickContain;
                    entity.setCustomName(ColorUtils.colorMessage(mobName + "&8 | &6&l☠ &f" + currHealth + "&8/&f" + maxHealth));
                    entity.setCustomNameVisible(true);

                } else {
                    if (entity.getName().contains("☠")) {
                        return;
                    }
                    entity.getPersistentDataContainer().set(new NamespacedKey(plugin, "Nickname"), PersistentDataType.STRING, entity.getName());

                    if (nickContain != null && player.hasLineOfSight(entity)) {
                        mobName = nickContain;
                        entity.setCustomName(ColorUtils.colorMessage(mobName + "&8 | &6&l☠ &f" + currHealth + "&8/&f" + maxHealth));
                        entity.setCustomNameVisible(true);
                    }
                }
            }
        });

        from.forEach(ent -> {
            if (to.contains(ent)) {
                return;
            }
            LivingEntity entity = (LivingEntity) ent;

            String mobName = Formatter.nameFormat(entity.getType().name());

            if (entity.getName().equalsIgnoreCase(mobName) || entity.getName().contains("❤")) {
                entity.setCustomName(null);
                entity.setCustomNameVisible(false);
            } else {
                String nickContain = entity.getPersistentDataContainer().get(new NamespacedKey(plugin, "Nickname"), PersistentDataType.STRING);
                if (entity.getName().contains("☠") && nickContain != null) {
                    mobName = nickContain;
                    entity.setCustomName(mobName);
                    entity.setCustomNameVisible(false);
                }
            }
        });
    }

    @EventHandler
    public void onMobDamage(EntityDamageEvent event) {
        List<Entity> playersNearby = event.getEntity().getLocation().getWorld().getNearbyEntities(event.getEntity().getLocation(), 5, 5, 5).stream().filter(player -> player instanceof Player).toList();

        playersNearby.forEach(player -> {
            if (!(player instanceof Player)) {
                return;
            }
            Player p = (Player) player;

            LivingEntity entity = (LivingEntity) event.getEntity();

            if (entity instanceof Boss || mmoBoss.isBoss((LivingEntity) event.getEntity())) {
                return;
            }

            String maxHealth = Formatter.decimalFormat(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue(), 1);
            String currHealth = Formatter.decimalFormat((entity.getHealth() - event.getFinalDamage() < 0) ? 0 : entity.getHealth() - event.getFinalDamage(), 1);
            String mobName = Formatter.nameFormat(entity.getType().name());
            String nickContain = entity.getPersistentDataContainer().get(new NamespacedKey(plugin, "Nickname"), PersistentDataType.STRING);

            if ((entity.getName().equalsIgnoreCase(mobName) || entity.getName().contains("❤")) && p.hasLineOfSight(entity)) {
                entity.setCustomName(ColorUtils.colorMessage(mobName + "&8 | &c&l❤ &f" + currHealth + "&8/&f" + maxHealth));
                entity.setCustomNameVisible(true);
            } else {
                if (entity.getName().contains("☠") && nickContain != null && p.hasLineOfSight(entity)) {
                    mobName = nickContain;
                    entity.setCustomName(ColorUtils.colorMessage(mobName + "&8 | &6&l☠ &f" + currHealth + "&8/&f" + maxHealth));
                    entity.setCustomNameVisible(true);
                } else {
                    if (entity.getCustomName() == null || entity.getCustomName().contains("☠")) {
                        return;
                    }
                    entity.getPersistentDataContainer().set(new NamespacedKey(plugin, "Nickname"), PersistentDataType.STRING, entity.getCustomName());

                    if (nickContain != null && p.hasLineOfSight(entity)) {
                        mobName = nickContain;
                        entity.setCustomName(ColorUtils.colorMessage(mobName + "&8 | &6&l☠ &f" + currHealth + "&8/&f" + maxHealth));
                        entity.setCustomNameVisible(true);
                    }
                }
            }
        });
    }

    // Make sure health display is off for entities outside of player range.
    @EventHandler
    public void onMobMove(EntityMoveEvent event) {
        if (!(event.getEntity() instanceof LivingEntity) || event.getEntity() instanceof Boss || mmoBoss.isBoss((LivingEntity) event.getEntity()))
            return;

        LivingEntity entity = event.getEntity();

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!(event.getTo().getWorld().getNearbyEntities(event.getTo(), 5, 5, 5).contains(p))) {
                String mobName = Formatter.nameFormat(entity.getType().name());

                if (entity.getName().equalsIgnoreCase(mobName) || entity.getName().contains("❤")) {
                    entity.setCustomName(null);
                    entity.setCustomNameVisible(false);
                } else {
                    String nickContain = entity.getPersistentDataContainer().get(new NamespacedKey(plugin, "Nickname"), PersistentDataType.STRING);
                    if (entity.getName().contains("☠") && nickContain != null) {
                        mobName = nickContain;
                        entity.setCustomName(mobName);
                        entity.setCustomNameVisible(false);
                    }
                }
            } else if (event.getTo().getWorld().getNearbyEntities(event.getTo(), 5, 5, 5).contains(p)) {
                String maxHealth = Formatter.decimalFormat(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue(), 1);
                String currHealth = Formatter.decimalFormat(entity.getHealth(), 1);
                String mobName = Formatter.nameFormat(entity.getType().name());
                String nickContain = entity.getPersistentDataContainer().get(new NamespacedKey(plugin, "Nickname"), PersistentDataType.STRING);

                if ((entity.getName().equalsIgnoreCase(mobName) || entity.getName().contains("❤"))) {
                    entity.setCustomName(ColorUtils.colorMessage(mobName + "&8 | &c&l❤ &f" + currHealth + "&8/&f" + maxHealth));
                    entity.setCustomNameVisible(true);
                } else {
                    if (entity.getName().contains("☠") && nickContain != null) {
                        mobName = nickContain;
                        entity.setCustomName(ColorUtils.colorMessage(mobName + "&8 | &6&l☠ &f" + currHealth + "&8/&f" + maxHealth));
                        entity.setCustomNameVisible(true);

                    } else {
                        if (entity.getName().contains("☠")) {
                            return;
                        }
                        entity.getPersistentDataContainer().set(new NamespacedKey(plugin, "Nickname"), PersistentDataType.STRING, entity.getCustomName());

                        if (nickContain != null) {
                            mobName = nickContain;
                            entity.setCustomName(ColorUtils.colorMessage(mobName + "&8 | &6&l☠ &f" + currHealth + "&8/&f" + maxHealth));
                            entity.setCustomNameVisible(true);
                        }
                    }
                }
            }
        }
    }
}
