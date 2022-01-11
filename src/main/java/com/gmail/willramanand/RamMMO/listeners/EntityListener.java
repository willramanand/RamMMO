package com.gmail.willramanand.RamMMO.listeners;

import com.gmail.willramanand.RamMMO.RamMMO;
import com.gmail.willramanand.RamMMO.boss.MMOBoss;
import com.gmail.willramanand.RamMMO.items.Item;
import com.gmail.willramanand.RamMMO.items.ItemManager;
import com.gmail.willramanand.RamMMO.mobs.MobConverter;
import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.leveler.SkillLeveler;
import com.gmail.willramanand.RamSkills.skills.Skill;
import com.gmail.willramanand.RamSkills.skills.Skills;
import com.gmail.willramanand.RamSkills.skills.combat.CombatLeveler;
import com.gmail.willramanand.RamSkills.skills.combat.CombatSource;
import com.gmail.willramanand.RamSkills.source.Source;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

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

        if (!(entity instanceof Monster) || entity instanceof ZombieVillager || entity instanceof Boss) {
            return;
        }

        if (!(entity.getPersistentDataContainer().has(new NamespacedKey(plugin, "Rarity"), PersistentDataType.INTEGER))) {

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

        if (event.getEntity().getKiller() == null || !(plugin.isVaultActive()) || !(econ.hasAccount(event.getEntity().getKiller()))) {
            return;
        }

        Player player = event.getEntity().getKiller();

        if (!(entity instanceof Monster) || entity instanceof ZombieVillager) {
            return;
        }


        if (entity instanceof Boss || entity instanceof MMOBoss) {
            econ.depositPlayer(player, BOSS_MONEY);
        }

        if (entity.getPersistentDataContainer().has(new NamespacedKey(plugin, "Rarity"), PersistentDataType.INTEGER)) {
            int rarity = entity.getPersistentDataContainer().get(new NamespacedKey(plugin, "Rarity"), PersistentDataType.INTEGER);

            ItemStack handItem = player.getInventory().getItemInMainHand();

            Skill skill = Skills.COMBAT;
            SkillLeveler skillLeveler = new CombatLeveler(RamSkills.getInstance());
            Source source = CombatSource.valueOf(entity.getType().toString());

            double money = 0.0;
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
            RamSkills.getInstance().getLeveler().addXp(player, skill, xpMult * skillLeveler.getXp(player, source));
            event.setDroppedExp((int) xpMult * xp);
            for (ItemStack item : droppedItems) {
                for (int i = 0; i < dropMult; i++) {
                    if (!(EnchantmentTarget.ARMOR.includes(item)) && !(EnchantmentTarget.TOOL.includes(item)))
                        event.getEntity().getWorld().dropItem(entity.getLocation(), item);
                    ;
                }
            }
        } else {
            econ.depositPlayer(player, COMMON_MONEY);
        }
    }


        @EventHandler
        public void onDragonDeath (EntityDeathEvent event){
            if (!(event.getEntity() instanceof EnderDragon)) {
                return;
            }

            int rng = new Random().nextInt(101);
            if (rng >= 80) {
                event.getDrops().add(ItemManager.getItem(Item.FIERY_SCALE));
            }
        }

        @EventHandler
        public void onPhantomDeath (EntityDeathEvent event){
            if (!(event.getEntity() instanceof Phantom)) {
                return;
            }

            int rng = new Random().nextInt(101);
            if (rng >= 98) {
                event.getDrops().add(ItemManager.getItem(Item.PHANTOM_PLATE));
            }
        }
    }
