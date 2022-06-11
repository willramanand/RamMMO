package com.gmail.willramanand.RamMMO.boss;

import com.gmail.willramanand.RamMMO.RamMMO;
import com.gmail.willramanand.RamMMO.boss.bosses.*;
import com.gmail.willramanand.RamMMO.utils.ColorUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BossManager {

    private final RamMMO plugin;

    public BossManager(RamMMO plugin) {
        this.plugin = plugin;
    }

    private final Map<Bosses, BaseBoss> bosses = new HashMap<>();
    private final Map<String, Bosses> bossRegistry = new HashMap<>();
    private Bosses currentBossType;
    private LivingEntity currentBoss;
    private BossBar bossBar;
    private boolean isActive = false;

    public void registerBosses() {
        bosses.put(Bosses.HEADLESS_HORSEMAN, new HeadlessHorseman());
        bosses.put(Bosses.THE_MINOTAUR, new TheMinotaur());
        bosses.put(Bosses.THE_GHOST, new TheGhost());
        bosses.put(Bosses.ENDER_PRIEST, new EnderPriest());
        bosses.put(Bosses.THE_COLOSSUS, new TheColossus());
        bosses.put(Bosses.THE_LEVIATHAN, new TheLeviathan());

        for (Bosses boss : Bosses.values()) {
            bossRegistry.put(boss.name().toLowerCase(), boss);
        }
    }

    public void spawnBoss(Bosses boss, Location loc) {
        isActive = true;
        currentBossType = boss;
        currentBoss = (LivingEntity) bosses.get(boss).spawn(loc);
        Bukkit.broadcast(Component.text(ColorUtils.colorMessage(String.format("%s &6has been spotted in World:&d %s &6at coords:&d %d, %d, %d", currentBoss.getCustomName(), currentBoss.getWorld().getName(), currentBoss.getLocation().getBlockX(), currentBoss.getLocation().getBlockY(), currentBoss.getLocation().getBlockZ()))));
        bossBar = bosses.get(boss).getBossBar();
        updateBossBar();
    }

    public void bossSpawnTicker() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (isActive()) return;
                Bosses boss = null;
                while (boss == null || boss == currentBossType) {
                    boss = Bosses.randomBoss();
                }
                spawnBoss(boss, generateLocation(boss));
            }
        }.runTaskTimer(RamMMO.getInstance(), 12000, 12000);
    }

    private Location generateLocation(Bosses bosses) {
        Random random = new Random();
        Location location = null;
        if (bosses != Bosses.THE_LEVIATHAN) {
            while (location == null || location.getBlock().getType() == Material.WATER || location.getBlock().getBiome() == Biome.OCEAN) {
                int x = random.nextInt(-2500, 2501);
                int z = random.nextInt(-2500, 2501);
                int y = Bukkit.getWorld("world").getHighestBlockYAt(x, z) + 3;
                if (bosses == Bosses.THE_GHOST) y += 7;
                location = new Location(Bukkit.getWorld("world"), x, y, z);
            }
        } else {
            while (location == null || location.getBlock().getBiome() != Biome.DEEP_OCEAN) {
                int x = random.nextInt(-2500, 2501);
                int z = random.nextInt(-2500, 2501);
                int y = Bukkit.getWorld("world").getHighestBlockYAt(x, z) + 3;
                location = new Location(Bukkit.getWorld("world"), x, y, z);
            }
        }
        location.getChunk().load();
        return location;
    }

    public Bosses getBoss(String displayName) {
        return bossRegistry.get(displayName);
    }

    public LivingEntity getCurrentBoss() {
        return currentBoss;
    }

    public Bosses getCurrentBossType() {
        return currentBossType;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean setActive) {
        isActive = setActive;
    }

    public void updateBossBar() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (bossBar == null) return;
                bossBar.setVisible(true);
                for (Player player : Bukkit.getOnlinePlayers()) {
                    bossBar.removePlayer(player);
                }
                currentBoss.getNearbyEntities(30, 30, 30).forEach(entity -> {
                    if (entity instanceof Player) {
                        bossBar.addPlayer((Player) entity);
                    }
                });
            }
        }.runTaskTimer(RamMMO.getInstance(), 0, 2);
    }

    public void bossBar(BossBar value) {
        bossBar = value;
    }

    public BossBar bossBar() {
        return bossBar;
    }

    public void clearBossInfo() {
        setActive(false);
        currentBoss = null;
    }
}
