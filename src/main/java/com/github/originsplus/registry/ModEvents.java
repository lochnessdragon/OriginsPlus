package com.github.originsplus.registry;

import java.util.Optional;
import java.util.function.Consumer;

import com.github.originsplus.events.BlockDropCallback;
import com.github.originsplus.events.EntityInteractCallback;
import com.github.originsplus.power.ExplodePower;
import com.github.originsplus.power.ModifyBlockDrop;

import io.github.apace100.origins.component.OriginComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;

public class ModEvents {

	public static void register() {
		// register block broken callback
		BlockDropCallback.EVENT.register((block, state, world, pos, player) -> {
			if (!world.isClient) {
				// System.out.println("Block broken!");
				if (OriginComponent.hasPower(player, ModifyBlockDrop.class)) {
					// System.out.println("Player has the modify block drop power");
					Optional<ModifyBlockDrop> dropOptional = OriginComponent.getPowers(player, ModifyBlockDrop.class)
							.stream().filter((power) -> {
								return power.doesApply(pos);
							}).sorted((power1, power2) -> {
								if (power1.getChance() == power2.getChance()) {
									if (power1.getExtraRolls() == power2.getExtraRolls()) {
										return 0;
									} else {
										return power1.getExtraRolls() < power2.getExtraRolls() ? -1 : 1;
									}
								} else {
									return power1.getChance() < power2.getChance() ? -1 : 1;
								}
							}).findFirst();

					if (dropOptional.isPresent()) {
						// System.out.println("Potentially dropping extra blocks!");
						ModifyBlockDrop drop = dropOptional.get();
						if (drop.getChance() > world.getRandom().nextFloat()) {
							// System.out.println("Dropped extra blocks");
							for (int i = 0; i < drop.getExtraRolls(); i++) {
								block.dropStacks(state, world, pos);
							}
						}
					}
				}
			}

			return ActionResult.PASS;
		});

		// register player interact callback
		EntityInteractCallback.EVENT.register((player, hand, entity) -> {
			if (entity instanceof PlayerEntity) {
				PlayerEntity affectedPlayer = (PlayerEntity) entity;
				if (OriginComponent.hasPower(affectedPlayer, ExplodePower.class)) {
					ExplodePower power = OriginComponent.getPowers(affectedPlayer, ExplodePower.class).get(0);

					if (power.isIgnitable()) {
						ItemStack stack = player.getStackInHand(hand);
						if (stack.getItem() == Items.FLINT_AND_STEEL) {
							if (!player.isCreative()) {
								player.world.playSound(player, player.getX(), player.getY(), player.getZ(),
										SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.PLAYERS, 1.0F,
										player.world.random.nextFloat() * 0.4F + 0.8F);
								if (!player.world.isClient) {
									power.onUse();
									stack.damage(1, (LivingEntity) player, (Consumer) ((playerEntity) -> {
										player.sendToolBreakStatus(hand);
									}));
								}
							}

							return ActionResult.SUCCESS;
						}
					}
				}
			}

			return ActionResult.PASS;
		});
	}

}
