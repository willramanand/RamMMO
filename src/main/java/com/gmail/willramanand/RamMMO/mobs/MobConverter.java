package com.gmail.willramanand.RamMMO.mobs;

import com.gmail.willramanand.RamMMO.RamMMO;
import com.gmail.willramanand.RamMMO.utils.ColorUtils;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.persistence.PersistentDataType;

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

        if (rarity.equalsIgnoreCase("common")) return;

        LivingEntity en = (LivingEntity) entity;

        // Base Values
        double baseHealth = en.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
        double baseArmor = en.getAttribute(Attribute.GENERIC_ARMOR).getBaseValue();
        double baseDamage = en.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getBaseValue();
        double baseMoveSpeed = en.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue();

        // Multipliers
        int metaValue = 0;
        double healthMult = 1.0;
        double armor = 0;
        double damageMult = 1.0;
        double moveMult = 1.0;
        String prefix = "";

        switch (rarity) {
            case "uncommon" -> {
                metaValue = 1;
                healthMult = UNCOMMON_HEALTH_MULT;
                armor = UNCOMMON_ARMOR;
                damageMult = UNCOMMON_DAMAGE_MULT;
                moveMult = UNCOMMON_MOVE_SPEED;
                prefix = UNCOMMON_PREFIX;
            }
            case "rare" -> {
                metaValue = 2;
                healthMult = RARE_HEALTH_MULT;
                armor = RARE_ARMOR;
                damageMult = RARE_DAMAGE_MULT;
                moveMult = RARE_MOVE_SPEED;
                prefix = RARE_PREFIX;
            }
            case "epic" -> {
                metaValue = 3;
                healthMult = EPIC_HEALTH_MULT;
                armor = EPIC_ARMOR;
                damageMult = EPIC_DAMAGE_MULT;
                moveMult = EPIC_MOVE_SPEED;
                prefix = EPIC_PREFIX;
            }
            case "legend" -> {
                metaValue = 4;
                healthMult = LEGEND_HEALTH_MULT;
                armor = LEGEND_ARMOR;
                damageMult = LEGEND_DAMAGE_MULT;
                moveMult = LEGEND_MOVE_SPEED;
                prefix = LEGEND_PREFIX;
            }
        }
        en.getPersistentDataContainer().set(new NamespacedKey(plugin, "Rarity"), PersistentDataType.INTEGER, metaValue);
        en.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(baseHealth * healthMult);
        en.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(baseArmor + armor);
        en.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(baseDamage * damageMult);
        en.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(baseMoveSpeed * moveMult);
        en.setHealth(en.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
        en.setCustomName(ColorUtils.colorMessage(prefix + en.getName()));
    }
}
