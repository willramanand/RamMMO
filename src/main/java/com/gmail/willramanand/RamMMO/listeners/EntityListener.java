package com.gmail.willramanand.RamMMO.listeners;

import com.gmail.willramanand.RamMMO.RamMMO;
import com.gmail.willramanand.RamMMO.item.Item;
import com.gmail.willramanand.RamMMO.item.ItemManager;
import com.gmail.willramanand.RamMMO.mobs.MobTier;
import com.gmail.willramanand.RamMMO.player.MMOPlayer;
import com.gmail.willramanand.RamMMO.utils.BossUtils;
import com.gmail.willramanand.RamMMO.utils.ColorUtils;
import com.gmail.willramanand.RamMMO.utils.DataUtils;
import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.api.RamSkillsAPI;
import com.gmail.willramanand.RamSkills.leveler.SkillLeveler;
import com.gmail.willramanand.RamSkills.skills.Skill;
import com.gmail.willramanand.RamSkills.skills.Skills;
import com.gmail.willramanand.RamSkills.skills.combat.CombatLeveler;
import com.gmail.willramanand.RamSkills.skills.combat.CombatSource;
import com.gmail.willramanand.RamSkills.source.Source;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;
import java.util.Random;

public class EntityListener implements Listener {

    private final double COMMON_MONEY = 0.25;

    private final double UNCOMMON_XP = 2.5;
    private final int UNCOMMON_DROP = 2;
    private final double UNCOMMON_MONEY = 7.50;

    private final double RARE_XP = 5;
    private final int RARE_DROP = 4;
    private final double RARE_MONEY = 10.25;

    private final double EPIC_XP = 15;
    private final int EPIC_DROP = 8;
    private final double EPIC_MONEY = 50.0;

    private final double LEGEND_XP = 25;
    private final int LEGEND_DROP = 16;
    private final double LEGEND_MONEY = 250.0;

    private final double BOSS_MONEY = 25000.0;

    private final RamMMO plugin;
    private final Economy econ;

    public EntityListener(RamMMO plugin) {
        this.plugin = plugin;
        econ = RamMMO.getEconomy();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onSpawnConvert(CreatureSpawnEvent event) {
        Entity entity = event.getEntity();
        Random rand = new Random();

        if (!(entity instanceof Monster) || entity instanceof ZombieVillager || entity instanceof Boss || BossUtils.isBoss((LivingEntity) entity)) return;

        if (!(DataUtils.has(entity, "Rarity"))) {

            MobTier mobTier;
            int randomInt = rand.nextInt(1001);

            if (randomInt >= 850 && randomInt < 950) {
                mobTier = MobTier.UNCOMMON;
            } else if (randomInt >= 950 && randomInt < 980) {
                mobTier = MobTier.RARE;
            } else if (randomInt >= 980 && randomInt < 995) {
                mobTier = MobTier.EPIC;
            } else if (randomInt >= 995) {
                mobTier = MobTier.LEGENDARY;
            } else {
                mobTier = MobTier.COMMON;
            }
            convertMob(entity, mobTier);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        int xp = event.getDroppedExp();
        List<ItemStack> droppedItems = event.getDrops();

        if (event.getEntity().getKiller() == null) {
            return;
        }

        Player player = event.getEntity().getKiller();
        MMOPlayer mmoPlayer = plugin.getPlayerManager().getPlayerData(player);
        int modifier = 1 + mmoPlayer.getPersonalDifficulty();


        if (!(plugin.isVaultActive()) || !(econ.hasAccount(event.getEntity().getKiller()))) {
            return;
        }

        if (!(entity instanceof Monster) && !(entity instanceof Boss)) return;

        if (entity.getPersistentDataContainer().has(new NamespacedKey(plugin, "Rarity"), PersistentDataType.INTEGER)) {
            int rarity = DataUtils.get(entity, "Rarity", PersistentDataType.INTEGER);

            Skill skill = Skills.COMBAT;
            SkillLeveler skillLeveler = new CombatLeveler(RamSkills.getInstance());
            Source source = CombatSource.valueOf(entity.getType().toString());

            double money = 0.0;
            double xpMult = 1.0;
            double dropMult = 0.0;

            switch (rarity) {
                case 1:
                    money = UNCOMMON_MONEY;
                    xpMult = UNCOMMON_XP;
                    dropMult = UNCOMMON_DROP;
                    break;
                case 2:
                    money = RARE_MONEY;
                    xpMult = RARE_XP;
                    dropMult = RARE_DROP;
                    break;
                case 3:
                    money = EPIC_MONEY;
                    xpMult = EPIC_XP;
                    dropMult = EPIC_DROP;
                    break;
                case 4:
                    money = LEGEND_MONEY;
                    xpMult = LEGEND_XP;
                    dropMult = LEGEND_DROP;
                    break;
            }

            money *= modifier;
            xpMult *= modifier;
            dropMult *= modifier;

            econ.depositPlayer(player, money);
            RamSkillsAPI.addXp(player, skill, xpMult * skillLeveler.getXp(player, source));
            event.setDroppedExp((int) xpMult * xp);
            for (ItemStack item : droppedItems) {
                for (int i = 0; i < dropMult; i++) {
                    if (!(EnchantmentTarget.ARMOR.includes(item)) && !(EnchantmentTarget.TOOL.includes(item)) && !(EnchantmentTarget.BOW.includes(item)) && !(EnchantmentTarget.WEAPON.includes(item)))
                        event.getEntity().getWorld().dropItem(entity.getLocation(), item);
                }
            }
        } else {
            if (entity instanceof Boss) {
                econ.depositPlayer(player, BOSS_MONEY * modifier);
            } else {
                econ.depositPlayer(player, COMMON_MONEY * modifier);
            }
            if (mmoPlayer.getPersonalDifficulty() > 0) {
                for (ItemStack item : droppedItems) {
                    for (int i = 0; i < modifier; i++) {
                        if (!(EnchantmentTarget.ARMOR.includes(item)) && !(EnchantmentTarget.TOOL.includes(item)) && !(EnchantmentTarget.BOW.includes(item)) && !(EnchantmentTarget.WEAPON.includes(item)))
                            event.getEntity().getWorld().dropItem(entity.getLocation(), item);
                    }
                }
                event.setDroppedExp(modifier * xp);
            }
        }
    }

    private void convertMob(Entity entity, MobTier mobTier) {
        if (mobTier == MobTier.COMMON) return;

        LivingEntity en = (LivingEntity) entity;

        // Base Values
        double baseMoveSpeed = en.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getValue();

        AttributeModifier healthMod = new AttributeModifier("tier_health", mobTier.getHealthMult(), AttributeModifier.Operation.MULTIPLY_SCALAR_1);
        AttributeModifier armorMod = new AttributeModifier("tier_armor", mobTier.getArmor(), AttributeModifier.Operation.ADD_NUMBER);
        AttributeModifier attackMod = new AttributeModifier("tier_damage", mobTier.getDamageMult(), AttributeModifier.Operation.MULTIPLY_SCALAR_1);


        DataUtils.set(en, "Rarity", PersistentDataType.INTEGER, mobTier.getMetaValue());
        en.getAttribute(Attribute.GENERIC_MAX_HEALTH).addModifier(healthMod);
        en.getAttribute(Attribute.GENERIC_ARMOR).addModifier(armorMod);
        en.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(attackMod);
        en.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(baseMoveSpeed * mobTier.getSpeedMult());
        en.setHealth(en.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        en.setCustomName(ColorUtils.colorMessage(mobTier.getPrefix() + en.getName()));
    }


    @EventHandler
    public void onDragonDeath(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof EnderDragon)) return;

        if (event.getEntity().getKiller() == null) return;

        Player killer = (Player) event.getEntity().getKiller();
        MMOPlayer mmoPlayer = plugin.getPlayerManager().getPlayerData(killer);

        int newDifficulty = mmoPlayer.getPersonalDifficulty() + 1;
        if (newDifficulty <= 5) {
            mmoPlayer.setPersonalDifficulty(newDifficulty);
            killer.sendMessage(ColorUtils.colorMessage("&6The world grows harder to match your might!"));
        }

        int rng = new Random().nextInt(101);
        int upperBound = 80 - (2 * mmoPlayer.getPersonalDifficulty());
        if (rng >= upperBound) {
            event.getEntity().getKiller().sendMessage(ColorUtils.colorMessage("&eYou have received a &4Fiery Scale&e!"));
            event.getEntity().getKiller().getInventory().addItem(ItemManager.getItem(Item.FIERY_SCALE));
        }
    }

    @EventHandler
    public void onWitherDeath(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof Wither)) return;

