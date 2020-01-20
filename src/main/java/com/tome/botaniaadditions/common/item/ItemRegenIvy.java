/**
 * This class was created by <ToMe25> based on a class by <Vazkii>.
 * It's distributed as part of the Botania Additions Mod.
 * Get the Source Code in github: https://github.com/ToMe25/Botania-Additions
 *
 * Botania Additions is Open Source and distributed under the
 * MIT License: https://github.com/ToMe25/Botania-Additions/blob/master/LICENSE
 * 
 * File Created @ [Jan 9, 2020, 12:38 (GMT)]
 */
package com.tome.botaniaadditions.common.item;

import com.tome.botaniaadditions.BotaniaAdditions;

import net.minecraft.item.ItemStack;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.core.helper.ItemNBTHelper;
import vazkii.botania.common.item.ItemMod;

@Mod.EventBusSubscriber(modid = BotaniaAdditions.MODID)
public class ItemRegenIvy extends ItemMod {

	public static final String TAG_REGEN = "botaniaadditions_regenIvy";
	private static final int MANA_PER_DAMAGE = 200;

	public ItemRegenIvy(Properties props) {
		super(props);
	}

	@SubscribeEvent
	public static void onTick(PlayerTickEvent event) {
		if (event.phase == Phase.END && !event.player.world.isRemote)
			for (int i = 0; i < event.player.inventory.getSizeInventory(); i++) {
				ItemStack stack = event.player.inventory.getStackInSlot(i);
				if (!stack.isEmpty() && stack.hasTag() && ItemNBTHelper.getBoolean(stack, TAG_REGEN, false)
						&& stack.getDamage() > 0
						&& ManaItemHandler.requestManaExact(stack, event.player, MANA_PER_DAMAGE, true))
					stack.setDamage(stack.getDamage() - 1);
			}
	}
} 