package com.github.originsplus.power;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import net.minecraft.entity.LivingEntity;

public class BlockPlayerSleep extends Power {

	boolean countPlayerTowardsSleepGoal;
	
	public BlockPlayerSleep(PowerType<?> type, LivingEntity player, boolean countPlayerTowardsSleepGoal) {
		super(type, player);
		this.countPlayerTowardsSleepGoal = countPlayerTowardsSleepGoal;
	}
	
	public boolean shouldCountPlayerTowardsSleepGoal() {
		return countPlayerTowardsSleepGoal;
	}

}
