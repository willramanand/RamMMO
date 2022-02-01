package com.gmail.willramanand.RamMMO.boss.bosses;

import com.gmail.willramanand.RamMMO.boss.BaseBoss;
import com.gmail.willramanand.RamMMO.boss.Bosses;
import com.gmail.willramanand.RamMMO.item.Item;
import com.gmail.willramanand.RamMMO.item.ItemManager;
import com.gmail.willramanand.RamMMO.utils.BossUtils;
import com.gmail.willramanand.RamMMO.utils.ColorUtils;
import com.gmail.willramanand.RamMMO.utils.DataUtils;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.SkeletonHorse;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class HeadlessHorseman extends BaseBoss {

    public HeadlessHorseman() {
        super(Bosses.HEADLESS_HORSEMAN, Skeleton.class);
    }

    @Override
    public void setAttributes(LivingEntity entity) {
        entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(500);
        entity.setHealth(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
        entity.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(100);
        entity.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(5);
        entity.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(1);
        entity.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).setBaseValue(5);

        Skeleton skeleton = (Skeleton) entity;
        skeleton.setShouldBurnInDay(false);
    }

    @Override
    public void setTags(LivingEntity entity) {
    }

    @Override
    public void setEquipment(LivingEntity entity) {
        EntityEquipment entityEquipment = entity.getEquipment();
        entityEquipment.clear();
        entityEquipment.setHelmet(new ItemStack(Material.CARVED_PUMPKIN), true);
        entityEquipment.setChestplate(new ItemStack(ItemManager.getItem(Item.NETHERFIRE_CHEST)));
        entityEquipment.setLeggings(new ItemStack(ItemManager.getItem(Item.NETHERFIRE_LEGS)));
        entityEquipment.setBoots(new ItemStack(ItemManager.getItem(Item.NETHERFIRE_BOOTS)));

        ItemStack item = new ItemStack(Material.BOW);
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(Enchantment.ARROW_DAMAGE, 10, true);
        meta.addEnchant(Enchantment.ARROW_FIRE, 1, true);
        item.setItemMeta(meta);
        entityEquipment.setItemInMainHand(item);
    }

    @Override
    public void setExtras(LivingEntity entity) {
        entity.getLocation().getWorld().spawn(entity.getLocation(), SkeletonHorse.class, horse -> {
            horse.addPassenger(entity);
            horse.setCustomName(ColorUtils.colorMessage("&4Horse of the Apocalypse"));
            horse.setCustomNameVisible(true);
            horse.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(200);
            horse.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(10);
            horse.setHealth(horse.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
        });
    }
}
