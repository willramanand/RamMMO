package com.gmail.willramanand.RamMMO.listeners;

import com.gmail.willramanand.RamMMO.RamMMO;
import com.gmail.willramanand.RamMMO.utils.ColorUtils;
import com.gmail.willramanand.RamSkills.events.SkillLevelUpEvent;
import com.gmail.willramanand.RamSkills.skills.Skill;
import com.gmail.willramanand.RamSkills.skills.Skills;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    private final RamMMO plugin;

    public PlayerListener(RamMMO plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void join(PlayerLoginEvent event) {
        plugin.getConfigManager().load(event.getPlayer());
    }

    @EventHandler
    public void leave(PlayerQuitEvent event) {
        plugin.getConfigManager().save(event.getPlayer(), false);
    }


    @EventHandler
    public void levelUp(SkillLevelUpEvent event) {
        Player player = event.getPlayer();
        Skill skill = event.getSkill();
        int lvl = event.getSkillLvl();

        // Agility
        if (skill == Skills.AGILITY && lvl == 10) {
            player.sendMessage(ColorUtils.colorMessage("&eYou have unlocked &dSpeed &epassive effect."));
            player.sendMessage(ColorUtils.colorMessage("&eReach level &a20&e to unlock &dJump Boost&e."));
        } else if (skill == Skills.AGILITY && lvl == 20) {
            player.sendMessage(ColorUtils.colorMessage("&eYou have unlocked &dJump Boost &epassive effect."));
            player.sendMessage(ColorUtils.colorMessage("&eReach level &a30&e to unlock &dSpeed II&e."));
        } else if (skill == Skills.AGILITY && lvl == 30) {
            player.sendMessage(ColorUtils.colorMessage("&eYou have unlocked &dSpeed II &epassive effect."));
            player.sendMessage(ColorUtils.colorMessage("&eReach level &a40&e to unlock &dJump Boost II&e."));
        } else if (skill == Skills.AGILITY && lvl == 40) {
            player.sendMessage(ColorUtils.colorMessage("&eYou have unlocked &dJump Boost II &epassive effect."));
            player.sendMessage(ColorUtils.colorMessage("&eYou have unlocked all additional passive effects for &dAgility&e."));
        }

        // Fishing
        if (skill == Skills.FISHING && lvl == 10) {
            player.sendMessage(ColorUtils.colorMessage("&eYou have unlocked &dDolphin's Grace &epassive effect."));
            player.sendMessage(ColorUtils.colorMessage("&eReach level &a20&e to unlock &dDolphin's Grace II&e."));
        } else if (skill == Skills.FISHING && lvl == 20) {
            player.sendMessage(ColorUtils.colorMessage("&eYou have unlocked &dDolphin's Grace II &epassive effect."));
            player.sendMessage(ColorUtils.colorMessage("&eReach level &a30&e to unlock &dConduit Power&e."));
        } else if (skill == Skills.FISHING && lvl == 30) {
            player.sendMessage(ColorUtils.colorMessage("&eYou have unlocked &dConduit Power &epassive effect."));
            player.sendMessage(ColorUtils.colorMessage("&eReach level &a40&e to unlock &dConduit Power II&e."));
        } else if (skill == Skills.FISHING && lvl == 40) {
            player.sendMessage(ColorUtils.colorMessage("&eYou have unlocked &dConduit Power II &epassive effect."));
            player.sendMessage(ColorUtils.colorMessage("&eYou have unlocked all additional passive effects for &dFishing&e."));
        }

        // Mining
        if (skill == Skills.MINING && lvl == 10) {
            player.sendMessage(ColorUtils.colorMessage("&eYou have unlocked &dHaste &epassive effect."));
            player.sendMessage(ColorUtils.colorMessage("&eReach level &a20&e to unlock &dHaste II&e."));
        } else if (skill == Skills.MINING && lvl == 20) {
            player.sendMessage(ColorUtils.colorMessage("&eYou have unlocked &dHaste II &epassive effect."));
            player.sendMessage(ColorUtils.colorMessage("&eYou have unlocked all additional passive effects for &dMining&e."));
        }

        // Excavation
        if (skill == Skills.EXCAVATION && lvl == 10) {
            player.sendMessage(ColorUtils.colorMessage("&eYou have unlocked &dHaste &epassive effect."));
            player.sendMessage(ColorUtils.colorMessage("&eReach level &a20&e to unlock &dHaste II&e."));
        } else if (skill == Skills.EXCAVATION && lvl == 20) {
            player.sendMessage(ColorUtils.colorMessage("&eYou have unlocked &dHaste II &epassive effect."));
            player.sendMessage(ColorUtils.colorMessage("&eYou have unlocked all additional passive effects for &dExcavation&e."));
        }

        // Foraging
        if (skill == Skills.WOODCUTTING && lvl == 10) {
            player.sendMessage(ColorUtils.colorMessage("&eYou have unlocked &dHaste &epassive effect."));
            player.sendMessage(ColorUtils.colorMessage("&eReach level &a20&e to unlock &dHaste II&e."));
        } else if (skill == Skills.WOODCUTTING && lvl == 20) {
            player.sendMessage(ColorUtils.colorMessage("&eYou have unlocked &dHaste II &epassive effect."));
            player.sendMessage(ColorUtils.colorMessage("&eYou have unlocked all additional passive effects for &dWoodcutting&e."));
        }
    }
}
