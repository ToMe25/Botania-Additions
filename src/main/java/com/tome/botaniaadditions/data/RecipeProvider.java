/**
 * This class was created by <ToMe25>. It's distributed as
 * part of the Botania Additions Mod. Get the Source Code in github:
 * https://github.com/ToMe25/Botania-Additions
 *
 * Botania Additions is Open Source and distributed under the
 * MIT License: https://github.com/ToMe25/Botania-Additions/blob/master/LICENSE
 *
 * File Created @ [Feb 24, 2020, 11:21 (GMT)]
 */
package com.tome.botaniaadditions.data;

import java.util.function.Consumer;

import com.tome.botaniaadditions.BotaniaAdditions;
import com.tome.botaniaadditions.common.crafting.recipe.RegenIvyRecipe;
import com.tome.botaniaadditions.common.item.ModItems;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.data.CustomRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.ConditionalAdvancement;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.ItemExistsCondition;
import vazkii.botania.common.lib.ModTags;

public class RecipeProvider extends net.minecraft.data.RecipeProvider {

	public RecipeProvider(DataGenerator generatorIn) {
		super(generatorIn);
	}

	@Override
	protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
		specialRecipe(consumer, RegenIvyRecipe.SERIALIZER);
		registerStandartRecipes(consumer);
	}

	private void registerStandartRecipes(Consumer<IFinishedRecipe> consumer) {
		resultExists(consumer,
				ShapelessRecipeBuilder.shapelessRecipe(ModItems.regenIvy).addIngredient(ModTags.Items.INGOTS_ELEMENTIUM)
						.addIngredient(Items.VINE).addIngredient(vazkii.botania.common.item.ModItems.lifeEssence)
						.addCriterion("has_item", hasItem(vazkii.botania.common.item.ModItems.lifeEssence)),
				hasItem(vazkii.botania.common.item.ModItems.lifeEssence), ModItems.regenIvy);
		resultExists(consumer,
				ShapedRecipeBuilder.shapedRecipe(ModItems.terraShovel).key('T', ModTags.Items.INGOTS_TERRASTEEL)
						.key('L', vazkii.botania.common.item.ModItems.livingwoodTwig).patternLine(" T ")
						.patternLine("TLT").patternLine(" L ")
						.addCriterion("has_item", hasItem(ModTags.Items.INGOTS_TERRASTEEL)),
				hasItem(ModTags.Items.INGOTS_TERRASTEEL), ModItems.terraShovel);
		resultExists(consumer,
				ShapedRecipeBuilder.shapedRecipe(ModItems.terraBow).key('T', ModTags.Items.INGOTS_TERRASTEEL)
						.key('L', vazkii.botania.common.item.ModItems.livingwoodTwig)
						.key('S', vazkii.botania.common.item.ModItems.manaString).patternLine(" LS").patternLine("T S")
						.patternLine(" LS").addCriterion("has_item", hasItem(ModTags.Items.INGOTS_TERRASTEEL)),
				hasItem(ModTags.Items.INGOTS_TERRASTEEL), ModItems.terraBow);
	}

	private void specialRecipe(Consumer<IFinishedRecipe> consumer, SpecialRecipeSerializer<?> serializer) {
		CustomRecipeBuilder.func_218656_a(serializer).build(consumer,
				BotaniaAdditions.MODID + ":dynamic/" + serializer.getRegistryName().getPath());
	}

	private void resultExists(Consumer<IFinishedRecipe> consumer, ShapelessRecipeBuilder recipeBuilder,
			ICriterionInstance trigger, IItemProvider result) {
		ConditionalRecipe.builder().addCondition(new ItemExistsCondition(result.asItem().getRegistryName()))
				.addRecipe(recipeBuilder::build)
				.setAdvancement(
						new ResourceLocation(BotaniaAdditions.MODID,
								"recipes/botaniaadditions/" + result.asItem().getRegistryName().getPath()),
						ConditionalAdvancement.builder()
								.addCondition(new ItemExistsCondition(result.asItem().getRegistryName().toString()))
								.addAdvancement(Advancement.Builder.builder()
										.withParentId(new ResourceLocation("recipes/root"))
										.withRewards(
												AdvancementRewards.Builder.recipe(result.asItem().getRegistryName()))
										.withCriterion("has_item", trigger)
										.withCriterion("has_the_recipe",
												new RecipeUnlockedTrigger.Instance(result.asItem().getRegistryName()))
										.withRequirementsStrategy(IRequirementsStrategy.OR)))
				.build(consumer, result.asItem().getRegistryName());
	}

	private void resultExists(Consumer<IFinishedRecipe> consumer, ShapedRecipeBuilder recipeBuilder,
			ICriterionInstance trigger, IItemProvider result) {
		ConditionalRecipe.builder().addCondition(new ItemExistsCondition(result.asItem().getRegistryName()))
				.addRecipe(recipeBuilder::build)
				.setAdvancement(
						new ResourceLocation(BotaniaAdditions.MODID,
								"recipes/botaniaadditions/" + result.asItem().getRegistryName().getPath()),
						ConditionalAdvancement.builder()
								.addCondition(new ItemExistsCondition(result.asItem().getRegistryName().toString()))
								.addAdvancement(Advancement.Builder.builder()
										.withParentId(new ResourceLocation("recipes/root"))
										.withRewards(
												AdvancementRewards.Builder.recipe(result.asItem().getRegistryName()))
										.withCriterion("has_item", trigger)
										.withCriterion("has_the_recipe",
												new RecipeUnlockedTrigger.Instance(result.asItem().getRegistryName()))
										.withRequirementsStrategy(IRequirementsStrategy.OR)))
				.build(consumer, result.asItem().getRegistryName());
	}

	@Override
	public String getName() {
		return "Botania Additions crafting recipes";
	}

}
