package com.gmail.willramanand.RamMMO.items.recipe;

import com.gmail.willramanand.RamMMO.RamMMO;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapelessRecipe;

public class RecipeManager {

    public static void createRecipes(RamMMO plugin) {

        for (Recipe recipe : Recipe.values()) {
            ShapelessRecipe newRecipe = new ShapelessRecipe(new NamespacedKey(plugin, recipe.name().toLowerCase()), recipe.getResult());

            for (int i = 0; i < recipe.getItems().length; i++) {
                newRecipe.addIngredient(recipe.getAmounts()[i], recipe.getItems()[i]);
            }
            Bukkit.addRecipe(newRecipe);
        }
    }
}
