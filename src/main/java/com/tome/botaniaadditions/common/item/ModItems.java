/**
 * This class was created by <ToMe25>. It's distributed as
 * part of the Botania Additions Mod. Get the Source Code in github:
 * https://github.com/ToMe25/Botania-Additions
 *
 * Botania Additions is Open Source and distributed under the
 * MIT License: https://github.com/ToMe25/Botania-Additions/blob/master/LICENSE
 *
 * File Created @ [Jan 09, 2019, 9:40 (GMT)]
 */
package com.tome.botaniaadditions.common.item;

import com.tome.botaniaadditions.BotaniaAdditions;
import com.tome.botaniaadditions.common.core.BotaniaAdditionsCreativeTab;
import com.tome.botaniaadditions.common.core.handler.ConfigHandler;
import com.tome.botaniaadditions.common.crafting.recipe.RegenIvyRecipe;
import com.tome.botaniaadditions.common.item.equipment.tool.terrasteel.ItemTerraShovel;
import com.tome.botaniaadditions.common.lib.LibItemNames;

import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = BotaniaAdditions.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(BotaniaAdditions.MODID)
public class ModItems {

	@ObjectHolder(LibItemNames.TERRA_SHOVEL)
	public static Item terraShovel;
	@ObjectHolder(LibItemNames.REGEN_IVY)
	public static Item regenIvy;

	public static Item.Properties defaultBuilder() {
		return new Item.Properties().group(BotaniaAdditionsCreativeTab.INSTANCE);
	}

	private static Item.Properties unstackable() {
		return defaultBuilder().maxStackSize(1);
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> evt) {
		IForgeRegistry<Item> r = evt.getRegistry();
		if (ConfigHandler.enableTerraHarvester.get())
			register(r, new ItemTerraShovel(unstackable()), LibItemNames.TERRA_SHOVEL);
		if (ConfigHandler.enableTimelessIvy.get())
			register(r, new ItemRegenIvy(defaultBuilder()), LibItemNames.REGEN_IVY);
	}

	@SubscribeEvent
	public static void registerRecipeSerializers(RegistryEvent.Register<IRecipeSerializer<?>> evt) {
		IForgeRegistry<IRecipeSerializer<?>> r = evt.getRegistry();
		register(r, RegenIvyRecipe.SERIALIZER, "regen_ivy");
	}

	public static <V extends IForgeRegistryEntry<V>> void register(IForgeRegistry<V> reg, IForgeRegistryEntry<V> thing,
			ResourceLocation name) {
		reg.register(thing.setRegistryName(name));
	}

	public static <V extends IForgeRegistryEntry<V>> void register(IForgeRegistry<V> reg, IForgeRegistryEntry<V> thing,
			String name) {
		register(reg, thing, new ResourceLocation(BotaniaAdditions.MODID, name));
	}

}
