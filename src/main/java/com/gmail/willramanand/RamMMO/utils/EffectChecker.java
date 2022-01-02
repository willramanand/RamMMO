package com.gmail.willramanand.RamMMO.utils;

import com.archyx.aureliumskills.api.AureliumAPI;
import com.archyx.aureliumskills.skills.Skills;
import com.gmail.willramanand.RamMMO.RamMMO;
import com.gmail.willramanand.RamMMO.enums.Passives;
import com.gmail.willramanand.RamMMO.items.Item;
import com.gmail.willramanand.RamMMO.items.ItemManager;
import com.gmail.willramanand.RamMMO.player.MMOPlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;


public class EffectChecker {

    private final RamMMO plugin;
    private MMOPlayer mmoPlayer;
    private final List<PotionEffectType> harmfulPotion = new ArrayList<>();

    public EffectChecker(RamMMO plugin) {
        this.plugin = plugin;
        setupHarmfulEffect();
    }

    public void checkPassives(Player player) {
        this.mmoPlayer = plugin.getPlayerManager().getPlayerData(player);
        if (mmoPlayer != null) {
            checkForaging(mmoPlayer);
            checkExcavation(mmoPlayer);
            checkAgility(mmoPlayer);
            checkFishing(mmoPlayer);
            checkMining(mmoPlayer);
        }
    }

    public void checkArmor(Player player) {
        checkHelmet(player);
        checkLeggings(player);
    }

    private void checkFishing(MMOPlayer mmoPlayer) {
        int fishLvl = AureliumAPI.getSkillLevel(mmoPlayer.getPlayer(), Skills.FISHING);

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
        int agilityLvl = AureliumAPI.getSkillLevel(mmoPlayer.getPlayer(), Skills.AGILITY);
        if (mmoPlayer.getPassives(Passives.AGILITY_SPEED)) {
            if (agilityLvl >= 10 && agilityLvl < 30) {
                mmoPlayer.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20, 0));
            } else if (agilityLvl >= 30) {
                mmoPlayer.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20, 1));
            }
        }

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

        int mineLvl = AureliumAPI.getSkillLevel(mmoPlayer.getPlayer(), Skills.MINING);
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

        int excavateLvl = AureliumAPI.getSkillLevel(mmoPlayer.getPlayer(), Skills.EXCAVATION);
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

        if (!mmoPlayer.getPassives(Passives.FORAGING_HASTE)) {
            return;
        }

        int axeLvl = AureliumAPI.getSkillLevel(mmoPlayer.getPlayer(), Skills.FORAGING);
        if (axeLvl >= 10 && axeLvl < 20) {
            mmoPlayer.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20, 0));
        } else if (axeLvl >= 20) {
            mmoPlayer.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20, 1));
        }
    }

    private void checkHelmet(Player player) {

        if (player.getInventory().getHelmet() == null || player.getInventory().getHelmet().lore() == null) {
            return;
        }

        if (player.getInventory().getHelmet().lore().equals(ItemManager.getItem(Item.NETHERFIRE_HELM).lore())) {
            for (PotionEffectType effect : harmfulPotion) {
                if (player.hasPotionEffect(effect)) {
                    player.removePotionEffect(effect);
                }
            }
        }
    }

    private void checkLeggings(Player player) {
        double baseMove = 0.1;

        if (player.getInventory().getLeggings() == null || player.getInventory().getLeggings().lore() == null) {
            player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(baseMove);
            return;
        }

        if (player.getInventory().getLeggings().lore().equals(ItemManager.getItem(Item.NETHERFIRE_LEGS).lore())) {
            player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(baseMove * 1.4);

        } else {
            player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(baseMove);
        }
    }

    private void setupHarmfulEffect() {
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

    public boolean isBow(ItemStack is) {
        return is.getType().name().contains("BOW");
    }
}
