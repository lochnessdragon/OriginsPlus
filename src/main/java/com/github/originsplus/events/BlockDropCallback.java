package com.github.originsplus.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface BlockDropCallback {

	Event<BlockDropCallback> EVENT = EventFactory.createArrayBacked(BlockDropCallback.class,
			(listeners) -> (block, state, world, pos, player) -> {
				for (BlockDropCallback listener : listeners) {
					ActionResult result = listener.onBreak(block, state, world, pos, player);

					if (result != ActionResult.PASS) {
						return result;
					}
				}

				return ActionResult.PASS;
			});
	
	ActionResult onBreak(Block block, BlockState state, World world, BlockPos pos, PlayerEntity player);

}
