package com.gmail.willramanand.RamMMO.boss;

import com.gmail.willramanand.RamMMO.RamMMO;
import com.gmail.willramanand.RamMMO.boss.bosses.HeadlessHorseman;
import com.gmail.willramanand.RamMMO.boss.bosses.TheGhost;
import com.gmail.willramanand.RamMMO.boss.bosses.TheMinotaur;
import com.gmail.willramanand.RamMMO.utils.ColorUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class BossManager {

    private static final Map<Bosses, BaseBoss> bosses = new HashMap<>();
    private static final Map<String, Bosses> bossRegistry = new HashMap<>();
    private static Bosses currentBossType;
    private static LivingEntity currentBoss;
    private static BossBar bossBar;
    private static boolean isActive = false;

    public static void registerBosses() {
        bosses.put(Bosses.HEADLESS_HORSEMAN, new HeadlessHorseman());
        bosses.put(Bosses.THE_MINOTAUR, new TheMinotaur());
        bosses.put(Bosses.THE_GHOST, new TheGhost());

        for (Bosses boss : Bosses.values()) {
            bossRegistry.put(boss.name().toLowerCase(), boss);
        }
    }

    public static void spawnBoss(Bosses boss, Location loc) {
        isActive = true;
        currentBossType = boss;
        currentBoss = (LivingEntity) bosses.get(boss).spawn(loc);
        Bukkit.broadcast(Component.text(ColorUtils.colorMessage(String.format("%s &6has been spotted in World:&d %s &6at coords:&d %d, %d, %d", currentBoss.getCustomName(), currentBoss.getWorld().getName(), currentBoss.getLocation().getBlockX(), currentBoss.getLocation().getBlockY(), currentBoss.getLocation().getBlockZ()))));
        bossBar = bosses.get(boss).getBossBar();
        updateBossBar();
    }

    public static void bossSpawnTicker() {
        new BukkitRunnable() {
            @Override
            public void run() {
                Bosses boss = null;
                while (boss == null || boss == currentBossType) {
                    boss = Bosses.randomBoss();
                }
                if (isActive()) return;
                isActive = true;
                currentBossType = boss;
                currentBoss = (LivingEntity) bosses.get(currentBossType).spawn(generateLocation(boss));
                Bukkit.broadcast(Component.text(ColorUtils.colorMessage(String.format("%s &6has been spotted in World:&d %s &6at coords:&d %d, %d, %d", currentBoss.getCustomName(), currentBoss.getWorld().
                        getName(), currentBoss.getLocation().getBlockX(), currentBoss.getLocation().getBlockY(), currentBoss.getLocation().getBlockZ()))));
                bossBar = bosses.get(boss).getBossBar();

                if (bossBar != null) updateBossBar();
            }
        }.runTaskTimer(RamMMO.getInstance(), 12000, 12000);
    }

    private static Location generateLocation(Bosses bosses) {
        Random random = new Random();
        Location location = null;
        while (location == null || location.getBlock().getType() == Material.WATER) {
            int x = random.nextInt(-2500, 2501);
            int z = random.nextInt(-2500, 2501);
            int y = Bukkit.getWorld("world").getHighestBlockYAt(x, z) + 3;
            if (bosses == Bosses.THE_GHOST) y += 7;
            location = new Location(Bukkit.getWorld("world"), x, y, z);
        }
        location.getChunk().load();
        return location;
    }

    public static Bosses getBoss(String displayName) { return bossRegistry.get(displayName); }

    public static LivingEntity getCurrentBoss() {
        return currentBoss;
    }

    public static Bosses getCurrentBossType() {
        return currentBossType;
    }

    public static boolean isActive() {
        return isActive;
    }

    public static void setActive(boolean setActive) {
        isActive = setActive;
    }

    ;

    public static void updateBossBar() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (bossBar == null) return;
                List<Entity> nearbyPlayers = currentBoss.getNearbyEntities(30, 30, 30).stream().filter(entity -> entity instanceof Player).toList();

                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (!(nearbyPlayers.contains(player)) && bossBar.getPlayers().contains(player)) {
                        bossBar.removePlayer(player);
                    } else if (nearbyPlayers.contains(player) && !(bossBar.getPlayers().contains(player))) {
                        bossBar.addPlayer(player);
                    }
                }
            }
        }.runTaskTimer(RamMMO.getInstance(), 0, 2);
    }

    public static void bossBar(BossBar value) {
        bossBar = value;
    }

    public static BossBar bossBar() {
        return bossBar;
    }

    public static Location getBossLocation() {
        if (currentBoss == null) return null;
        return currentBoss.getLocation();
    }
}
