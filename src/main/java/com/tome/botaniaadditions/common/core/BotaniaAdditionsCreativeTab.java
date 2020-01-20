/**
 * This class was created by <ToMe25>. It's distributed as
 * part of the Botania Additions Mod. Get the Source Code in github:
 * https://github.com/ToMe25/Botania-Additions
 *
 * Botania Additions is Open Source and distributed under the
 * MIT License: https://github.com/ToMe25/Botania-Additions/blob/master/LICENSE
 *
 * File Created @ [Dec 30, 2019, 9:51 (GMT)]
 */
package com.tome.botaniaadditions.common.core;

import com.tome.botaniaadditions.BotaniaAdditions;
import com.tome.botaniaadditions.common.item.ModItems;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class BotaniaAdditionsCreativeTab extends ItemGroup {

	public static final BotaniaAdditionsCreativeTab INSTANCE = new BotaniaAdditionsCreativeTab();

	public BotaniaAdditionsCreativeTab() {
		super(BotaniaAdditions.MODID);
	}

	@Override
	public ItemStack createIcon() {
		return new ItemStack(ModItems.terraShovel);
	}

}
