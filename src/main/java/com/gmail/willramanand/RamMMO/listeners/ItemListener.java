package com.gmail.willramanand.RamMMO.listeners;

import com.gmail.willramanand.RamMMO.RamMMO;
import com.gmail.willramanand.RamMMO.items.Item;
import com.gmail.willramanand.RamMMO.items.ItemManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

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
        Player player = (Player) event.getEntity();

        if (player.getInventory().getBoots() == null || player.getInventory().getBoots().lore() == null) {
            return;
        }

        if (player.getInventory().getBoots().lore().equals(ItemManager.getItem(Item.NETHERFIRE_BOOTS).lore()) && (event.getCause() == EntityDamageEvent.DamageCause.FALL)) {
            event.setDamage(0);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onFly(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (player.getInventory().getChestplate() == null || player.getInventory().getChestplate().lore() == null) {
            return;
        }
        if (player.isSneaking() && player.isGliding() && player.getInventory().getChestplate().lore().contains(ItemManager.getItem(Item.EMPOWERED_ELYTRA).lore().get(0))) {
            event.getPlayer().boostElytra(new ItemStack(Material.FIREWORK_ROCKET));
        }
    }

    @EventHandler
    public void removeDamagingEffect(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        Player player = (Player) event.getEntity();

        if (player.getInventory().getHelmet() == null || player.getInventory().getHelmet().lore() == null) {
            return;
        }

        List<EntityDamageEvent.DamageCause> potionDamage = new ArrayList<>();
        potionDamage.add(EntityDamageEvent.DamageCause.POISON);
        potionDamage.add(EntityDamageEvent.DamageCause.WITHER);
        potionDamage.add(EntityDamageEvent.DamageCause.DRAGON_BREATH);
        if (player.getInventory().getHelmet().lore().equals(ItemManager.getItem(Item.NETHERFIRE_HELM).lore()) && potionDamage.contains(event.getCause())) {
            event.setDamage(0);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onFire(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        Player player = (Player) event.getEntity();

        if (player.getInventory().getChestplate() == null || player.getInventory().getChestplate().lore() == null) {
            return;
        }

        if (player.getInventory().getChestplate().lore().contains(ItemManager.getItem(Item.NETHERFIRE_CHEST).lore().get(0)) && isFireEffect(event.getCause())) {
            event.setDamage(0);
            player.setFireTicks(0);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void setBonusDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();

        for (ItemStack item : player.getInventory().getArmorContents()) {
            if (item == null || item.lore() == null || !(item.lore().contains(ItemManager.getItem(Item.NETHERFIRE_CHEST).lore().get(1)))) {
                return;
            }
        }
        double damage = event.getFinalDamage();
        event.setDamage(damage * 0.8);

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
