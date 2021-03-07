package com.github.originsplus.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;

public interface BlockDropCallback {

	Event<BlockDropCallback> EVENT = EventFactory.createArrayBacked(BlockDropCallback.class,
			(listeners) -> (block, state, world, pos, stack) -> {
				for (BlockDropCallback listener : listeners) {
					ActionResult result = listener.onStacksDropped(block, state, world, pos, stack);

					if (result != ActionResult.PASS) {
						return result;
					}
				}

				return ActionResult.PASS;
			});

	ActionResult onStacksDropped(AbstractBlock block, BlockState state, ServerWorld world, BlockPos pos, ItemStack stack);

}
