package com.gmail.willramanand.RamMMO;

import com.archyx.aureliumskills.api.AureliumAPI;
import com.gmail.willramanand.RamMMO.commands.CommandManager;
import com.gmail.willramanand.RamMMO.listeners.EntityListener;
import com.gmail.willramanand.RamMMO.listeners.PlayerListener;
import com.gmail.willramanand.RamMMO.utils.ColorUtils;
import com.gmail.willramanand.RamMMO.utils.EffectChecker;
import com.gmail.willramanand.RamMMO.utils.Formatter;
import com.gmail.willramanand.RamMMO.listeners.HealthListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class RamMMO extends JavaPlugin {

  private RamMMO plugin = this;
  private CommandManager commandManager = new CommandManager(this);
  private Formatter formatter = new Formatter(1);
  private EffectChecker effectChecker = new EffectChecker(this);

  @Override
  public void onEnable() {
    if (!AureliumAPI.getPlugin().isEnabled()) {
      getServer().getPluginManager().disablePlugin(this);
    }

    long enableTime = System.currentTimeMillis();
    getServer()
        .getConsoleSender()
        .sendMessage(
            ColorUtils.colorMessage("&f[" + this.getName() + "] &6===&bENABLE START&6==="));

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
    getLogger().info("Disabled");
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
}
