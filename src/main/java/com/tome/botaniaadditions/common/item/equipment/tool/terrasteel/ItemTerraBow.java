/**
 * This class was created by <ToMe25>. It's distributed as
 * part of the Botania Additions Mod. Get the Source Code in github:
 * https://github.com/ToMe25/Botania-Additions
 *
 * Botania Additions is Open Source and distributed under the
 * MIT License: https://github.com/ToMe25/Botania-Additions/blob/master/LICENSE
 *
 * File Created @ [Jan 24, 2020, 15:22 (GMT)]
 */
package com.tome.botaniaadditions.common.item.equipment.tool.terrasteel;

import javax.annotation.Nonnull;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.item.equipment.tool.bow.ItemLivingwoodBow;

public class ItemTerraBow extends ItemLivingwoodBow {

	private static final int ARROW_COST = 400;
	private static final int ARROW_ROWS = 3;
	private static final int ARROW_COLS = 3;

	public ItemTerraBow(Properties builder) {
		super(builder);
	}

	// [VanillaCopy] super
	@Nonnull
	@Override
	public ActionResult<ItemStack> onItemRightClick(@Nonnull World worldIn, PlayerEntity playerIn,
			@Nonnull Hand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		boolean flag = canFire(itemstack, playerIn); // Botania - custom check

		ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemstack, worldIn,
				playerIn, handIn, flag);
		if (ret != null) {
			return ret;
		}

		if (!playerIn.abilities.isCreativeMode && !flag) {
			return ActionResult.resultFail(itemstack);
		} else {
			playerIn.setActiveHand(handIn);
			return ActionResult.resultConsume(itemstack);
		}
	}

	// [VanillaCopy] super
	@Override
	public void onPlayerStoppedUsing(@Nonnull ItemStack stack, @Nonnull World worldIn, LivingEntity entityLiving,
			int timeLeft) {
		if (entityLiving instanceof PlayerEntity) {
			PlayerEntity playerentity = (PlayerEntity) entityLiving;
			boolean flag = canFire(stack, playerentity); // Botania - custom check
			ItemStack itemstack = playerentity.findAmmo(stack);

			int i = (int) ((getUseDuration(stack) - timeLeft) * chargeVelocityMultiplier()); // Botania - velocity
																								// multiplier
			i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, playerentity, i,
					!itemstack.isEmpty() || flag);
			if (i < 0)
				return;

			if (!itemstack.isEmpty() || flag) {
				if (itemstack.isEmpty()) {
					itemstack = new ItemStack(Items.ARROW);
				}

				float f = getArrowVelocity(i);
				if (!((double) f < 0.1D)) {
					boolean flag1 = playerentity.abilities.isCreativeMode || (itemstack.getItem() instanceof ArrowItem
							&& ((ArrowItem) itemstack.getItem()).isInfinite(itemstack, stack, playerentity));
					if (!worldIn.isRemote) {
						ArrowItem arrowitem = (ArrowItem) (itemstack.getItem() instanceof ArrowItem
								? itemstack.getItem()
								: Items.ARROW);

						// Botania Additions - loop over entity creation
						int row = 0;
						while (row < ARROW_ROWS) {
							int col = 0;
							while (col < ARROW_COLS) {
								float pitchAddition = (row - ARROW_ROWS / 2) * 3F;
								float yawAddition = (col - ARROW_COLS / 2) * 3F;
								AbstractArrowEntity abstractarrowentity = arrowitem.createArrow(worldIn, itemstack,
										playerentity);
								abstractarrowentity = customArrow(abstractarrowentity);
								abstractarrowentity.func_234612_a_(playerentity,
										playerentity.rotationPitch + pitchAddition,
										playerentity.rotationYaw + yawAddition, 0.0F, f * 3.0F, 1.0F);
								if (f == 1.0F) {
									abstractarrowentity.setIsCritical(true);
								}

								int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
								if (j > 0) {
									abstractarrowentity
											.setDamage(abstractarrowentity.getDamage() + (double) j * 0.5D + 0.5D);
								}

								int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);
								if (k > 0) {
									abstractarrowentity.setKnockbackStrength(k);
								}

								if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0) {
									abstractarrowentity.setFire(100);
								}

								// Botania - onFire
								onFire(stack, playerentity, flag1, abstractarrowentity);

								stack.damageItem(1, playerentity, (p_220009_1_) -> {
									p_220009_1_.sendBreakAnimation(playerentity.getActiveHand());
								});
								if (flag1 || playerentity.abilities.isCreativeMode
										&& (itemstack.getItem() == Items.SPECTRAL_ARROW
												|| itemstack.getItem() == Items.TIPPED_ARROW)) {
									abstractarrowentity.pickupStatus = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
								}

								worldIn.addEntity(abstractarrowentity);
								col++;
							}
							row++;
						}
					}

					worldIn.playSound((PlayerEntity) null, playerentity.getPosX(), playerentity.getPosY(),
							playerentity.getPosZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F,
							1.0F / (random.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
					// Botania Additions - The Power of Terra Steal allways creates new arrows.
					playerentity.addStat(Stats.ITEM_USED.get(this));
				}
			}
		}
	}

	@Override
	public float chargeVelocityMultiplier() {
		return 2F;
	}

	private boolean canFire(ItemStack stack, PlayerEntity player) {
		boolean infinity = EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
		return player.abilities.isCreativeMode || ManaItemHandler.instance().requestManaExactForTool(stack, player,
				ARROW_COST / (infinity ? 2 : 1), false);
	}

	private void onFire(ItemStack stack, LivingEntity living, boolean infinity, AbstractArrowEntity arrow) {
		arrow.pickupStatus = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
		if (living instanceof PlayerEntity)
			ManaItemHandler.instance().requestManaExactForTool(stack, (PlayerEntity) living,
					ARROW_COST / (infinity ? 2 : 1), true);
	}
}