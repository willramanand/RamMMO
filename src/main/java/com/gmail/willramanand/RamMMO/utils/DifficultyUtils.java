package com.gmail.willramanand.RamMMO.utils;

import com.gmail.willramanand.RamMMO.RamMMO;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;

public class DifficultyUtils {

    private final RamMMO plugin;
    private final String PATH = "ender_dragon_defeated";
    private int enderDragonDefeats = 0;

    public DifficultyUtils(RamMMO plugin) {
        this.plugin = plugin;
    }


    public void load() {
        enderDragonDefeats = RamMMO.getInstance().getConfig().getInt(PATH);
        plugin.getLogger().info(ColorUtils.colorMessage("&eEnderDragon defeated: &d" + enderDragonDefeats));
    }

    public void addDefeat() {
        if (enderDragonDefeats >= 5) return;
        enderDragonDefeats++;
        broadcastDefeat();
    }

    private void broadcastDefeat() {
        Component compOne = Component.text("The Ender Dragon has been defeated! ").color(TextColor.color(255, 170, 0));
        Component compTwo = Component.text("The world grows harder!").color(TextColor.color(170, 0, 0)).decorate(TextDecoration.BOLD);
        compOne = compOne.append(compTwo);
        Bukkit.broadcast(compOne);
    }

    public void save() {
        plugin.getConfig().set(PATH, enderDragonDefeats);
        plugin.saveConfig();
    }

    public int getDifficultyModifier() {
        return enderDragonDefeats;
    }

    public String getBossStars() {
        String bossStars = " ";
        for (int i = 0; i < enderDragonDefeats; i++) {
            bossStars += "★";
        }
        return bossStars;
    }
}
