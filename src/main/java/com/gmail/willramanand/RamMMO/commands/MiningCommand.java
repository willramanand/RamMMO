package com.gmail.willramanand.RamMMO.commands;

import com.gmail.willramanand.RamMMO.RamMMO;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MiningCommand extends SubCommand {

  RamMMO plugin;

  public MiningCommand(RamMMO plugin) {
    this.plugin = plugin;
  }

  @Override
  public void onCommand(Player player, String[] args) {
    if (player == null) {
      return;
    }

    String arg0 = plugin.getCommandManager().mining;
    String arg1 = "";
    try {
      if (args.length == 2) {
        if (args[1].equalsIgnoreCase("haste")) {
          arg1 = "haste";
        } else {
          player.sendMessage("§4Valid agility arguments: haste");
        }
        if (plugin
            .getConfig()
            .getBoolean("player." + player.getUniqueId() + "." + arg0 + "." + arg1)) {
          plugin.getConfig().set("player." + player.getUniqueId() + "." + arg0 + "." + arg1, false);
          player.sendMessage(
              "§eAbility effect for §d"
                  + arg1
                  + " §ein skill §d"
                  + arg0
                  + " §ehas been §cdisabled");
        } else {
          plugin.getConfig().set("player." + player.getUniqueId() + "." + arg0 + "." + arg1, true);
          player.sendMessage(
              "§eAbility effect for §d" + arg1 + " §ein skill §d" + arg0 + " §ehas been §aenabled");
        }
        plugin.saveConfig();
      } else {
        player.sendMessage("§e/mmo §dmining §a<effect>");
      }
    } catch (IllegalArgumentException e) {
      player.sendMessage(ChatColor.DARK_RED + "Not valid argument!");
    }
  }

  @Override
  public String name() {
    return plugin.getCommandManager().mining;
  }

  @Override
  public String info() {
    return "Toggle passive effects for Mining.";
  }

  @Override
  public String[] aliases() {
    return new String[0];
  }

  @Override
  public List<String> getSubCommandArguments() {
    List<String> args = new ArrayList<>();

    args.add("haste");

    return args;
  }
}
