package com.gmail.willramanand.RamMMO.mobs;

import com.gmail.willramanand.RamMMO.RamMMO;
import com.gmail.willramanand.RamMMO.utils.ColorUtils;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.metadata.FixedMetadataValue;

public class MobConverter {

    private final RamMMO plugin;

    private final int UNCOMMON_HEALTH_MULT = 2;
    private final int UNCOMMON_ARMOR = 2;
    private final int UNCOMMON_DAMAGE_MULT = 2;
    private final double UNCOMMON_MOVE_SPEED = 1.5;
    private final String UNCOMMON_PREFIX = "&2Uncommon ";

    private final int RARE_HEALTH_MULT = 2;
    private final int RARE_ARMOR = 2;
    private final int RARE_DAMAGE_MULT = 3;
    private final double RARE_MOVE_SPEED = 1.75;
    private final String RARE_PREFIX = "&1Rare ";

    private final int EPIC_HEALTH_MULT = 3;
    private final int EPIC_ARMOR = 2;
    private final int EPIC_DAMAGE_MULT = 6;
    private final double EPIC_MOVE_SPEED = 0.8;
    private final String EPIC_PREFIX = "&5Epic ";

    private final int LEGEND_HEALTH_MULT = 4;
    private final int LEGEND_ARMOR = 4;
    private final int LEGEND_DAMAGE_MULT = 8;
    private final double LEGEND_MOVE_SPEED = 0.8;
    private final String LEGEND_PREFIX = "&6Legendary ";

    public MobConverter(RamMMO plugin) {
        this.plugin = plugin;
    }

    public void convertMob(Entity entity, String rarity) {
        LivingEntity en = (LivingEntity) entity;

        // Base Values
        double baseHealth = en.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
        double baseArmor = en.getAttribute(Attribute.GENERIC_ARMOR).getBaseValue();
        double baseDamage = en.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getBaseValue();
        double baseMoveSpeed = en.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue();

        switch (rarity) {
            case "uncommon" -> {
                en.setMetadata("Rarity", new FixedMetadataValue(plugin, 1));
                en.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(baseHealth * UNCOMMON_HEALTH_MULT);
                en.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(baseArmor + UNCOMMON_ARMOR);
                en.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(baseDamage * UNCOMMON_DAMAGE_MULT);
                en.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(baseMoveSpeed * UNCOMMON_MOVE_SPEED);
                en.setHealth(en.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
                en.setCustomName(ColorUtils.colorMessage(UNCOMMON_PREFIX + en.getName()));
            }
            case "rare" -> {
                en.setMetadata("Rarity", new FixedMetadataValue(plugin, 2));
                en.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(baseHealth * RARE_HEALTH_MULT);
                en.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(baseArmor + RARE_ARMOR);
                en.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(baseDamage * RARE_DAMAGE_MULT);
                en.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(baseMoveSpeed * RARE_MOVE_SPEED);
                en.setHealth(en.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
                en.setCustomName(ColorUtils.colorMessage(RARE_PREFIX + en.getName()));
            }
            case "epic" -> {
                en.setMetadata("Rarity", new FixedMetadataValue(plugin, 3));
                en.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(baseHealth * EPIC_HEALTH_MULT);
                en.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(baseArmor + EPIC_ARMOR);
                en.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(baseDamage * EPIC_DAMAGE_MULT);
                en.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(baseMoveSpeed * EPIC_MOVE_SPEED);
                en.setHealth(en.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
                en.setCustomName(ColorUtils.colorMessage(EPIC_PREFIX + en.getName()));
            }
            case "legend" -> {
                en.setMetadata("Rarity", new FixedMetadataValue(plugin, 4));
                en.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(baseHealth * LEGEND_HEALTH_MULT);
                en.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(baseArmor + LEGEND_ARMOR);
                en.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(baseDamage * LEGEND_DAMAGE_MULT);
                en.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(baseMoveSpeed * LEGEND_MOVE_SPEED);
                en.setHealth(en.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
                en.setCustomName(ColorUtils.colorMessage(LEGEND_PREFIX + en.getName()));
            }
            default -> {
            }
        }
    }
}
