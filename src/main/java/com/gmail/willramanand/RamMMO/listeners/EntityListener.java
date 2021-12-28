package com.gmail.willramanand.RamMMO.listeners;

import com.archyx.aureliumskills.api.AureliumAPI;
import com.archyx.aureliumskills.leveler.SkillLeveler;
import com.archyx.aureliumskills.skills.Skill;
import com.archyx.aureliumskills.skills.Skills;
import com.archyx.aureliumskills.skills.archery.ArcheryLeveler;
import com.archyx.aureliumskills.skills.archery.ArcherySource;
import com.archyx.aureliumskills.skills.fighting.FightingLeveler;
import com.archyx.aureliumskills.skills.fighting.FightingSource;
import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;
import com.gmail.willramanand.RamMMO.RamMMO;
import com.gmail.willramanand.RamMMO.boss.MMOBoss;
import com.gmail.willramanand.RamMMO.mobs.MobConverter;
import com.gmail.willramanand.RamMMO.utils.ColorUtils;
import net.ess3.api.MaxMoneyException;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EntityListener implements Listener {

    private final BigDecimal COMMON_MONEY = new BigDecimal(0.05);

    private final double UNCOMMON_XP = 2.5;
    private final int UNCOMMON_DROP = 2;
    private final BigDecimal UNCOMMON_MONEY = new BigDecimal(0.75);

    private final double RARE_XP = 5;
    private final int RARE_DROP = 4;
    private final BigDecimal RARE_MONEY = new BigDecimal(1.25);

    private final double EPIC_XP = 15;
    private final int EPIC_DROP = 8;
    private final BigDecimal EPIC_MONEY = new BigDecimal(5.0);

    private final double LEGEND_XP = 25;
    private final int LEGEND_DROP = 16;
    private final BigDecimal LEGEND_MONEY = new BigDecimal(25.0);

    private final BigDecimal BOSS_MONEY = new BigDecimal(250.0);

    private RamMMO plugin;
    private MobConverter mobConverter;
    private SkillLeveler skillLeveler;
    private Economy econ;

    public EntityListener(RamMMO plugin) {
        this.plugin = plugin;
        econ = plugin.getEconomy();
        mobConverter = new MobConverter(plugin);
    }

    @EventHandler
    public void onSpawn(CreatureSpawnEvent event) {
        Entity entity = event.getEntity();
        Random rand = new Random();

        if (!(entity instanceof Monster) || entity instanceof ZombieVillager || entity instanceof Boss) {
            return;
        }

        String rarity;
        int randomInt = rand.nextInt(1001);

        if (randomInt >= 850 && randomInt < 950) {
            rarity = "uncommon";
        } else if (randomInt >= 950 && randomInt < 980) {
            rarity = "rare";
        } else if (randomInt >= 980 && randomInt < 999) {
            rarity = "epic";
        } else if (randomInt >= 999) {
            rarity = "legend";
        } else {
            rarity = "common";
        }
        mobConverter.convertMob(entity, rarity);
    }

    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        int xp = event.getDroppedExp();
        List<ItemStack> droppedItems = event.getDrops();
        List<ItemStack> newDrops = new ArrayList<>();

        if (event.getEntity().getKiller() == null) {
            return;
        }

        Player player = event.getEntity().getKiller();

        if (!(entity instanceof Monster) || entity instanceof ZombieVillager) {
            return;
        }

        List<MetadataValue> rarityMeta = entity.getMetadata("Rarity");
        try {
            if (entity instanceof Boss || entity instanceof MMOBoss) {
                if (plugin.isEssActive() && econ.playerExists(player.getUniqueId())) {
                    econ.add(player.getUniqueId(), BOSS_MONEY);
                }
            }

            if (!rarityMeta.isEmpty()) {

                for (MetadataValue value : rarityMeta) {
                    int rarity = value.asInt();

                    ItemStack handItem = player.getInventory().getItemInMainHand();

                    Skill skill;
                    SkillLeveler skillLeveler;

                    if (plugin.getChecker().isBow(handItem)) {
                        skill = Skills.ARCHERY;
                        skillLeveler = new ArcheryLeveler(AureliumAPI.getPlugin());
                    } else {
                        skill = Skills.FIGHTING;
                        skillLeveler = new FightingLeveler(AureliumAPI.getPlugin());
                    }

                    if (skill == Skills.ARCHERY) {
                        switch (rarity) {
                            case 1 -> {
                                if (plugin.isEssActive() && econ.playerExists(player.getUniqueId())) {
                                    econ.add(player.getUniqueId(), UNCOMMON_MONEY);
                                }
                                AureliumAPI.addXp(player, skill, UNCOMMON_XP * skillLeveler.getXp(player, ArcherySource.valueOf(entity.getType().toString())));
                                event.setDroppedExp((int) UNCOMMON_XP * xp);
                                for (int i = 0; i < UNCOMMON_DROP; i++) {
                                    newDrops.addAll(droppedItems);
                                }
                            }
                            case 2 -> {
                                if (plugin.isEssActive() && econ.playerExists(player.getUniqueId())) {
                                    econ.add(player.getUniqueId(), RARE_MONEY);
                                }
                                AureliumAPI.addXp(player, skill, RARE_XP * skillLeveler.getXp(player, ArcherySource.valueOf(entity.getType().toString())));
                                event.setDroppedExp((int) RARE_XP * xp);
                                for (int i = 0; i < RARE_DROP; i++) {
                                    newDrops.addAll(droppedItems);
                                }
                            }
                            case 3 -> {
                                if (plugin.isEssActive() && econ.playerExists(player.getUniqueId())) {
                                    econ.add(player.getUniqueId(), EPIC_MONEY);
                                }
                                AureliumAPI.addXp(player, skill, EPIC_XP * skillLeveler.getXp(player, ArcherySource.valueOf(entity.getType().toString())));
                                event.setDroppedExp((int) EPIC_XP * xp);
                                for (int i = 0; i < EPIC_DROP; i++) {
                                    newDrops.addAll(droppedItems);
                                }
                            }
                            case 4 -> {
                                if (plugin.isEssActive() && econ.playerExists(player.getUniqueId())) {
                                    econ.add(player.getUniqueId(), LEGEND_MONEY);
                                }
                                AureliumAPI.addXp(player, skill, LEGEND_XP * skillLeveler.getXp(player, ArcherySource.valueOf(entity.getType().toString())));
                                event.setDroppedExp((int) LEGEND_XP * xp);
                                for (int i = 0; i < LEGEND_DROP; i++) {
                                    newDrops.addAll(droppedItems);
                                }
                            }
                            default -> {
                            }
                        }
                    } else if (skill == Skills.FIGHTING) {
                        switch (rarity) {
                            case 1 -> {
                                if (plugin.isEssActive() && econ.playerExists(player.getUniqueId())) {
                                    econ.add(player.getUniqueId(), UNCOMMON_MONEY);
                                }
                                AureliumAPI.addXp(player, skill, UNCOMMON_XP * skillLeveler.getXp(player, FightingSource.valueOf(entity.getType().toString())));
                                event.setDroppedExp((int) UNCOMMON_XP * xp);
                                for (int i = 0; i < UNCOMMON_DROP; i++) {
                                    newDrops.addAll(droppedItems);
                                }
                            }
                            case 2 -> {
                                if (plugin.isEssActive() && econ.playerExists(player.getUniqueId())) {
                                    econ.add(player.getUniqueId(), RARE_MONEY);
                                }
                                AureliumAPI.addXp(player, skill, RARE_XP * skillLeveler.getXp(player, FightingSource.valueOf(entity.getType().toString())));
                                event.setDroppedExp((int) RARE_XP * xp);
                                for (int i = 0; i < RARE_DROP; i++) {
                                    newDrops.addAll(droppedItems);
                                }
                            }
                            case 3 -> {
                                if (plugin.isEssActive() && econ.playerExists(player.getUniqueId())) {
                                    econ.add(player.getUniqueId(), EPIC_MONEY);
                                }
                                AureliumAPI.addXp(player, skill, EPIC_XP * skillLeveler.getXp(player, FightingSource.valueOf(entity.getType().toString())));
                                event.setDroppedExp((int) EPIC_XP * xp);
                                for (int i = 0; i < EPIC_DROP; i++) {
                                    newDrops.addAll(droppedItems);
                                }
                            }
                            case 4 -> {
                                if (plugin.isEssActive() && econ.playerExists(player.getUniqueId())) {
                                    econ.add(player.getUniqueId(), LEGEND_MONEY);
                                }
                                AureliumAPI.addXp(player, skill, LEGEND_XP * skillLeveler.getXp(player, FightingSource.valueOf(entity.getType().toString())));
                                event.setDroppedExp((int) LEGEND_XP * xp);
                                for (int i = 0; i < LEGEND_DROP; i++) {
                                    newDrops.addAll(droppedItems);
                                }
                            }
                            default -> {
                            }
                        }
                    }
                }
                for (ItemStack newDrop : newDrops) {
                    event.getEntity().getWorld().dropItem(entity.getLocation(), newDrop);
                }
            } else {
                if (plugin.isEssActive() && econ.playerExists(player.getUniqueId())) {
                    econ.add(player.getUniqueId(), COMMON_MONEY);
                }
            }
        } catch (MaxMoneyException e) {
            player.sendMessage(ColorUtils.colorMessage("&4You already have the max amount of money!"));
        } catch (UserDoesNotExistException e) {
            plugin.getLogger().severe("User with UUID: " + player.getUniqueId() + " does not exist!");
        } catch (NoLoanPermittedException e) {
            player.sendMessage(ColorUtils.colorMessage("&4You are not permitted for additonal loans!"));
        }
    }
}
