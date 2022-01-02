package com.gmail.willramanand.RamMMO.commands;

import com.gmail.willramanand.RamMMO.RamMMO;
import com.gmail.willramanand.RamMMO.utils.ColorUtils;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MobsCommand extends SubCommand {

    private final RamMMO plugin;

    public MobsCommand(RamMMO plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onCommand(Player player, String[] args) {
        player.sendMessage(ColorUtils.colorMessage("&eMobs can now spawn at &d5 &edifferent tiers within the wild!"));
        player.sendMessage(ColorUtils.colorMessage("&fCommon Mobs: &eThese are your standard mobs."));
        player.sendMessage(ColorUtils.colorMessage("&2Uncommon Mobs: &a+50% &espeed, &a2x &edamage, &a2x &ehealth."));
        player.sendMessage(ColorUtils.colorMessage("&1Rare Mobs: &a+75% &espeed, &a2x &edamage, &a3x &ehealth."));
        player.sendMessage(ColorUtils.colorMessage("&5Epic Mobs: &c-20% &espeed, &a6x &edamage, &a3x &ehealth."));
        player.sendMessage(ColorUtils.colorMessage("&6Legendary Mobs: &c-40% &espeed, &a8x &edamage, &a4x &ehealth."));
        player.sendMessage(ColorUtils.colorMessage("&eEach mob tier increases in drops, xp and skill xp!"));
    }

    @Override
    public String name() {
        return Commands.MOBS.getName();
    }

    @Override
    public String info() {
        return "Tells info about mob tiers.";
    }

    @Override
    public List<String> aliases() {
        return new ArrayList<>();
    }

    @Override
    public List<String> getPrimaryArguments() {
        return null;
    }

    @Override
    public List<String> getSecondaryArguments(String[] args) {
        return null;
    }
}