        if (event.getEntity().getKiller() == null) return;

        Player killer = (Player) event.getEntity().getKiller();
        MMOPlayer mmoPlayer = plugin.getPlayerManager().getPlayerData(killer);

        int rng = new Random().nextInt(101);
        int upperBound = 100 - (2 * mmoPlayer.getPersonalDifficulty());

        if (rng >= upperBound) {
            event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), ItemManager.getItem(Item.ENCHANTED_NETHERSTAR));
        }
    }

    @EventHandler
    public void changePersonalDamageReceived(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        if (event.getDamager() instanceof Boss) return;

        Player player = (Player) event.getEntity();
        MMOPlayer mmoPlayer = plugin.getPlayerManager().getPlayerData(player);

        if (mmoPlayer.getPersonalDifficulty() == 0) return;

        double newDamage = event.getFinalDamage() * (1 + mmoPlayer.getPersonalDifficulty());
        event.setDamage(newDamage);
    }

    @EventHandler
    public void changePersonalDamageDealt(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) return;
        if (event.getEntity() instanceof Boss) return;

        Player player = (Player) event.getDamager();
        MMOPlayer mmoPlayer = plugin.getPlayerManager().getPlayerData(player);

        if (mmoPlayer.getPersonalDifficulty() == 0) return;

        double damageRed = 1 - (0.05 * (1 + mmoPlayer.getPersonalDifficulty()));
        double newDamage = event.getFinalDamage() * damageRed;
        event.setDamage(newDamage);
    }

    @EventHandler
    public void changePersonalDamageReceivedBoss(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        if (!(event.getDamager() instanceof Boss)) return;

        Player player = (Player) event.getEntity();
        MMOPlayer mmoPlayer = plugin.getPlayerManager().getPlayerData(player);

        if (mmoPlayer.getPersonalDifficulty() == 0) return;

        double newDamage = event.getFinalDamage() * ((1 + mmoPlayer.getPersonalDifficulty()) * 2);
        event.setDamage(newDamage);
    }

    @EventHandler
    public void changePersonalDamageDealtBoss(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) return;
        if (!(event.getEntity() instanceof Boss)) return;

        Player player = (Player) event.getDamager();
        MMOPlayer mmoPlayer = plugin.getPlayerManager().getPlayerData(player);

        if (mmoPlayer.getPersonalDifficulty() == 0) return;

        double damageRed = 1 - (0.1 * (1 + mmoPlayer.getPersonalDifficulty()));
        double newDamage = event.getFinalDamage() * damageRed;
        event.setDamage(newDamage);
    }
}
