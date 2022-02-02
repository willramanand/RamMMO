package com.gmail.willramanand.RamMMO.utils;

import com.gmail.willramanand.RamMMO.RamMMO;
import com.gmail.willramanand.RamMMO.enums.Passives;
import com.gmail.willramanand.RamMMO.player.MMOPlayer;
import com.gmail.willramanand.RamSkills.api.RamSkillsAPI;
import com.gmail.willramanand.RamSkills.skills.Skills;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;


public class EffectChecker {

    private final RamMMO plugin;
    private final List<PotionEffectType> harmfulPotion = new ArrayList<>();

    public EffectChecker(RamMMO plugin) {
        this.plugin = plugin;
        setupHarmfulEffect();
    }

    public void checkPassives(Player player) {
        MMOPlayer mmoPlayer = plugin.getPlayerManager().getPlayerData(player);
        if (mmoPlayer != null) {
            checkForaging(mmoPlayer);
            checkExcavation(mmoPlayer);
            checkAgility(mmoPlayer);
            checkFishing(mmoPlayer);
            checkMining(mmoPlayer);
        }
    }

    public void checkArmor(Player player) {
        checkHarmfulImmunity(player);
    }

    private void checkFishing(MMOPlayer mmoPlayer) {
        int fishLvl = RamSkillsAPI.getSkillLevel(mmoPlayer.getPlayer(), Skills.FISHING);

        if (mmoPlayer.getPassives(Passives.FISHING_DOLPHINS_GRACE)) {
            if (fishLvl >= 10 && fishLvl < 20) {
                mmoPlayer.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 20, 0));
            } else if (fishLvl >= 20) {
                mmoPlayer.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 20, 1));
            }
        }

        if (mmoPlayer.getPassives(Passives.FISHING_CONDUIT_POWER)) {

            if (fishLvl >= 30 && fishLvl < 40) {
                mmoPlayer.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.CONDUIT_POWER, 20, 0));
            } else if (fishLvl >= 40) {
                mmoPlayer.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.CONDUIT_POWER, 20, 1));
            }
        }
    }

    private void checkAgility(MMOPlayer mmoPlayer) {
        int agilityLvl = RamSkillsAPI.getSkillLevel(mmoPlayer.getPlayer(), Skills.AGILITY);
        if (mmoPlayer.getPassives(Passives.AGILITY_JUMP_BOOST)) {

            if (agilityLvl >= 20 && agilityLvl < 40) {
                mmoPlayer.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20, 0));
            } else if (agilityLvl >= 40) {
                mmoPlayer.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20, 1));
            }
        }

    }

    private void checkMining(MMOPlayer mmoPlayer) {
        ItemStack handItem;
        handItem = mmoPlayer.getPlayer().getInventory().getItemInMainHand();

        if (!isPick(handItem)) {
            return;
        }

        if (!mmoPlayer.getPassives(Passives.MINING_HASTE)) {
            return;
        }

        int mineLvl = RamSkillsAPI.getSkillLevel(mmoPlayer.getPlayer(), Skills.MINING);
        if (mineLvl >= 10 && mineLvl < 20) {
            mmoPlayer.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20, 0));
        } else if (mineLvl >= 20) {
            mmoPlayer.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20, 1));
        }
    }

    private void checkExcavation(MMOPlayer mmoPlayer) {
        ItemStack handItem;
        handItem = mmoPlayer.getPlayer().getInventory().getItemInMainHand();

        if (!isShovel(handItem)) {
            return;
        }

        if (!mmoPlayer.getPassives(Passives.EXCAVATION_HASTE)) {
            return;
        }

        int excavateLvl = RamSkillsAPI.getSkillLevel(mmoPlayer.getPlayer(), Skills.EXCAVATION);
        if (excavateLvl >= 10 && excavateLvl < 20) {
            mmoPlayer.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20, 0));
        } else if (excavateLvl >= 20) {
            mmoPlayer.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20, 1));
        }
    }

    private void checkForaging(MMOPlayer mmoPlayer) {
        ItemStack handItem;
        handItem = mmoPlayer.getPlayer().getInventory().getItemInMainHand();

        if (!isAxe(handItem)) {
            return;
        }

        if (!mmoPlayer.getPassives(Passives.WOODCUTTING_HASTE)) {
            return;
        }

        int axeLvl = RamSkillsAPI.getSkillLevel(mmoPlayer.getPlayer(), Skills.WOODCUTTING);
        if (axeLvl >= 10 && axeLvl < 20) {
            mmoPlayer.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20, 0));
        } else if (axeLvl >= 20) {
            mmoPlayer.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20, 1));
        }
    }

    private void checkHarmfulImmunity(Player player) {
        for (ItemStack item : player.getInventory().getArmorContents()) {
            if (item != null && item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "effect_immunity"))) {
                for (PotionEffectType effect : harmfulPotion) {
                    if (player.hasPotionEffect(effect)) {
                        player.removePotionEffect(effect);
                    }
                }
            }
        }
    }

        private void setupHarmfulEffect () {
            harmfulPotion.add(PotionEffectType.POISON);
            harmfulPotion.add(PotionEffectType.BLINDNESS);
            harmfulPotion.add(PotionEffectType.CONFUSION);
            harmfulPotion.add(PotionEffectType.HARM);
            harmfulPotion.add(PotionEffectType.HUNGER);
            harmfulPotion.add(PotionEffectType.LEVITATION);
            harmfulPotion.add(PotionEffectType.SLOW);
            harmfulPotion.add(PotionEffectType.SLOW_DIGGING);
            harmfulPotion.add(PotionEffectType.UNLUCK);
            harmfulPotion.add(PotionEffectType.WEAKNESS);
            harmfulPotion.add(PotionEffectType.WITHER);
        }

        public boolean isSword (ItemStack is){
            return is.getType().name().contains("SWORD");
        }

        public boolean isPick (ItemStack is){
            return is.getType().name().contains("PICKAXE");
        }

        public boolean isShovel (ItemStack is){
            return is.getType().name().contains("SHOVEL");
        }

        public boolean isAxe (ItemStack is){
            return is.getType().name().contains("_AXE");
        }

        public boolean isBow (ItemStack is){
            return is.getType().name().contains("BOW");
        }
    }
