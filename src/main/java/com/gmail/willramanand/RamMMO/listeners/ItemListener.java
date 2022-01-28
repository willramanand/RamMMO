package com.gmail.willramanand.RamMMO.listeners;

import com.gmail.willramanand.RamMMO.RamMMO;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemListener implements Listener {

    private final RamMMO plugin;

    public ItemListener(RamMMO plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onFall(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        if (event.getCause() != EntityDamageEvent.DamageCause.FALL) return;

        Player player = (Player) event.getEntity();
        for (ItemStack item : player.getInventory().getArmorContents()) {
            if (item != null && item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "fall_immunity"))) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onFly(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (player.isSneaking() && player.isGliding()) {
            for (ItemStack item : player.getInventory().getArmorContents()) {
                if (item != null && item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "infinite_flight"))) {
                    event.getPlayer().boostElytra(new ItemStack(Material.FIREWORK_ROCKET));
                }
            }
        }
    }

    @EventHandler
    public void removeDamagingEffect(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        Player player = (Player) event.getEntity();

        List<EntityDamageEvent.DamageCause> potionDamage = new ArrayList<>();
        potionDamage.add(EntityDamageEvent.DamageCause.POISON);
        potionDamage.add(EntityDamageEvent.DamageCause.WITHER);
        potionDamage.add(EntityDamageEvent.DamageCause.DRAGON_BREATH);
        for (ItemStack item : player.getInventory().getArmorContents()) {
            if (item != null && item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "effect_immunity")) && potionDamage.contains(event.getCause())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onFire(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        Player player = (Player) event.getEntity();

        for (ItemStack item : player.getInventory().getArmorContents()) {
            if (item != null && item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "fire_immunity")) && isFireEffect(event.getCause())) {
                player.setFireTicks(0);
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void setBonusDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();

        for (ItemStack item : player.getInventory().getArmorContents()) {
            if (item == null || !(item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "reduced_dmg_set")))) {
                return;
            }
        }
        double damage = event.getFinalDamage();
        event.setDamage(damage * 0.95);
    }

    @EventHandler
    public void silkToucherBreak(BlockBreakEvent event) {
        if (event.getBlock().getType() != Material.SPAWNER) return;
        Player player = event.getPlayer();

        CreatureSpawner originalSpawner = (CreatureSpawner) event.getBlock().getState();
        ItemStack newSpawner = new ItemStack(Material.SPAWNER);
        ItemMeta meta = newSpawner.getItemMeta();
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text(String.valueOf(originalSpawner.getSpawnedType())));
        meta.lore(lore);
        newSpawner.setItemMeta(meta);

        if (player.getInventory().getItemInMainHand() != null && player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "silk_touch"))) {
            event.getBlock().getLocation().getWorld().dropItemNaturally(event.getBlock().getLocation(), newSpawner);
        }
    }

    @EventHandler
    public void placeSpawner(BlockPlaceEvent event) {
        if (event.getBlockPlaced().getType() != Material.SPAWNER) return;
        if (event.getItemInHand().getItemMeta().lore() == null) return;

        CreatureSpawner creatureSpawner = (CreatureSpawner) event.getBlockPlaced().getState();
        for (EntityType entityType : EntityType.values()) {
            if (event.getItemInHand().getItemMeta().lore().contains(Component.text(entityType.name()))) {
                creatureSpawner.setSpawnedType(entityType);
                creatureSpawner.update();
            }
        }
    }

    @EventHandler
    public void apolloBow(ProjectileLaunchEvent event) {
        if (!(event.getEntity() instanceof AbstractArrow)) return;
        if (!(event.getEntity().getShooter() instanceof Player)) return;
        AbstractArrow arrow = (AbstractArrow) event.getEntity();
        Player player = (Player) event.getEntity().getShooter();

        if (player.getInventory().getItemInMainHand() != null && player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "apollo_bow"))) {
            arrow.setDamage(arrow.getDamage() * 2);
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
