package com.gmail.willramanand.RamMMO.listeners;

import com.gmail.willramanand.RamMMO.RamMMO;
import com.gmail.willramanand.RamMMO.boss.MMOBoss;
import com.gmail.willramanand.RamMMO.utils.ColorUtils;
import io.papermc.paper.event.entity.EntityMoveEvent;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import java.util.List;

public class HealthListener implements Listener {

    private final RamMMO plugin;

    public HealthListener(RamMMO plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {

        Player player = event.getPlayer();

        List<Entity> from = event.getFrom().getWorld().getNearbyEntities(event.getFrom(), 5, 5, 5).stream()
                .filter(ent -> ent instanceof LivingEntity && !(ent instanceof Boss || ent instanceof Player || ent instanceof MMOBoss || ent instanceof ArmorStand)).toList();
        List<Entity> to = event.getTo().getWorld().getNearbyEntities(event.getTo(), 5, 5, 5).stream()
                .filter(ent -> ent instanceof LivingEntity && !(ent instanceof Boss || ent instanceof Player || ent instanceof MMOBoss || ent instanceof ArmorStand)).toList();

        to.forEach(ent -> {
            LivingEntity entity = (LivingEntity) ent;

            String maxHealth = plugin.getFormatter().format(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
            String currHealth = plugin.getFormatter().format(entity.getHealth());
            String mobName = nameFormatter(entity.getType().name());
            List<MetadataValue> nickMeta = entity.getMetadata("Nickname");

            if ((entity.getName().equalsIgnoreCase(mobName) || entity.getName().contains("❤")) && event.getPlayer().hasLineOfSight(entity)) {
                entity.setCustomName(ColorUtils.colorMessage(mobName + "&8 | &c&l❤ &f" + currHealth + "&8/&f" + maxHealth));
                entity.setCustomNameVisible(true);
            } else {
                if (entity.getName().contains("☠") && (!nickMeta.isEmpty()) && event.getPlayer().hasLineOfSight(entity)) {
                    for (MetadataValue value : nickMeta) {
                        mobName = value.asString();
                    }
                    entity.setCustomName(ColorUtils.colorMessage(mobName + "&8 | &6&l☠ &f" + currHealth + "&8/&f" + maxHealth));
                    entity.setCustomNameVisible(true);

                } else {
                    if (entity.getName().contains("☠")) {
                        return;
                    }
                    entity.setMetadata("Nickname", new FixedMetadataValue(plugin, entity.getCustomName()));

                    if (!nickMeta.isEmpty() && event.getPlayer().hasLineOfSight(entity)) {
                        for (MetadataValue value : nickMeta) {
                            mobName = value.asString();
                        }
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

            String mobName = nameFormatter(entity.getType().name());

            if (entity.getName().equalsIgnoreCase(mobName) || entity.getName().contains("❤")) {
                entity.setCustomName(null);
                entity.setCustomNameVisible(false);
            } else {
                List<MetadataValue> nickMeta = entity.getMetadata("Nickname");
                if (entity.getName().contains("☠") && !nickMeta.isEmpty()) {
                    for (MetadataValue value : nickMeta) {
                        mobName = value.asString();
                    }
                    entity.setCustomName(mobName);
                    entity.setCustomNameVisible(false);
                }
            }
        });
    }

    @EventHandler
    public void onMobDamage(EntityDamageEvent event) {
        List<Entity> playersNearby = event.getEntity().getLocation().getWorld().getNearbyEntities(event.getEntity().getLocation(), 5, 5, 5).stream()
                .filter(player -> player instanceof Player).toList();

        playersNearby.forEach(player -> {
            if (!(player instanceof Player)) {
                return;
            }
            Player p = (Player) player;

            LivingEntity entity = (LivingEntity) event.getEntity();

            if (entity instanceof Boss || entity instanceof MMOBoss) {
                return;
            }

            String maxHealth = plugin.getFormatter().format(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
            String currHealth = plugin.getFormatter().format((entity.getHealth() - event.getFinalDamage() < 0) ? 0 : entity.getHealth() - event.getFinalDamage());
            String mobName = nameFormatter(entity.getType().name());
            List<MetadataValue> nickMeta = entity.getMetadata("Nickname");

            if ((entity.getName().equalsIgnoreCase(mobName) || entity.getName().contains("❤")) && p.hasLineOfSight(entity)) {
                entity.setCustomName(ColorUtils.colorMessage(mobName + "&8 | &c&l❤ &f" + currHealth + "&8/&f" + maxHealth));
                entity.setCustomNameVisible(true);
            } else {
                if (entity.getName().contains("☠") && (!nickMeta.isEmpty()) && p.hasLineOfSight(entity)) {
                    for (MetadataValue value : nickMeta) {
                        mobName = value.asString();
                    }
                    entity.setCustomName(ColorUtils.colorMessage(mobName + "&8 | &6&l☠ &f" + currHealth + "&8/&f" + maxHealth));
                    entity.setCustomNameVisible(true);
                } else {
                    if (entity.getCustomName() == null || entity.getCustomName().contains("☠")) {
                        return;
                    }
                    entity.setMetadata("Nickname", new FixedMetadataValue(plugin, entity.getCustomName()));

                    if (!nickMeta.isEmpty() && p.hasLineOfSight(entity)) {
                        for (MetadataValue value : nickMeta) {
                            mobName = value.asString();
                        }
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
        if (!(event.getEntity() instanceof LivingEntity) || event.getEntity() instanceof Boss || event.getEntity() instanceof MMOBoss) return;

        LivingEntity entity = (LivingEntity) event.getEntity();

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!(event.getTo().getWorld().getNearbyEntities(event.getTo(), 5, 5, 5).contains(p))) {
                String mobName = nameFormatter(entity.getType().name());

                if (entity.getName().equalsIgnoreCase(mobName) || entity.getName().contains("❤")) {
                    entity.setCustomName(null);
                    entity.setCustomNameVisible(false);
                } else {
                    List<MetadataValue> nickMeta = entity.getMetadata("Nickname");
                    if (entity.getName().contains("☠") && !nickMeta.isEmpty()) {
                        for (MetadataValue value : nickMeta) {
                            mobName = value.asString();
                        }
                        entity.setCustomName(mobName);
                        entity.setCustomNameVisible(false);
                    }
                }
            } else if (event.getTo().getWorld().getNearbyEntities(event.getTo(), 5, 5, 5).contains(p)) {
                String maxHealth = plugin.getFormatter().format(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
                String currHealth = plugin.getFormatter().format(entity.getHealth());
                String mobName = nameFormatter(entity.getType().name());
                List<MetadataValue> nickMeta = entity.getMetadata("Nickname");

                if ((entity.getName().equalsIgnoreCase(mobName) || entity.getName().contains("❤"))) {
                    entity.setCustomName(ColorUtils.colorMessage(mobName + "&8 | &c&l❤ &f" + currHealth + "&8/&f" + maxHealth));
                    entity.setCustomNameVisible(true);
                } else {
                    if (entity.getName().contains("☠") && (!nickMeta.isEmpty())) {
                        for (MetadataValue value : nickMeta) {
                            mobName = value.asString();
                        }
                        entity.setCustomName(ColorUtils.colorMessage(mobName + "&8 | &6&l☠ &f" + currHealth + "&8/&f" + maxHealth));
                        entity.setCustomNameVisible(true);

                    } else {
                        if (entity.getName().contains("☠")) {
                            return;
                        }
                        entity.setMetadata("Nickname", new FixedMetadataValue(plugin, entity.getCustomName()));

                        if (!nickMeta.isEmpty()) {
                            for (MetadataValue value : nickMeta) {
                                mobName = value.asString();
                            }
                            entity.setCustomName(ColorUtils.colorMessage(mobName + "&8 | &6&l☠ &f" + currHealth + "&8/&f" + maxHealth));
                            entity.setCustomNameVisible(true);
                        }
                    }
                }
            }
        }
    }

    private String nameFormatter(String name) {
        String formattedName = name;
        String upperFirst = formattedName.substring(0, 1).toUpperCase();
        formattedName = upperFirst + formattedName.substring(1).toLowerCase();

        return formattedName;
    }
}
