package com.gmail.willramanand.RamMMO.commands;

import com.gmail.willramanand.RamMMO.RamMMO;
import com.gmail.willramanand.RamMMO.utils.ColorUtils;
import org.bukkit.entity.Player;

import java.util.List;

public class HelpCommand extends SubCommand {

  private RamMMO plugin;

  public HelpCommand(RamMMO plugin) {
    this.plugin = plugin;
  }

  @Override
  public void onCommand(Player player, String[] args) {

    List<SubCommand> commands = plugin.getCommandManager().getCommandList();
    String prefix = plugin.getCommandManager().main;

    player.sendMessage(ColorUtils.colorMessage("&e---- &d" + plugin.getName() + " Help &e----"));

    commands.forEach(
        command -> player.sendMessage(
            ColorUtils.colorMessage(
                "&d/" + prefix + " &a" + command.name() + "&e: " + command.info())));
  }

  @Override
  public String name() {
    return plugin.getCommandManager().help;
  }

  @Override
  public String info() {
    return "Information on the commands of RamMMO.";
  }

  @Override
  public String[] aliases() {
    return new String[0];
  }

  @Override
  public List<String> getSubCommandArguments() {
    return null;
  }
}
