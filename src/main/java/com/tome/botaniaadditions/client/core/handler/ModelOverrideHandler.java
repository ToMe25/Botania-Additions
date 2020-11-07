/**
 * This class was created by <ToMe25>. It's distributed as
 * part of the Botania Additions Mod. Get the Source Code in github:
 * https://github.com/ToMe25/Botania-Additions
 *
 * Botania Additions is Open Source and distributed under the
 * MIT License: https://github.com/ToMe25/Botania-Additions/blob/master/LICENSE
 *
 * File Created @ [Sep 30, 2020, 10:16 (GMT)]
 */
package com.tome.botaniaadditions.client.core.handler;

import com.tome.botaniaadditions.BotaniaAdditions;
import com.tome.botaniaadditions.common.core.handler.ConfigHandler;
import com.tome.botaniaadditions.common.item.ModItems;
import com.tome.botaniaadditions.common.item.equipment.tool.terrasteel.ItemTerraShovel;

import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.Items;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import vazkii.botania.common.item.equipment.tool.bow.ItemLivingwoodBow;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = BotaniaAdditions.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModelOverrideHandler {

	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent event) {
		registerPropertyGetters();
	}

	private static void registerPropertyGetter(IItemProvider item, ResourceLocation id,
			IItemPropertyGetter propGetter) {
		ItemModelsProperties.registerProperty(item.asItem(), id, propGetter);
	}

	private static void registerPropertyGetters() {
		if (ConfigHandler.enableTerraHarvester.get()) {
			registerPropertyGetter(ModItems.terraShovel, new ResourceLocation(BotaniaAdditions.MODID, "enabled"),
					(stack, world, entity) -> ItemTerraShovel.isEnabled(stack) ? 1 : 0);
		}

		if (ConfigHandler.enableTerraBow.get()) {
			IItemPropertyGetter pulling = ItemModelsProperties.func_239417_a_(Items.BOW,
					new ResourceLocation("pulling"));
			IItemPropertyGetter pull = (stack, worldIn, entity) -> {
				if (entity == null) {
					return 0.0F;
				} else {
					ItemLivingwoodBow item = ((ItemLivingwoodBow) stack.getItem());
					return entity.getActiveItemStack() != stack ? 0.0F
							: (stack.getUseDuration() - entity.getItemInUseCount()) * item.chargeVelocityMultiplier()
									/ 20.0F;
				}
			};
			registerPropertyGetter(ModItems.terraBow, new ResourceLocation("pulling"), pulling);
			registerPropertyGetter(ModItems.terraBow, new ResourceLocation("pull"), pull);
		}
	}

}
