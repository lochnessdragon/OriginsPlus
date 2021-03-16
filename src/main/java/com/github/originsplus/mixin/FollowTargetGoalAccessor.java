package com.github.originsplus.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.FollowTargetGoal;

@Mixin(FollowTargetGoal.class)
public interface FollowTargetGoalAccessor {

	@Accessor("targetEntity")
	public LivingEntity getTargetEntity();
	
}
