package com.gmail.willramanand.RamMMO;

import com.archyx.aureliumskills.api.AureliumAPI;
import com.gmail.willramanand.RamMMO.commands.CommandManager;
import com.gmail.willramanand.RamMMO.items.ItemManager;
import com.gmail.willramanand.RamMMO.items.recipe.RecipeManager;
import com.gmail.willramanand.RamMMO.listeners.EntityListener;
import com.gmail.willramanand.RamMMO.listeners.HealthListener;
import com.gmail.willramanand.RamMMO.listeners.ItemListener;
import com.gmail.willramanand.RamMMO.listeners.PlayerListener;
import com.gmail.willramanand.RamMMO.player.PlayerManager;
import com.gmail.willramanand.RamMMO.utils.ColorUtils;
import com.gmail.willramanand.RamMMO.utils.ConfigManager;
import com.gmail.willramanand.RamMMO.utils.EffectChecker;
import com.gmail.willramanand.RamMMO.utils.Formatter;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.logging.Logger;

public class RamMMO extends JavaPlugin {

    private static final Logger log = Logger.getLogger("Minecraft");
    private CommandManager commandManager;
    private Formatter formatter;
    private EffectChecker effectChecker;
    private ConfigManager configManager;
    private PlayerManager playerManager;
    private static Economy econ = null;
    private static Permission perms = null;
    private static Chat chat = null;


    @Override
    public void onEnable() {
        long startTime = System.currentTimeMillis();
        log.info(ColorUtils.colorMessage("[" + this.getName() + "] &6===&bENABLE START&6==="));
        // Dependencies
        if (!AureliumAPI.getPlugin().isEnabled()) {
            log.info(ColorUtils.colorMessage("[" + this.getName() + "] &4AureliumSkills not detected! Disabling..."));
            getServer().getPluginManager().disablePlugin(this);
        }

        if (isVaultActive()) {
            log.info(ColorUtils.colorMessage("[" + this.getName() + "] &2Enabling Vault integration."));
            setupEconomy();
        }

        commandManager = new CommandManager(this);
        formatter = new Formatter(1);
        effectChecker = new EffectChecker(this);
        configManager = new ConfigManager(this);
        playerManager = new PlayerManager(this);

        // Config
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!playerManager.hasPlayerData(p)) {
                configManager.load(p);
            }
        }

        // Listeners
        Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
        Bukkit.getPluginManager().registerEvents(new EntityListener(this), this);
        Bukkit.getPluginManager().registerEvents(new HealthListener(this), this);
        Bukkit.getPluginManager().registerEvents(new ItemListener(this), this);

        // Commands
        commandManager.setup();

        // Custom Items
        ItemManager.createItems();
        RecipeManager.createRecipes(this);


        new BukkitRunnable() {
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    effectChecker.checkPassives(p);
                    effectChecker.checkArmor(p);
                }
            }
        }.runTaskTimer(this, 0L, 2L);
        startTime = System.currentTimeMillis() - startTime;
        log.info(ColorUtils.colorMessage("[" + this.getName() + "] &6=== &bENABLE &2COMPLETE &6(&eTook &d" + startTime +"ms&6) ==="));
    }

    @Override
    public void onDisable() {
        for (Player player : Bukkit.getOnlinePlayers())
            configManager.save(player, true);

        log.info("Disabled");
    }

    public boolean isVaultActive() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            log.warning("Vault is not installed, disabling Vault integration!");
            return false;
        }
        return true;
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

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public static Economy getEconomy() {
        return econ;
    }
}