package com.coffee.corenex.datagen;

import com.coffee.corenex.Corenex;
import com.coffee.corenex.block.ModBlocks;
import com.coffee.corenex.item.Moditems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import org.checkerframework.checker.units.qual.C;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipesProvider extends RecipeProvider implements IConditionBuilder {

    public ModRecipesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }



    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DRAGON_SCALE_BLOCK)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', Moditems.DRAGON_SCALE)
                .unlockedBy(getHasName(Moditems.DRAGON_SCALE), has(Moditems.DRAGON_SCALE))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Moditems.DRAGON_SCALE, 9)
                .requires(ModBlocks.DRAGON_SCALE_BLOCK)
                .unlockedBy(getHasName(ModBlocks.DRAGON_SCALE_BLOCK), has(ModBlocks.DRAGON_SCALE_BLOCK))
                .save(recipeOutput);
        //如果有原版物品， .save(recipeOutput, TutorialMod.MOD_ID + ":" + "sugar_from_beetroot");




    }
    protected static void oreSmelting(
            RecipeOutput recipeOutput, List<ItemLike> ingredients, RecipeCategory category, ItemLike result, float experience, int cookingTime, String group
    ) {
        oreCooking(
                recipeOutput,
                RecipeSerializer.SMELTING_RECIPE,
                SmeltingRecipe::new,
                ingredients,
                category,
                result,
                experience,
                cookingTime,
                group,
                "_from_smelting"
        );
    }

    protected static void oreBlasting(
            RecipeOutput recipeOutput, List<ItemLike> ingredients, RecipeCategory category, ItemLike result, float experience, int cookingTime, String group
    ) {
        oreCooking(
                recipeOutput,
                RecipeSerializer.BLASTING_RECIPE,
                BlastingRecipe::new,
                ingredients,
                category,
                result,
                experience,
                cookingTime,
                group,
                "_from_blasting"
        );
    }

    protected static <T extends AbstractCookingRecipe> void oreCooking(
            RecipeOutput recipeOutput,
            RecipeSerializer<T> serializer,
            AbstractCookingRecipe.Factory<T> recipeFactory,
            List<ItemLike> ingredients,
            RecipeCategory category,
            ItemLike result,
            float experience,
            int cookingTime,
            String group,
            String suffix
    ) {
        for (ItemLike itemlike : ingredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), category, result, experience, cookingTime, serializer, recipeFactory)
                    .group(group)
                    .unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(recipeOutput, Corenex.MOD_ID + ":" + getItemName(result) + suffix + "_" + getItemName(itemlike));
        }
    }
}