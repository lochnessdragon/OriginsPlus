package com.github.originsplus.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.originsplus.power.ModifyBehavior;
import com.github.originsplus.power.ModifyBehavior.EntityBehavior;

import io.github.apace100.origins.component.OriginComponent;
import net.minecraft.entity.ai.goal.FollowTargetGoal;

@Mixin(FollowTargetGoal.class)
public class FollowTargetGoalMixin {

	@Inject(at = @At(value = "HEAD"), method = "start", cancellable = true)
	public void blockZombieTarget(CallbackInfo info) {
		FollowTargetGoal target = (FollowTargetGoal) (Object) this;

		List<ModifyBehavior> powers = OriginComponent.getPowers(((FollowTargetGoalAccessor) target).getTargetEntity(), ModifyBehavior.class);
		powers.removeIf((power) -> {
			if(power.checkEntity(((TrackTargetGoalAccessor) target).getMob().getType())) {
				return false;
			} else {
				return true;
			}
		});
		
		if (!powers.isEmpty()) {
			ModifyBehavior.EntityBehavior behavior = powers.get(0).getDesiredBehavior();
			if(behavior == EntityBehavior.NEUTRAL) {
				//System.out.println("Modifying entity behavior");
				target.stop();
				info.cancel();
			}
		}
	}

}
