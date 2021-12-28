package com.gmail.willramanand.RamMMO.utils;

import com.gmail.willramanand.RamMMO.RamMMO;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class ConfigManager {

    private RamMMO plugin;

    public ConfigManager(RamMMO plugin) {
        this.plugin = plugin;
    }

    public void setup(UUID playerUuid) {
        File dir = new File(plugin.getDataFolder() + "/playerdata/");
        if (!dir.exists()) {
            dir.mkdir();
        }

        File file = new File(plugin.getDataFolder() + "/playerdata/" + playerUuid + ".yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
                Bukkit.getServer().getConsoleSender().sendMessage(ColorUtils.colorMessage("&2Created player config for UUID: " + playerUuid));

                FileConfiguration config = YamlConfiguration.loadConfiguration(file);

                if (file.exists()) {
                    config.set("mining.haste", true);

                    // Excavation initialize
                    config.set("excavation.haste", true);

                    // Foraging initialize
                    config.set("foraging.haste", true);

                    // Fishing initialize
                    config.set("fishing.dolphins_grace", true);
                    config.set("fishing.conduit_power", true);

                    // Agility initialize
                    config.set("agility.speed", true);
                    config.set("agility.jump_boost", true);

                    try {
                        config.save(file);
                    } catch (IOException e) {
                        Bukkit.getServer().getConsoleSender().sendMessage(ColorUtils.colorMessage("&4Could not save player config for UUID: " + playerUuid));
                    }
                }
            } catch (IOException e) {
                Bukkit.getServer().getConsoleSender().sendMessage(ColorUtils.colorMessage("&4Could not create player config for UUID: " + playerUuid));
            }
        }
    }

    public FileConfiguration load(UUID playerUuid) {
        File file = new File(plugin.getDataFolder() + "/playerdata/" + playerUuid + ".yml");

        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        if (file.exists()) {
            return config;
        }
        Bukkit.getServer().getConsoleSender().sendMessage(ColorUtils.colorMessage("&bCould not load player config for UUID: " + playerUuid));
        return null;
    }
}
