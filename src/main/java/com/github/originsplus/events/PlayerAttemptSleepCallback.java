package com.github.originsplus.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;

public interface PlayerAttemptSleepCallback {
	Event<PlayerAttemptSleepCallback> EVENT = EventFactory.createArrayBacked(PlayerAttemptSleepCallback.class,
			(listeners) -> (player, pos) -> {
				for(PlayerAttemptSleepCallback listener : listeners) {
					ActionResult result = listener.onTrySleep(player, pos);
					
					if(result != ActionResult.PASS) {
						return result;
					}
				}
				
				return ActionResult.PASS;
			});

	ActionResult onTrySleep(PlayerEntity player, BlockPos pos);
}
