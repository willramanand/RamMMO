package com.gmail.willramanand.RamMMO.commands;

import com.gmail.willramanand.RamMMO.RamMMO;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class CommandManager implements TabExecutor {

  private ArrayList<SubCommand> commands = new ArrayList<>();
  private RamMMO plugin;

  public CommandManager(RamMMO plugin) {
    this.plugin = plugin;
  }

  public String main = "mmo";
  public String agility = "agility";
  public String fishing = "fishing";
  public String excavation = "excavation";
  public String foraging = "foraging";
  public String mining = "mining";
  public String boss = "boss";
  public String mobs = "mobs";
  public String help = "help";

  public void setup() {
    plugin.getCommand(main).setExecutor(this);

    this.commands.add(new AgilityCommand(plugin));
    this.commands.add(new ExcavationCommand(plugin));
    this.commands.add(new FishingCommand(plugin));
    this.commands.add(new MiningCommand(plugin));
    this.commands.add(new ForagingCommand(plugin));
    this.commands.add(new MobsCommand(plugin));
    this.commands.add(new HelpCommand(plugin));
    this.commands.add(new BossCommand(plugin)); // WIP
  }

  @Override
  public boolean onCommand(
      @NotNull CommandSender sender,
      @NotNull Command command,
      @NotNull String s,
      @NotNull String[] args) {

    if (!(sender instanceof Player)) {
      return true;
    }

    Player player = (Player) sender;

    if (command.getName().equalsIgnoreCase(main)) {
      if (args.length == 0) {
        player.sendMessage("§ePlease add arguments to your command. Type §d/mmo help §e for info.");
        return true;
      }

      SubCommand target = this.get(args[0]);

      if (target == null) {
        player.sendMessage("§eInvalid subcommand. Type §d/mmo help §e for info.");
        return true;
      }

      ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(args));
      arrayList.remove(0);

      try {
        target.onCommand(player, args);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    return true;
  }

  public List<SubCommand> getCommandList() {
    return this.commands;
  }

  private SubCommand get(String name) {

    for (SubCommand subCommand : this.commands) {
      if (subCommand.name().equalsIgnoreCase(name)) {
        return subCommand;
      }

      String[] aliases;
      int length = (aliases = subCommand.aliases()).length;

      for (int var5 = 0; var5 < length; ++var5) {
        String alias = aliases[var5];
        if (name.equalsIgnoreCase(alias)) {
          return subCommand;
        }
      }
    }
    return null;
  }

  @Nullable
  @Override
  public List<String> onTabComplete(
      @NotNull CommandSender sender,
      @NotNull Command command,
      @NotNull String s,
      @NotNull String[] args) {
    Iterator<SubCommand> sc = this.commands.iterator();

    if (args.length == 1) {
      List<String> skills = new ArrayList<>();

      while (sc.hasNext()) {
        SubCommand subCommand = sc.next();
        skills.add(subCommand.name());
      }
      return skills;
    }
    if (args.length == 2) {
      while (sc.hasNext()) {
        SubCommand subCommand = sc.next();

        if (subCommand.name().equalsIgnoreCase(args[0])) {
          return subCommand.getSubCommandArguments();
        }
      }
    }
    return null;
  }
}
