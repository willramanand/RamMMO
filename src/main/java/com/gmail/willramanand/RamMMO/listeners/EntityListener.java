package com.gmail.willramanand.RamMMO.listeners;

import com.gmail.willramanand.RamMMO.RamMMO;
import com.gmail.willramanand.RamMMO.items.Item;
import com.gmail.willramanand.RamMMO.items.ItemManager;
import com.gmail.willramanand.RamMMO.mobs.MobTier;
import com.gmail.willramanand.RamMMO.utils.ColorUtils;
import com.gmail.willramanand.RamSkills.RamSkills;
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
    private Economy econ;

    public EntityListener(RamMMO plugin) {
        this.plugin = plugin;
        econ = RamMMO.getEconomy();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void addModifier(CreatureSpawnEvent event) {
        LivingEntity entity = event.getEntity();

        int modifier = plugin.getDifficultyUtils().getDifficultyModifier();
        if (modifier <= 0) return;

        if (!(entity instanceof Monster) && !(entity instanceof Boss)) return;
        if (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.NETHER_PORTAL) return;

        AttributeModifier multMod = new AttributeModifier("difficulty_multiplier", modifier, AttributeModifier.Operation.MULTIPLY_SCALAR_1);
        AttributeModifier addMod = new AttributeModifier("difficulty_add", modifier, AttributeModifier.Operation.ADD_NUMBER);

        if (!(entity instanceof Boss)) {
            entity.getAttribute(Attribute.GENERIC_ARMOR).addModifier(addMod);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(multMod);
        } else {
            double health = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
            double armor = entity.getAttribute(Attribute.GENERIC_ARMOR).getBaseValue();
            double damage = entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getBaseValue();

            entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health * modifier);
            entity.setHealth(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
            entity.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(armor + modifier);
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(damage * modifier);
            entity.setCustomName(entity.getName() + plugin.getDifficultyUtils().getBossStars());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onSpawnConvert(CreatureSpawnEvent event) {
        Entity entity = event.getEntity();
        Random rand = new Random();

        if (!(entity instanceof Monster) || entity instanceof ZombieVillager || entity instanceof Boss) {
            return;
        }

        if (!(entity.getPersistentDataContainer().has(new NamespacedKey(plugin, "Rarity"), PersistentDataType.INTEGER))) {

            MobTier mobTier;
            int randomInt = rand.nextInt(1001);

            if (randomInt >= 850 && randomInt < 950) {
                mobTier = MobTier.UNCOMMON;
            } else if (randomInt >= 950 && randomInt < 980) {
                mobTier = MobTier.RARE;
            } else if (randomInt >= 980 && randomInt < 999) {
                mobTier = MobTier.EPIC;
            } else if (randomInt >= 999) {
                mobTier = MobTier.LEGENDARY;
            } else {
                mobTier = MobTier.COMMON;
            }
            convertMob(entity, mobTier);
        }
    }

    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        int xp = event.getDroppedExp();
        int modifier = 1 + plugin.getDifficultyUtils().getDifficultyModifier();
        List<ItemStack> droppedItems = event.getDrops();

        if (event.getEntity().getKiller() == null || !(plugin.isVaultActive()) || !(econ.hasAccount(event.getEntity().getKiller()))) {
            return;
        }

        Player player = event.getEntity().getKiller();

        if (!(entity instanceof Monster) || entity instanceof ZombieVillager) {
            return;
        }


        if (entity instanceof Boss) {
            econ.depositPlayer(player, BOSS_MONEY);
        }

        if (entity.getPersistentDataContainer().has(new NamespacedKey(plugin, "Rarity"), PersistentDataType.INTEGER)) {
            int rarity = entity.getPersistentDataContainer().get(new NamespacedKey(plugin, "Rarity"), PersistentDataType.INTEGER);

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

            money *= modifier;
            xpMult *= modifier;
            dropMult *= modifier;

            econ.depositPlayer(player, money);
            RamSkills.getInstance().getLeveler().addXp(player, skill, xpMult * skillLeveler.getXp(player, source));
            event.setDroppedExp((int) xpMult * xp);
            for (ItemStack item : droppedItems) {
                for (int i = 0; i < dropMult; i++) {
                    if (!(EnchantmentTarget.ARMOR.includes(item)) && !(EnchantmentTarget.TOOL.includes(item))
                            && !(EnchantmentTarget.BOW.includes(item)) && !(EnchantmentTarget.WEAPON.includes(item)))
                        event.getEntity().getWorld().dropItem(entity.getLocation(), item);
                    ;
                }
            }
        } else {
            econ.depositPlayer(player, COMMON_MONEY * modifier);
            event.setDroppedExp(modifier * xp);
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


        en.getPersistentDataContainer().set(new NamespacedKey(plugin, "Rarity"), PersistentDataType.INTEGER, mobTier.getMetaValue());
        en.getAttribute(Attribute.GENERIC_MAX_HEALTH).addModifier(healthMod);
        en.getAttribute(Attribute.GENERIC_ARMOR).addModifier(armorMod);
        en.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).addModifier(attackMod);
        en.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(baseMoveSpeed * mobTier.getSpeedMult());
        en.setHealth(en.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        en.setCustomName(ColorUtils.colorMessage(mobTier.getPrefix() + en.getName()));
    }


        @EventHandler
        public void onDragonDeath (EntityDeathEvent event){
            if (!(event.getEntity() instanceof EnderDragon)) {
                return;
            }

            plugin.getDifficultyUtils().addDefeat();

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
