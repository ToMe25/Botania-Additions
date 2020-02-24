/**
 * This class was created by <ToMe25>. It's distributed as
 * part of the Botania Additions Mod. Get the Source Code in github:
 * https://github.com/ToMe25/Botania-Additions
 *
 * Botania Additions is Open Source and distributed under the
 * MIT License: https://github.com/ToMe25/Botania-Additions/blob/master/LICENSE
 *
 * File Created @ [Feb 24, 2020, 11:14 (GMT)]
 */
package com.tome.botaniaadditions.data;

import com.tome.botaniaadditions.BotaniaAdditions;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = BotaniaAdditions.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

	@SubscribeEvent
	public static void gatherData(GatherDataEvent evt) {
		final DataGenerator generator = evt.getGenerator();
		if (evt.includeServer()) {
			generator.addProvider(new RecipeProvider(generator));
		}
	}

}