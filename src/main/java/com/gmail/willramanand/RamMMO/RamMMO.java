package com.gmail.willramanand.RamMMO;

import co.aikar.commands.InvalidCommandArgument;
import co.aikar.commands.PaperCommandManager;
import com.gmail.willramanand.RamMMO.boss.BossManager;
import com.gmail.willramanand.RamMMO.boss.Bosses;
import com.gmail.willramanand.RamMMO.commands.MMOCommand;
import com.gmail.willramanand.RamMMO.config.ConfigManager;
import com.gmail.willramanand.RamMMO.item.ItemManager;
import com.gmail.willramanand.RamMMO.listeners.*;
import com.gmail.willramanand.RamMMO.player.PlayerManager;
import com.gmail.willramanand.RamMMO.utils.ColorUtils;
import com.gmail.willramanand.RamMMO.utils.EffectChecker;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class RamMMO extends JavaPlugin {

    private static final Logger log = Logger.getLogger("Minecraft");
    public static RamMMO i;
    private EffectChecker effectChecker;
    private ConfigManager configManager;
    private PlayerManager playerManager;
    private PaperCommandManager commandManager;
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

        effectChecker = new EffectChecker(this);
        configManager = new ConfigManager(this);
        playerManager = new PlayerManager(this);
        ItemListener itemListener = new ItemListener(this);

        // Config
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();

        // Custom Items
        ItemManager.registerItems();
        ItemManager.buildExtraRecipes();

        // Listener runnables
        itemListener.itemMagnetSearch();

        // Bosses
        BossManager.registerBosses();
        BossManager.bossSpawnTicker();

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!playerManager.hasPlayerData(p)) {
                configManager.load(p);
            }
        }

        // Listeners
        Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
        Bukkit.getPluginManager().registerEvents(new EntityListener(this), this);
        Bukkit.getPluginManager().registerEvents(new HealthListener(this), this);
        Bukkit.getPluginManager().registerEvents(itemListener, this);
        Bukkit.getPluginManager().registerEvents(new DamageIndicatorListener(this), this);
        Bukkit.getPluginManager().registerEvents(new VersionListener(this), this);
        Bukkit.getPluginManager().registerEvents(new CraftingListener(this), this);
        Bukkit.getPluginManager().registerEvents(new BossListener(this), this);
        Bukkit.getPluginManager().registerEvents(new GUIListener(this), this);

        // Commands
        registerCommands();

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
        if (BossManager.isActive())
            BossManager.getCurrentBoss().damage(99999);
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

    private void registerCommands() {
        commandManager = new PaperCommandManager(this);

        commandManager.enableUnstableAPI("help");

        commandManager.getCommandContexts().registerContext(Bosses.class, c -> {
            String input = c.popFirstArg();
            Bosses boss = BossManager.getBoss(input);
            if (boss != null) {
                return boss;
            } else {
                throw new InvalidCommandArgument("Boss " + input + " not found!");
            }
        });

        commandManager.getCommandCompletions().registerAsyncCompletion("bosses", context -> {
            List<String> values = new ArrayList<>();
            for (Bosses boss : Bosses.values()) {
                values.add(boss.name().toLowerCase());
            }
            return values;
        });

        commandManager.registerCommand(new MMOCommand(this));
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