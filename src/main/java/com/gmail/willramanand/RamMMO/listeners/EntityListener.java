package com.gmail.willramanand.RamMMO.listeners;

import com.archyx.aureliumskills.api.AureliumAPI;
import com.archyx.aureliumskills.leveler.SkillLeveler;
import com.archyx.aureliumskills.skills.Skill;
import com.archyx.aureliumskills.skills.Skills;
import com.archyx.aureliumskills.skills.archery.ArcheryLeveler;
import com.archyx.aureliumskills.skills.archery.ArcherySource;
import com.archyx.aureliumskills.skills.fighting.FightingLeveler;
import com.archyx.aureliumskills.skills.fighting.FightingSource;
import com.gmail.willramanand.RamMMO.RamMMO;
import com.gmail.willramanand.RamMMO.mobs.MobConverter;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class EntityListener implements Listener {

    private final double UNCOMMON_XP = 2.5;
    private final int UNCOMMON_DROP = 2;

    private final double RARE_XP = 5;
    private final int RARE_DROP = 4;

    private final double EPIC_XP = 15;
    private final int EPIC_DROP = 8;

    private final double LEGEND_XP = 25;
    private final int LEGEND_DROP = 16;

    private RamMMO plugin;
    private MobConverter mobConverter;
    private SkillLeveler skillLeveler;


    public EntityListener(RamMMO plugin) {
        this.plugin = plugin;
        mobConverter = new MobConverter(plugin);
    }

    @EventHandler
    public void onSpawn(CreatureSpawnEvent event) {
        Entity entity = event.getEntity();
        Random rand = new Random();

        if (!(entity instanceof Monster)) {
            return;
        }

        if (entity instanceof ZombieVillager) {
            return;
        }

        if (entity instanceof Boss) {
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
        } else if (randomInt >= 999 && randomInt < 1001) {
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

        if (!(event.getEntity().getKiller() instanceof Player)) {
            return;
        }

        Player player = event.getEntity().getKiller();

        if (!(entity instanceof Monster)) {
            return;
        }

        if (entity instanceof ZombieVillager) {
            return;
        }

        if (entity instanceof Boss) {
            return;
        }

        List<MetadataValue> rarityMeta = entity.getMetadata("Rarity");
        if (!rarityMeta.isEmpty()) {
            Iterator<MetadataValue> metadataValueIterator = rarityMeta.iterator();

            while (metadataValueIterator.hasNext()) {
                MetadataValue value = metadataValueIterator.next();
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
                        case 1:
                            AureliumAPI.addXp(player, skill, UNCOMMON_XP * skillLeveler.getXp(player, ArcherySource.valueOf(entity.getType().toString())));
                            event.setDroppedExp((int) UNCOMMON_XP * xp);

                            for (int i = 0; i < UNCOMMON_DROP; i++) {
                                for (int k = 0; k < droppedItems.size(); k++) {
                                    newDrops.add(droppedItems.get(k));
                                }
                            }

                            break;
                        case 2:
                            AureliumAPI.addXp(player, skill, RARE_XP * skillLeveler.getXp(player, ArcherySource.valueOf(entity.getType().toString())));
                            event.setDroppedExp((int) RARE_XP * xp);

                            for (int i = 0; i < RARE_DROP; i++) {
                                for (int k = 0; k < droppedItems.size(); k++) {
                                    newDrops.add(droppedItems.get(k));
                                }
                            }

                            break;
                        case 3:
                            AureliumAPI.addXp(player, skill, EPIC_XP * skillLeveler.getXp(player, ArcherySource.valueOf(entity.getType().toString())));
                            event.setDroppedExp((int) EPIC_XP * xp);

                            for (int i = 0; i < EPIC_DROP; i++) {
                                for (int k = 0; k < droppedItems.size(); k++) {
                                    newDrops.add(droppedItems.get(k));
                                }
                            }

                            break;
                        case 4:
                            AureliumAPI.addXp(player, skill, LEGEND_XP * skillLeveler.getXp(player, ArcherySource.valueOf(entity.getType().toString())));
                            event.setDroppedExp((int) LEGEND_XP * xp);

                            for (int i = 0; i < LEGEND_DROP; i++) {
                                for (int k = 0; k < droppedItems.size(); k++) {
                                    newDrops.add(droppedItems.get(k));
                                }
                            }

                            break;
                        default:
                            break;
                    }
                } else if (skill == Skills.FIGHTING) {
                    switch (rarity) {
                        case 1:
                            AureliumAPI.addXp(player, skill, UNCOMMON_XP * skillLeveler.getXp(player, FightingSource.valueOf(entity.getType().toString())));
                            event.setDroppedExp((int) UNCOMMON_XP * xp);

                            for (int i = 0; i < UNCOMMON_DROP; i++) {
                                for (int k = 0; k < droppedItems.size(); k++) {
                                    newDrops.add(droppedItems.get(k));
                                }
                            }

                            break;
                        case 2:
                            AureliumAPI.addXp(player, skill, RARE_XP * skillLeveler.getXp(player, FightingSource.valueOf(entity.getType().toString())));
                            event.setDroppedExp((int) RARE_XP * xp);

                            for (int i = 0; i < RARE_DROP; i++) {
                                for (int k = 0; k < droppedItems.size(); k++) {
                                    newDrops.add(droppedItems.get(k));
                                }
                            }

                            break;
                        case 3:
                            AureliumAPI.addXp(player, skill, EPIC_XP * skillLeveler.getXp(player, FightingSource.valueOf(entity.getType().toString())));
                            event.setDroppedExp((int) EPIC_XP * xp);

                            for (int i = 0; i < EPIC_DROP; i++) {
                                for (int k = 0; k < droppedItems.size(); k++) {
                                    newDrops.add(droppedItems.get(k));
                                }
                            }

                            break;
                        case 4:
                            AureliumAPI.addXp(player, skill, LEGEND_XP * skillLeveler.getXp(player, FightingSource.valueOf(entity.getType().toString())));
                            event.setDroppedExp((int) LEGEND_XP * xp);

                            for (int i = 0; i < LEGEND_DROP; i++) {
                                for (int k = 0; k < droppedItems.size(); k++) {
                                    newDrops.add(droppedItems.get(k));
                                }
                            }

                            break;
                        default:
                            break;
                    }
                }
            }
        }
        for (int i = 0; i < newDrops.size(); i++) {
            event.getEntity().getWorld().dropItem(entity.getLocation(), newDrops.get(i));
        }
    }

}
