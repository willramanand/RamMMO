package com.gmail.willramanand.RamMMO.listeners;

import com.archyx.aureliumskills.api.AureliumAPI;
import com.archyx.aureliumskills.leveler.SkillLeveler;
import com.archyx.aureliumskills.skills.Skill;
import com.archyx.aureliumskills.skills.Skills;
import com.archyx.aureliumskills.skills.archery.ArcheryLeveler;
import com.archyx.aureliumskills.skills.archery.ArcherySource;
import com.archyx.aureliumskills.skills.fighting.FightingLeveler;
import com.archyx.aureliumskills.skills.fighting.FightingSource;
import com.archyx.aureliumskills.source.Source;
import com.gmail.willramanand.RamMMO.RamMMO;
import com.gmail.willramanand.RamMMO.boss.MMOBoss;
import com.gmail.willramanand.RamMMO.items.Item;
import com.gmail.willramanand.RamMMO.items.ItemManager;
import com.gmail.willramanand.RamMMO.mobs.MobConverter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EntityListener implements Listener {

    private final double COMMON_MONEY = 0.05;

    private final double UNCOMMON_XP = 2.5;
    private final int UNCOMMON_DROP = 2;
    private final double UNCOMMON_MONEY = 0.75;

    private final double RARE_XP = 5;
    private final int RARE_DROP = 4;
    private final double RARE_MONEY = 1.25;

    private final double EPIC_XP = 15;
    private final int EPIC_DROP = 8;
    private final double EPIC_MONEY = 5.0;

    private final double LEGEND_XP = 25;
    private final int LEGEND_DROP = 16;
    private final double LEGEND_MONEY = 25.0;

    private final double BOSS_MONEY = 250.0;

    private final RamMMO plugin;
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
        List<MetadataValue> rarityMeta = entity.getMetadata("Rarity");

        if (!(entity instanceof Monster) || entity instanceof ZombieVillager || entity instanceof Boss) {
            return;
        }

        if (rarityMeta.isEmpty()) {

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
    }

    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        int xp = event.getDroppedExp();
        List<ItemStack> droppedItems = event.getDrops();
        List<ItemStack> newDrops = new ArrayList<>();

        if (event.getEntity().getKiller() == null || !(plugin.isVaultActive()) || !(econ.hasAccount(event.getEntity().getKiller()))) {
            return;
        }

        Player player = event.getEntity().getKiller();

        if (!(entity instanceof Monster) || entity instanceof ZombieVillager) {
            return;
        }

        List<MetadataValue> rarityMeta = entity.getMetadata("Rarity");

        if (entity instanceof Boss || entity instanceof MMOBoss) {
            econ.depositPlayer(player, BOSS_MONEY);
        }

        if (!rarityMeta.isEmpty()) {

            for (MetadataValue value : rarityMeta) {
                int rarity = value.asInt();

                ItemStack handItem = player.getInventory().getItemInMainHand();

                Skill skill;
                SkillLeveler skillLeveler;
                Source source;

                if (plugin.getChecker().isBow(handItem)) {
                    skill = Skills.ARCHERY;
                    skillLeveler = new ArcheryLeveler(AureliumAPI.getPlugin());
                    source = ArcherySource.valueOf(entity.getType().toString());
                } else {
                    skill = Skills.FIGHTING;
                    skillLeveler = new FightingLeveler(AureliumAPI.getPlugin());
                    source = FightingSource.valueOf(entity.getType().toString());
                }

                double money = COMMON_MONEY;
                double xpMult = 1.0;
                double dropMult = 0.0;

                switch (rarity) {
                    case 1 -> {
                        money = UNCOMMON_MONEY;
                        xpMult = UNCOMMON_XP;
                        dropMult = UNCOMMON_DROP;
                    }
                    case 2 -> {
                        money = RARE_MONEY;
                        xpMult = RARE_XP;
                        dropMult = RARE_DROP;
                    }
                    case 3 -> {
                        money = EPIC_MONEY;
                        xpMult = EPIC_XP;
                        dropMult = EPIC_DROP;
                    }
                    case 4 -> {
                        money = LEGEND_MONEY;
                        xpMult = LEGEND_XP;
                        dropMult = LEGEND_DROP;
                    }
                }

                econ.depositPlayer(player, money);
                AureliumAPI.addXp(player, skill, xpMult * skillLeveler.getXp(player, source));
                event.setDroppedExp((int) xpMult * xp);
                for (ItemStack item : droppedItems) {
                    for (int i = 0; i < dropMult; i++) {
                        if (!(EnchantmentTarget.ARMOR.includes(item)) || !(EnchantmentTarget.TOOL.includes(item))) event.getEntity().getWorld().dropItem(entity.getLocation(), item);;
                    }
                }
            }
        }
    }


    @EventHandler
    public void onDragonDeath(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof EnderDragon)) {
            return;
        }

        int rng = new Random().nextInt(101);
        if (rng >= 80) {
            event.getDrops().add(ItemManager.getItem(Item.FIERY_SCALE));
        }
    }

    @EventHandler
    public void onPhantomDeath(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof Phantom)) {
            return;
        }

        int rng = new Random().nextInt(101);
        if (rng >= 98) {
            event.getDrops().add(ItemManager.getItem(Item.PHANTOM_PLATE));
        }
    }
}
