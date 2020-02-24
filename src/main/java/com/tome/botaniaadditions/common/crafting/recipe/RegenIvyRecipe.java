/**
 * This class was created by <ToMe25> based on a class by <Vazkii>.
 * It's distributed as part of the Botania Additions Mod.
 * Get the Source Code in github: https://github.com/ToMe25/Botania-Additions
 *
 * Botania Additions is Open Source and distributed under the
 * MIT License: https://github.com/ToMe25/Botania-Additions/blob/master/LICENSE
 * 
 * File Created @ [Jan 9, 2020, 12:43 (GMT)]
 */
package com.tome.botaniaadditions.common.crafting.recipe;

import javax.annotation.Nonnull;

import com.tome.botaniaadditions.common.item.ItemRegenIvy;
import com.tome.botaniaadditions.common.item.ModItems;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.world.World;
import vazkii.botania.common.core.helper.ItemNBTHelper;

public class RegenIvyRecipe extends SpecialRecipe {
	public static final SpecialRecipeSerializer<RegenIvyRecipe> SERIALIZER = new SpecialRecipeSerializer<>(
			RegenIvyRecipe::new);

	public RegenIvyRecipe(ResourceLocation id) {
		super(id);
	}

	@Override
	public boolean matches(@Nonnull CraftingInventory inv, @Nonnull World world) {
		ItemStack tool = ItemStack.EMPTY;
		boolean foundIvy = false;
		int materialsFound = 0;

		for (int i = 0; i < inv.getSizeInventory(); i++) {
			ItemStack stack = inv.getStackInSlot(i);
			if (!stack.isEmpty()) {
				if (stack.getItem() == ModItems.regenIvy)
					foundIvy = true;
				else if (tool.isEmpty()
						&& !(stack.hasTag() && ItemNBTHelper.getBoolean(stack, ItemRegenIvy.TAG_REGEN, false))
						&& stack.isDamageable())
					tool = stack;
			}
		}
		for (int i = 0; i < inv.getSizeInventory(); i++) {
			ItemStack stack = inv.getStackInSlot(i);
			if (!stack.isEmpty()) {
				if (materialsFound < 3 && !tool.isEmpty() && tool.getItem().getIsRepairable(tool, stack))
					materialsFound++;
			} else if (!stack.isEmpty() && stack != tool && stack.getItem() != ModItems.regenIvy)
				return false;
		}

		return foundIvy && !tool.isEmpty() && materialsFound == 3;
	}

	@Nonnull
	@Override
	public ItemStack getCraftingResult(@Nonnull CraftingInventory inv) {
		ItemStack item = ItemStack.EMPTY;

		for (int i = 0; i < inv.getSizeInventory(); i++) {
			ItemStack stack = inv.getStackInSlot(i);
			if (!stack.isEmpty() && stack.isDamageable())
				item = stack;
		}

		ItemStack copy = item.copy();
		ItemNBTHelper.setBoolean(copy, ItemRegenIvy.TAG_REGEN, true);
		copy.setCount(1);
		return copy;
	}

	@Override
	public boolean canFit(int width, int height) {
		return width * height >= 5;
	}

	@Nonnull
	@Override
	public IRecipeSerializer<?> getSerializer() {
		return SERIALIZER;
	}

}