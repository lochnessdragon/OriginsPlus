package com.github.originsplus.mixin;

import java.util.List;

import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.originsplus.power.ModifyBehavior;
import com.github.originsplus.power.ModifyBehavior.EntityBehavior;

import io.github.apace100.origins.component.OriginComponent;
import net.minecraft.entity.ai.goal.FollowTargetGoal;

@Mixin(FollowTargetGoal.class)
public abstract class FollowTargetGoalMixin extends TrackTargetGoal {

	@Shadow protected LivingEntity targetEntity;

	public FollowTargetGoalMixin(MobEntity mob, boolean checkVisibility) {
		super(mob, checkVisibility);
	}

	@Inject(at = @At(value = "HEAD"), method = "start", cancellable = true)
	public void blockZombieTarget(CallbackInfo info) {
		List<ModifyBehavior> powers = PowerHolderComponent.getPowers(targetEntity, ModifyBehavior.class);
		powers.removeIf((power) -> !power.checkEntity(targetEntity.getType()));
		
		if (!powers.isEmpty()) {
			ModifyBehavior.EntityBehavior behavior = powers.get(0).getDesiredBehavior();
			if(behavior == EntityBehavior.NEUTRAL) {
				//System.out.println("Modifying entity behavior");
				stop();
				info.cancel();
			}
		}
	}

}
