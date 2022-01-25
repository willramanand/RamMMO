package com.gmail.willramanand.RamMMO;

import com.gmail.willramanand.RamMMO.commands.CommandManager;
import com.gmail.willramanand.RamMMO.config.ConfigManager;
import com.gmail.willramanand.RamMMO.items.ItemManager;
import com.gmail.willramanand.RamMMO.items.recipe.RecipeManager;
import com.gmail.willramanand.RamMMO.listeners.*;
import com.gmail.willramanand.RamMMO.player.PlayerManager;
import com.gmail.willramanand.RamMMO.utils.ColorUtils;
import com.gmail.willramanand.RamMMO.utils.DifficultyUtils;
import com.gmail.willramanand.RamMMO.utils.EffectChecker;
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
    public static RamMMO i;
    private CommandManager commandManager;
    private EffectChecker effectChecker;
    private ConfigManager configManager;
    private PlayerManager playerManager;
    private DifficultyUtils difficultyUtils;
    private static Economy econ = null;
    private static Permission perms = null;
    private static Chat chat = null;


    @Override
    public void onEnable() {
        i = this;

        long startTime = System.currentTimeMillis();
        log.info(ColorUtils.colorMessage("[" + this.getName() + "] &6===&b ENABLE START &6==="));
        // Dependencies
        if (getServer().getPluginManager().getPlugin("RamSkills") == null) {
            log.info(ColorUtils.colorMessage("[" + this.getName() + "] &4RamSkills not detected! Disabling..."));
            setEnabled(false);
        }

        if (isVaultActive()) {
            log.info(ColorUtils.colorMessage("[" + this.getName() + "] &2Enabling Vault integration."));
            setupEconomy();
        }

        commandManager = new CommandManager(this);
        effectChecker = new EffectChecker(this);
        configManager = new ConfigManager(this);
        playerManager = new PlayerManager(this);
        difficultyUtils = new DifficultyUtils(this);

        // Config
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        difficultyUtils.load();

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
        Bukkit.getPluginManager().registerEvents(new DamageIndicatorListener(this), this);
        //Bukkit.getPluginManager().registerEvents(new BossListener(this), this);

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
        difficultyUtils.save();
        log.info("Disabled");
    }

    public static RamMMO getInstance() {
        return i;
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

    public EffectChecker getChecker() {
        return effectChecker;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public DifficultyUtils getDifficultyUtils() { return difficultyUtils; }

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