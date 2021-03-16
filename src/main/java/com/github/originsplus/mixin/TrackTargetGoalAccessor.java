package com.github.originsplus.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.mob.MobEntity;

@Mixin(TrackTargetGoal.class)
public interface TrackTargetGoalAccessor {

	@Accessor("mob")
	public MobEntity getMob();
	
}
