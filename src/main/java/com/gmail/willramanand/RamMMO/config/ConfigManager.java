package com.gmail.willramanand.RamMMO.config;

import com.gmail.willramanand.RamMMO.RamMMO;
import com.gmail.willramanand.RamMMO.enums.Passive;
import com.gmail.willramanand.RamMMO.enums.Passives;
import com.gmail.willramanand.RamMMO.player.MMOPlayer;
import com.gmail.willramanand.RamMMO.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    private final RamMMO plugin;
    public ConfigManager(RamMMO plugin) {
        this.plugin = plugin;
    }

    public void setup(Player player) {
        File dir = new File(plugin.getDataFolder() + "/playerdata/");
        if (!dir.exists()) {
            dir.mkdir();
        }

        File file = new File(plugin.getDataFolder() + "/playerdata/" + player.getUniqueId() + ".yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
                Bukkit.getServer().getConsoleSender().sendMessage(ColorUtils.colorMessage("&2Created player config for UUID: " + player.getUniqueId()));

                FileConfiguration config = YamlConfiguration.loadConfiguration(file);
                MMOPlayer mmoPlayer = new MMOPlayer(plugin, player);

                if (file.exists()) {

                    for (Passive passive : Passives.values()) {
                        config.set("passives." + passive.name().toLowerCase(), true);
                        mmoPlayer.setPassives(passive, true);
                    }

                    mmoPlayer.setPersonalDifficulty(0);
                    plugin.getPlayerManager().addPlayerData(mmoPlayer);
                    try {
                        config.save(file);
                    } catch (IOException e) {
                        Bukkit.getServer().getConsoleSender().sendMessage(ColorUtils.colorMessage("&4Could not save player config for UUID: " + player.getUniqueId()));
                    }
                }
            } catch (IOException e) {
                Bukkit.getServer().getConsoleSender().sendMessage(ColorUtils.colorMessage("&4Could not create player config for UUID: " + player.getUniqueId()));
            }
        }
    }

    public void load(Player player) {
        File file = new File(plugin.getDataFolder() + "/playerdata/" + player.getUniqueId() + ".yml");

        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        MMOPlayer mmoPlayer = new MMOPlayer(plugin, player);

        if (file.exists()) {
            for (Passive passive : Passives.values()) {
                boolean isEnabled = config.getBoolean("passives." + passive.name().toLowerCase());
                mmoPlayer.setPassives(passive, isEnabled);
            }

            int difficulty = config.getInt("difficulty");
            mmoPlayer.setPersonalDifficulty(difficulty);
            plugin.getPlayerManager().addPlayerData(mmoPlayer);
        } else {
            Bukkit.getServer().getConsoleSender().sendMessage(ColorUtils.colorMessage("&bCould not load player config for UUID: " + player.getUniqueId()));
            setup(player);
        }
    }

    public void save(Player player, boolean isShutdown) {
        File file = new File(plugin.getDataFolder() + "/playerdata/" + player.getUniqueId() + ".yml");
        MMOPlayer mmoPlayer = plugin.getPlayerManager().getPlayerData(player);

        if (mmoPlayer == null) return;
        if (mmoPlayer.shouldNotSave()) return;
        if (mmoPlayer.isSaving()) return;
        mmoPlayer.setSaving(true);

        if (file.exists()) {
            FileConfiguration config = YamlConfiguration.loadConfiguration(file);
            try {
                for (Passive passive : Passives.values()) {
                    config.set("passives." + passive.name().toLowerCase(), mmoPlayer.getPassives(passive));
                }

                config.set("difficulty", mmoPlayer.getPersonalDifficulty());

                config.save(file);
                if (isShutdown) {
                    plugin.getPlayerManager().removePlayerData(player.getUniqueId());
                }
            } catch (IOException e) {
                Bukkit.getServer().getConsoleSender().sendMessage(ColorUtils.colorMessage("&bCould not save player config for UUID: " + player.getUniqueId()));
            }
        } else {
            Bukkit.getServer().getConsoleSender().sendMessage(ColorUtils.colorMessage("&bCould not save player config for UUID: " + player.getUniqueId() + " because it does not exist!"));
        }
        mmoPlayer.setSaving(false);
    }

}
