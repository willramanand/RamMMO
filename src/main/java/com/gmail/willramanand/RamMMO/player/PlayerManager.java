package com.gmail.willramanand.RamMMO.player;

import com.gmail.willramanand.RamMMO.RamMMO;
import com.gmail.willramanand.RamMMO.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerManager {

    private final RamMMO plugin;
    private final ConcurrentHashMap<UUID, MMOPlayer> playerData;

    public PlayerManager(RamMMO plugin) {
        this.plugin = plugin;
        this.playerData = new ConcurrentHashMap<>();
        startAutoSave();
    }


    @Nullable
    public MMOPlayer getPlayerData(Player player) {
        return playerData.get(player.getUniqueId());
    }

    @Nullable
    public MMOPlayer getPlayerData(UUID id) {
        return this.playerData.get(id);
    }

    public void addPlayerData(@NotNull MMOPlayer mmoPlayer) {
        this.playerData.put(mmoPlayer.getUuid(), mmoPlayer);
    }

    public void removePlayerData(UUID id) {
        this.playerData.remove(id);
    }

    public boolean hasPlayerData(Player player) {
        return playerData.containsKey(player.getUniqueId());
    }

    public ConcurrentHashMap<UUID, MMOPlayer> getPlayerDataMap() {
        return playerData;
    }

    public void startAutoSave() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    MMOPlayer mmoPlayer = plugin.getPlayerManager().getPlayerData(player);
                    if (mmoPlayer != null && !mmoPlayer.isSaving()) {
                        plugin.getConfigManager().save(mmoPlayer.getPlayer(), false);
                    }
                }
            }
        }.runTaskTimer(plugin, 6000L, 6000L);
    }
}
