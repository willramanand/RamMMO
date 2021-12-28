package com.gmail.willramanand.RamMMO;

import com.archyx.aureliumskills.api.AureliumAPI;
import com.earth2me.essentials.api.Economy;
import com.gmail.willramanand.RamMMO.commands.CommandManager;
import com.gmail.willramanand.RamMMO.listeners.EntityListener;
import com.gmail.willramanand.RamMMO.listeners.HealthListener;
import com.gmail.willramanand.RamMMO.listeners.PlayerListener;
import com.gmail.willramanand.RamMMO.utils.ColorUtils;
import com.gmail.willramanand.RamMMO.utils.ConfigManager;
import com.gmail.willramanand.RamMMO.utils.EffectChecker;
import com.gmail.willramanand.RamMMO.utils.Formatter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.logging.Logger;

public class RamMMO extends JavaPlugin {

    private RamMMO plugin = this;
    private static final Logger log = Logger.getLogger("Minecraft");
    private CommandManager commandManager = new CommandManager(this);
    private Formatter formatter = new Formatter(1);
    private EffectChecker effectChecker = new EffectChecker(this);
    private ConfigManager configManager = new ConfigManager(this);
    private static Economy econ = null;

    @Override
    public void onEnable() {
        // Dependencies
        if (!AureliumAPI.getPlugin().isEnabled()) {
            getServer().getPluginManager().disablePlugin(this);
        }

        if (isEssActive()) {
            log.info(ColorUtils.colorMessage("&2Enabling EssentialsX integration."));
            setupEconomy();
        }

        log.info(ColorUtils.colorMessage("[" + this.getName() + "] &6===&bENABLE START&6==="));

        // Config
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();

        // Listeners
        Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
        Bukkit.getPluginManager().registerEvents(new EntityListener(this), this);
        Bukkit.getPluginManager().registerEvents(new HealthListener(this), this);

        // Commands
        commandManager.setup();

        new BukkitRunnable() {
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    effectChecker.checkPassives(p);
                }
            }
        }.runTaskTimer(this, 0L, 2L);
    }

    @Override
    public void onDisable() {
        log.info("Disabled");
    }

    public boolean isEssActive() {
        if (getServer().getPluginManager().getPlugin("Essentials") == null) {
            log.warning("EssentialsX is not installed, disabling EssentialsX integration!");
            return false;
        }
        return true;
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public Formatter getFormatter() {
        return formatter;
    }

    public EffectChecker getChecker() {
        return effectChecker;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public Economy getEconomy() {
        return econ;
    }
}
