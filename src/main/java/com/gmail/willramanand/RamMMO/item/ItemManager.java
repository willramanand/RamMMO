package com.gmail.willramanand.RamMMO.item;

import com.gmail.willramanand.RamMMO.RamMMO;
import com.gmail.willramanand.RamMMO.item.items.BaseItem;
import com.gmail.willramanand.RamMMO.item.items.armors.*;
import com.gmail.willramanand.RamMMO.item.items.materials.*;
import com.gmail.willramanand.RamMMO.item.items.tools.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemManager {

    private static final Map<Item, BaseItem> items = new HashMap<>();
    private static final List<String> extraKeys = new ArrayList<>();

    public static void registerItems() {
        // Special Materials
        items.put(Item.PHANTOM_PLATE, new PhantomPlate());
        items.put(Item.FIERY_SCALE, new FieryScale());

        // Upgraded Materials
        items.put(Item.ENCHANTED_GOLD, new EnchantedGold());
        items.put(Item.ENCHANTED_DIAMOND, new EnchantedDiamond());
        items.put(Item.ENCHANTED_EMERALD, new EnchantedEmerald());
        items.put(Item.ENCHANTED_NETHERITE, new EnchantedNetherite());
        items.put(Item.ENCHANTED_NETHERSTAR, new EnchantedNetherstar());

        items.put(Item.ENCHANTED_GOLD_BLOCK, new EnchantedGoldBlock());
        items.put(Item.ENCHANTED_DIAMOND_BLOCK, new EnchantedDiamondBlock());
        items.put(Item.ENCHANTED_EMERALD_BLOCK, new EnchantedEmeraldBlock());
        items.put(Item.ENCHANTED_NETHERITE_BLOCK, new EnchantedNetheriteBlock());

        // Tools
        items.put(Item.SILK_TOUCHER, new SilkToucher());
        items.put(Item.VULCANS_PICK, new VulcansPick());
        items.put(Item.APOLLOS_BOW, new ApolloBow());
        items.put(Item.FENRIRS_FANG, new FenrirsFang());
        items.put(Item.BUILDERS_WAND, new BuilderWand());
        items.put(Item.CHAACS_AXE, new ChaacsAxe());
        items.put(Item.POSEIDONS_TRIDENT, new PoseidonsTrident());

        // Elytra
        items.put(Item.EMPOWERED_ELYTRA, new EmpoweredElytra());

        // Armor
        items.put(Item.NETHERFIRE_HELM, new NetherfireHelm());
        items.put(Item.NETHERFIRE_CHEST, new NetherfireChest());
        items.put(Item.NETHERFIRE_LEGS, new NetherfireLegs());
        items.put(Item.NETHERFIRE_BOOTS, new NetherfireBoots());

        // Combined items
        items.put(Item.NETHERFIRE_ELYTRA, new NetherfireElytra());

        // Build Recipes
        for (Item item : Item.values()) {
            Bukkit.addRecipe(items.get(item).recipe());
        }
    }

    public static void buildExtraRecipes() {
        List<Recipe> recipeList = new ArrayList<>();
        ShapelessRecipe enchantedToGold = new ShapelessRecipe(new NamespacedKey(RamMMO.getInstance(), "enchantedToGold"), new ItemStack(Material.GOLD_INGOT, 5));
        enchantedToGold.addIngredient(items.get(Item.ENCHANTED_GOLD).get());
        recipeList.add(enchantedToGold);
        extraKeys.add("enchantedToGold");

        ShapelessRecipe enchantedToDiamond = new ShapelessRecipe(new NamespacedKey(RamMMO.getInstance(), "enchantedToDiamond"), new ItemStack(Material.DIAMOND, 5));
        enchantedToDiamond.addIngredient(items.get(Item.ENCHANTED_DIAMOND).get());
        recipeList.add(enchantedToDiamond);
        extraKeys.add("enchantedToDiamond");

        ShapelessRecipe enchantedToEmerald = new ShapelessRecipe(new NamespacedKey(RamMMO.getInstance(), "enchantedToEmerald"), new ItemStack(Material.EMERALD, 5));
        enchantedToEmerald.addIngredient(items.get(Item.ENCHANTED_EMERALD).get());
        recipeList.add(enchantedToEmerald);
        extraKeys.add("enchantedToEmerald");

        ShapelessRecipe enchantedToNether = new ShapelessRecipe(new NamespacedKey(RamMMO.getInstance(), "enchantedToNether"), new ItemStack(Material.NETHERITE_INGOT, 5));
        enchantedToNether.addIngredient(items.get(Item.ENCHANTED_NETHERITE).get());
        recipeList.add(enchantedToNether);
        extraKeys.add("enchantedToNether");

        ShapelessRecipe enchantedToStar = new ShapelessRecipe(new NamespacedKey(RamMMO.getInstance(), "enchantedToStar"), new ItemStack(Material.NETHER_STAR, 5));
        enchantedToStar.addIngredient(items.get(Item.ENCHANTED_NETHERSTAR).get());
        recipeList.add(enchantedToStar);
        extraKeys.add("enchantedToStar");

        ItemStack gold = new ItemStack(items.get(Item.ENCHANTED_GOLD).get());
        gold.setAmount(5);
        ShapelessRecipe enchantedToGoldBlock = new ShapelessRecipe(new NamespacedKey(RamMMO.getInstance(), "enchantedToGoldBlock"), gold);
        enchantedToGoldBlock.addIngredient(items.get(Item.ENCHANTED_GOLD_BLOCK).get());
        recipeList.add(enchantedToGoldBlock);
        extraKeys.add("enchantedToGoldBlock");

        ItemStack diamond = new ItemStack(items.get(Item.ENCHANTED_DIAMOND).get());
        diamond.setAmount(5);
        ShapelessRecipe enchantedToDiamondBlock = new ShapelessRecipe(new NamespacedKey(RamMMO.getInstance(), "enchantedToDiamondBlock"), diamond);
        enchantedToDiamondBlock.addIngredient(items.get(Item.ENCHANTED_DIAMOND_BLOCK).get());
        recipeList.add(enchantedToDiamondBlock);
        extraKeys.add("enchantedToDiamondBlock");

        ItemStack emerald = new ItemStack(items.get(Item.ENCHANTED_EMERALD).get());
        emerald.setAmount(5);
        ShapelessRecipe enchantedToEmeraldBlock = new ShapelessRecipe(new NamespacedKey(RamMMO.getInstance(), "enchantedToEmeraldBlock"), emerald);
        enchantedToEmeraldBlock.addIngredient(items.get(Item.ENCHANTED_EMERALD_BLOCK).get());
        recipeList.add(enchantedToEmeraldBlock);
        extraKeys.add("enchantedToEmeraldBlock");

        ItemStack nether = new ItemStack(items.get(Item.ENCHANTED_NETHERITE).get());
        nether.setAmount(5);
        ShapelessRecipe enchantedToNetherBlock = new ShapelessRecipe(new NamespacedKey(RamMMO.getInstance(), "enchantedToNetherBlock"), nether);
        enchantedToNetherBlock.addIngredient(items.get(Item.ENCHANTED_NETHERITE_BLOCK).get());
        recipeList.add(enchantedToNetherBlock);
        extraKeys.add("enchantedToNetherBlock");

        for (Recipe recipe : recipeList) {
            Bukkit.addRecipe(recipe);
        }
    }

    public static ItemStack getItem(Item item) {
        return items.get(item).get();
    }

    public static Recipe getRecipe(Item item) {
        return items.get(item).recipe();
    }

    public static List<String> getExtraRecipes() { return extraKeys; }
}
