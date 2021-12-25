package com.gmail.willramanand.RamMMO.utils;

import com.archyx.aureliumskills.api.AureliumAPI;
import com.archyx.aureliumskills.skills.Skills;
import com.gmail.willramanand.RamMMO.RamMMO;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EffectChecker {

    private RamMMO plugin;

    public EffectChecker(RamMMO plugin) {
        this.plugin = plugin;
    }

    public void checkPassives(Player player) {
        checkForaging(player);
        checkExcavation(player);
        checkAgility(player);
        checkFishing(player);
        checkMining(player);
    }

    private void checkFishing(Player player) {
        int fishLvl = AureliumAPI.getSkillLevel(player, Skills.FISHING);

        if (plugin.getConfig().getBoolean("player." + player.getUniqueId() + ".fishing.dolphins_grace")) {
            if (fishLvl >= 10 && fishLvl < 20) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 20, 0));
            } else if (fishLvl >= 20 && fishLvl < 30) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 20, 1));
            } else if (fishLvl >= 30 && fishLvl < 40) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 20, 1));
                player.addPotionEffect(new PotionEffect(PotionEffectType.CONDUIT_POWER, 20, 0));
            } else if (fishLvl >= 40) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 20, 1));
                player.addPotionEffect(new PotionEffect(PotionEffectType.CONDUIT_POWER, 20, 1));
            }
        }

        if (plugin.getConfig().getBoolean("player." + player.getUniqueId() + ".fishing.conduit_power")) {

            if (fishLvl >= 30 && fishLvl < 40) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.CONDUIT_POWER, 20, 0));
            } else if (fishLvl >= 40) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.CONDUIT_POWER, 20, 1));
            }
        }
    }

    private void checkAgility(Player player) {
        int agilityLvl = AureliumAPI.getSkillLevel(player, Skills.AGILITY);

        if (plugin.getConfig().getBoolean("player." + player.getUniqueId() + ".agility.speed")) {
            if (agilityLvl >= 10 && agilityLvl < 20) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20, 0));
            } else if (agilityLvl >= 30 && agilityLvl < 40) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20, 1));
            } else if (agilityLvl >= 40) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20, 1));
            }
        }

        if (plugin.getConfig().getBoolean("player." + player.getUniqueId() + ".agility.jump_boost")) {

            if (agilityLvl >= 20 && agilityLvl < 30) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20, 0));
            } else if (agilityLvl >= 30 && agilityLvl < 40) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20, 0));
            } else if (agilityLvl >= 40) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20, 1));
            }
        }

    }

    private void checkMining(Player player) {
        ItemStack handItem;
        handItem = player.getInventory().getItemInMainHand();

        if (!isPick(handItem)) {
            return;
        }

        if (!plugin.getConfig().getBoolean("player." + player.getUniqueId() + ".mining.haste")) {
            return;
        }

        int mineLvl = AureliumAPI.getSkillLevel(player, Skills.MINING);
        if (mineLvl >= 10 && mineLvl < 20) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20, 0));
        } else if (mineLvl >= 20) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20, 1));
        } else {
            return;
        }
    }

    private void checkExcavation(Player player) {
        ItemStack handItem;
        handItem = player.getInventory().getItemInMainHand();

        if (!isShovel(handItem)) {
            return;
        }

        if (!plugin.getConfig().getBoolean("player." + player.getUniqueId() + ".excavation.haste")) {
            return;
        }

        int excavateLvl = AureliumAPI.getSkillLevel(player, Skills.EXCAVATION);
        if (excavateLvl >= 10 && excavateLvl < 20) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20, 0));
        } else if (excavateLvl >= 20) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20, 1));
        } else {
            return;
        }
    }

    private void checkForaging(Player player) {
        ItemStack handItem;
        handItem = player.getInventory().getItemInMainHand();

        if (!isAxe(handItem)) {
            return;
        }

        if (!plugin.getConfig().getBoolean("player." + player.getUniqueId() + ".foraging.haste")) {
            return;
        }

        int axeLvl = AureliumAPI.getSkillLevel(player, Skills.FORAGING);
        if (axeLvl >= 10 && axeLvl < 20) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20, 0));
        } else if (axeLvl >= 20) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20, 1));
        } else {
            return;
        }
    }

    public boolean isSword(ItemStack is) {
        return is.getType().name().contains("SWORD");
    }

    public boolean isPick(ItemStack is) {
        return is.getType().name().contains("PICKAXE");
    }

    public boolean isShovel(ItemStack is) {
        return is.getType().name().contains("SHOVEL");
    }

    public boolean isAxe(ItemStack is) {
        return is.getType().name().contains("_AXE");
    }

    public boolean isBow(ItemStack is) { return is.getType().name().contains("BOW");}
}
