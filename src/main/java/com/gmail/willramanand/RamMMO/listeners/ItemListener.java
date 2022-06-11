package com.gmail.willramanand.RamMMO.listeners;

import com.destroystokyo.paper.MaterialSetTag;
import com.destroystokyo.paper.MaterialTags;
import com.gmail.willramanand.RamMMO.RamMMO;
import com.gmail.willramanand.RamMMO.item.ItemManager;
import com.gmail.willramanand.RamMMO.utils.BuilderWandUtils;
import com.gmail.willramanand.RamMMO.utils.ColorUtils;
import com.gmail.willramanand.RamMMO.utils.DataUtils;
import com.gmail.willramanand.RamSkills.RamSkills;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.bukkit.Bukkit.getServer;

public class ItemListener implements Listener {

    private final RamMMO plugin;
    private final Set<Player> wandPaused;
    private final Set<Player> downpourPaused;

    public ItemListener(RamMMO plugin) {
        this.plugin = plugin;
        this.wandPaused = new HashSet<>();
        this.downpourPaused = new HashSet<>();
    }

    @EventHandler
    public void onFall(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        if (event.getCause() != EntityDamageEvent.DamageCause.FALL && event.getCause() != EntityDamageEvent.DamageCause.FLY_INTO_WALL) return;

        Player player = (Player) event.getEntity();
        for (ItemStack item : player.getInventory().getArmorContents()) {
            if (item != null && DataUtils.has(item.getItemMeta(), "fall_immunity")) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onFly(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (player.isSneaking() && player.isGliding()) {
            for (ItemStack item : player.getInventory().getArmorContents()) {
                if (item != null && DataUtils.has(item.getItemMeta(), "infinite_flight")) {
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
            if (item != null && DataUtils.has(item.getItemMeta(), "effect_immunity") && potionDamage.contains(event.getCause())) {
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
            if (item != null && DataUtils.has(item.getItemMeta(), "fire_immunity") && isFireEffect(event.getCause())) {
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
            if (item == null || !(DataUtils.has(item.getItemMeta(), "reduced_dmg_set"))) {
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

        if (player.getInventory().getItemInMainHand() != null && DataUtils.has(player.getInventory().getItemInMainHand().getItemMeta(), "silk_touch")) {
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
    public void cannotPlace(BlockPlaceEvent event) {
        if (!(DataUtils.has(event.getItemInHand().getItemMeta(), "cannot_place"))) return;
        event.setCancelled(true);
        event.getPlayer().sendMessage(ColorUtils.colorMessage("&4This item cannot be placed!"));
    }

    @EventHandler
    public void apolloBow(ProjectileLaunchEvent event) {
        if (!(event.getEntity() instanceof AbstractArrow)) return;
        if (!(event.getEntity().getShooter() instanceof Player)) return;
        AbstractArrow arrow = (AbstractArrow) event.getEntity();
        Player player = (Player) event.getEntity().getShooter();

        if (player.getInventory().getItemInMainHand() != null && DataUtils.has(player.getInventory().getItemInMainHand().getItemMeta(), "apollo_bow")) {
            arrow.setDamage(arrow.getDamage() * 2);
        }
    }

    @EventHandler
    public void preventBurning(EntityDamageEvent event) {
        if (event.getEntity().getType() != EntityType.DROPPED_ITEM) return;

        Item item = (Item) event.getEntity();
        ItemStack itemStack = item.getItemStack();

        if (itemStack.getItemMeta() != null && DataUtils.has(itemStack.getItemMeta(), "cannot_burn")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void instaBreak(BlockDamageEvent event) {
        if (event.getPlayer().getInventory().getItemInMainHand() == null || event.getPlayer().getInventory().getItemInMainHand().getItemMeta() == null || !(DataUtils.has(event.getPlayer().getInventory().getItemInMainHand().getItemMeta(), "insta_break")))
            return;
        if (!(Tag.MINEABLE_PICKAXE.isTagged(event.getBlock().getType()))) return;
        event.setInstaBreak(true);
    }

    @EventHandler
    public void builderWand(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        if (event.getHand() != EquipmentSlot.HAND) {
            return;
        }

        Player player = event.getPlayer();

        if (player.getInventory().getItemInMainHand() == null) return;

        if (wandPaused.contains(player)) {
            RamSkills.getInstance().getActionBar().sendAbilityActionBar(player, "&4Wand on Cooldown!");
            return;
        }

        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if (itemStack.getItemMeta() == null || !(DataUtils.has(itemStack.getItemMeta(), "builderwand"))) return;

        Block block = event.getClickedBlock();
        if (block == null || block.getType() == Material.AIR) {
            return;
        }

        BlockFace face = BuilderWandUtils.getBlockFace(player);
        if (face == null) {
            face = event.getBlockFace();
        }
        Material blockType = block.getType();
        BlockData blockData = block.getBlockData();

        ItemStack cost = new ItemStack(blockType, 1);
        for (Location location : BuilderWandUtils.getValidLocations(block.getLocation().add(face.getModX(), face.getModY(), face.getModZ()), face, blockType)) {
            if (player.getLocation().getBlock().getLocation().equals(location) || player.getLocation().add(0, 1, 0).getBlock().getLocation().equals(location)) {
                continue;
            }
            if (!BuilderWandUtils.getReplaceables().contains(location.getBlock().getType())) {
                continue;
            }

            boolean hasItem;
            if (player.getGameMode() == GameMode.CREATIVE || player.getInventory().containsAtLeast(cost, 1)) {
                hasItem = player.getGameMode() != GameMode.CREATIVE;
            } else {
                continue;
            }
            BlockState state = location.getBlock().getState();
            state.setType(blockType);
            state.setBlockData(blockData);
            BlockPlaceEvent buildEvent = new BlockPlaceEvent(location.getBlock(), state, location.getBlock().getRelative(face.getOppositeFace()), cost, player, true, EquipmentSlot.HAND);
            Bukkit.getPluginManager().callEvent(buildEvent);

            if (buildEvent.isCancelled()) {
                continue;
            }

            if (hasItem) {
                player.getInventory().removeItem(cost);
            }
            state.update(true);
        }
        setWandPaused(player, 10);
    }

    @EventHandler
    public void strikeLightning(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) return;
        Player player = (Player) event.getDamager();
        if (player.getInventory().getItemInMainHand() == null) return;
        if (player.getInventory().getItemInMainHand().getItemMeta() == null
                || !(DataUtils.has(player.getInventory().getItemInMainHand().getItemMeta(), "strike_lightning"))) return;

        player.getWorld().strikeLightning(event.getEntity().getLocation());
    }

    @EventHandler
    public void preventLightningDamage(EntityDamageEvent event) {
        if (event.getCause() != EntityDamageEvent.DamageCause.LIGHTNING) return;
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        if (player.getInventory().getItemInMainHand() == null) return;
        if (player.getInventory().getItemInMainHand().getItemMeta() == null
                || !(DataUtils.has(player.getInventory().getItemInMainHand().getItemMeta(), "strike_lightning"))) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void downPour(PlayerInteractEvent event) {
        if (!(event.getAction().isLeftClick())) return;
        if (event.getPlayer().getInventory().getItemInMainHand() == null) return;
        if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta() == null
                || !(DataUtils.has(event.getPlayer().getInventory().getItemInMainHand().getItemMeta(), "downpour"))) return;

        Player player = event.getPlayer();


        if (downpourPaused.contains(player)) {
            RamSkills.getInstance().getActionBar().sendAbilityActionBar(player, "&4On Cooldown!");
            return;
        }

        if (player.getWorld().isClearWeather()) {
            player.getWorld().setStorm(true);
            player.getWorld().setThundering(true);
        } else {
            player.getWorld().setStorm(false);
            player.getWorld().setThundering(false);
        }
        setDownpourPaused(player, 600);
    }

    @EventHandler
    public void spawnHorse(PlayerInteractEvent e) {
        if (!(e.hasItem()) && e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (e.getItem() == null) return;
        if (!(DataUtils.has(e.getItem().getItemMeta(), "apocalypseegg"))) return;
        e.getPlayer().getLocation().getWorld().spawn(e.getPlayer().getLocation(), SkeletonHorse.class, horse -> {
            horse.setTamed(true);
            horse.setCustomName(ColorUtils.colorMessage("&4Horse of the Apocalypse"));
            horse.setCustomNameVisible(true);
            horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.4);
            horse.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(10.0);
            horse.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(200);
            horse.setHealth(horse.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
            horse.setRemoveWhenFarAway(false);
        });
        e.getPlayer().getInventory().remove(e.getItem());
        e.setCancelled(true);
    }

    @EventHandler
    public void activateGem (PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK && e.getAction() != Action.RIGHT_CLICK_AIR) return;
        if (e.getItem() == null) return;
        if (DataUtils.has(e.getItem().getItemMeta(), "endergem")) {
            e.getPlayer().getInventory().remove(e.getItem());
            e.getPlayer().getInventory().setItemInMainHand(new ItemStack(ItemManager.getItem(com.gmail.willramanand.RamMMO.item.Item.ACTIVE_ENDER_GEM)));
            e.setCancelled(true);
        } else if (DataUtils.has(e.getItem().getItemMeta(), "activeendergem")) {
            e.getPlayer().getInventory().remove(e.getItem());
            e.getPlayer().getInventory().setItemInMainHand(new ItemStack(ItemManager.getItem(com.gmail.willramanand.RamMMO.item.Item.ENDER_GEM)));
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void mythrilDrillSpeed(BlockDamageEvent e) {
        if (!(DataUtils.has(e.getPlayer().getInventory().getItemInMainHand().getItemMeta(), "mythrildrill"))) return;
        if (!(Tag.MINEABLE_PICKAXE.isTagged(e.getBlock().getType()))) return;
        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 10, 150, false, false, false));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void mythrilDrill(BlockBreakEvent e) {
        if (!(DataUtils.has(e.getPlayer().getInventory().getItemInMainHand().getItemMeta(), "mythrildrill"))) return;
        if (!(Tag.MINEABLE_PICKAXE.isTagged(e.getBlock().getType()))) return;
        e.setDropItems(false);

        for (ItemStack item : e.getBlock().getDrops()) {
            if (MaterialTags.ORES.isTagged(e.getBlock())) {
                item.setAmount(5);
            } else {
                item.setAmount(1);
            }
        }
    }

    public void itemMagnetSearch() {
        new BukkitRunnable() {
            @Override
            public void run() {
                double magnetDistanceSq = 100;

                for (World world : getServer().getWorlds()) {
                    List<Player> players = world.getPlayers();
                    // No players in this world, nobody to pick items up
                    if (players.isEmpty()) {
                        continue;
                    }

                    for (Item item : world.getEntitiesByClass(Item.class)) {
                        Location itemLocation = item.getLocation();

                        // Make sure we don't pick up items we just threw on the ground
                        if (item.getPickupDelay() > item.getTicksLived()) {
                            continue;
                        }

                        // Find the closest player to the item, within magnet distance
                        Player closestPlayer = null;
                        double closestPlayerDistance = Double.MAX_VALUE;
                        for (Player player : players) {
                            boolean hasActive = player.getInventory().contains(ItemManager.getItem(com.gmail.willramanand.RamMMO.item.Item.ACTIVE_ENDER_GEM));
                            if (!(hasActive)) continue;

                            double playerDistance = player.getLocation().distanceSquared(itemLocation);
                            if (playerDistance <= magnetDistanceSq && playerDistance < closestPlayerDistance) {
                                closestPlayerDistance = playerDistance;
                                closestPlayer = player;
                            }
                        }

                        if (closestPlayer != null) {
                            item.setVelocity(closestPlayer.getLocation().toVector().subtract(itemLocation.toVector()).normalize());
                        }
                    }
                }
            }
        }.runTaskTimer(RamMMO.getInstance(), 0, 2);
    }

    public void setWandPaused(Player player, int ticks) {
        wandPaused.add(player);
        new BukkitRunnable() {
            @Override
            public void run() {
                wandPaused.remove(player);
            }
        }.runTaskLater(plugin, ticks);
    }

    public void setDownpourPaused(Player player, int ticks) {
        downpourPaused.add(player);
        new BukkitRunnable() {
            @Override
            public void run() {
                downpourPaused.remove(player);
            }
        }.runTaskLater(plugin, ticks);
    }

        private boolean isFireEffect (EntityDamageEvent.DamageCause cause){
            List<EntityDamageEvent.DamageCause> fireDamage = new ArrayList<>();
            fireDamage.add(EntityDamageEvent.DamageCause.FIRE);
            fireDamage.add(EntityDamageEvent.DamageCause.FIRE_TICK);
            fireDamage.add(EntityDamageEvent.DamageCause.LAVA);
            fireDamage.add(EntityDamageEvent.DamageCause.HOT_FLOOR);

            return fireDamage.contains(cause);
        }

    }
