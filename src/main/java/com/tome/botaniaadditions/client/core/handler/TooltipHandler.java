/**
 * This class was created by <ToMe25>. It's distributed as
 * part of the Botania Additions Mod. Get the Source Code in github:
 * https://github.com/ToMe25/Botania-Additions
 *
 * Botania Additions is Open Source and distributed under the
 * MIT License: https://github.com/ToMe25/Botania-Additions/blob/master/LICENSE
 *
 * File Created @ [Jan 14, 2020, 10:13 (GMT)]
 */
package com.tome.botaniaadditions.client.core.handler;

import com.tome.botaniaadditions.BotaniaAdditions;
import com.tome.botaniaadditions.common.item.ItemRegenIvy;

import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import vazkii.botania.common.core.helper.ItemNBTHelper;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = BotaniaAdditions.MODID)
public final class TooltipHandler {

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onTooltipEvent(ItemTooltipEvent event) {
		if(event.getItemStack().hasTag() && ItemNBTHelper.getBoolean(event.getItemStack(), ItemRegenIvy.TAG_REGEN, false))
			event.getToolTip().add(new TranslationTextComponent("botaniaadditions.hasRegenIvy"));
	}

}