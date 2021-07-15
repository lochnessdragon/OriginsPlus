package com.github.originsplus.mixin;

import com.github.originsplus.power.ModifyBehavior;
import com.github.originsplus.power.ModifyBehavior.EntityBehavior;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.sensor.VillagerHostilesSensor;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(VillagerHostilesSensor.class)
public class VillagerHostilesSensorMixin {

	@Inject(at = @At(value = "HEAD"), method = "isHostile", cancellable = true)
	public void markZombiePlayerAsHostile(final LivingEntity entity, CallbackInfoReturnable<Boolean> info) {
//		VillagerHostilesSensor sensor = (VillagerHostilesSensor) (Object) this;
		
		List<ModifyBehavior> powers = PowerHolderComponent.getPowers(entity, ModifyBehavior.class);
		powers.removeIf((power) -> !power.checkEntity(EntityType.VILLAGER));
		
		if (!powers.isEmpty()) {
			ModifyBehavior.EntityBehavior behavior = powers.get(0).getDesiredBehavior();
			if(behavior == EntityBehavior.HOSTILE) {
				info.setReturnValue(true);
			}
		}
	}
	
	@Inject(at = @At(value = "HEAD"), method = "isCloseEnoughForDanger", cancellable = true)
	public void zombiePlayerIsCloseEnoughForDanger(LivingEntity entity, LivingEntity hostile, CallbackInfoReturnable<Boolean> info) {
//		System.out.println("Begin Zombie Method");
		if(hostile instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) hostile;
			List<ModifyBehavior> powers = PowerHolderComponent.getPowers(player, ModifyBehavior.class);
			powers.removeIf((power) -> !power.checkEntity(EntityType.VILLAGER));
			
			if (!powers.isEmpty()) {
				ModifyBehavior.EntityBehavior behavior = powers.get(0).getDesiredBehavior();
				if(behavior == EntityBehavior.HOSTILE) {
					float distanceRequired = 8.0f;
					if(hostile.squaredDistanceTo(entity) <= (double)(distanceRequired * distanceRequired)) {
						info.setReturnValue(true);
					} else {
						info.setReturnValue(false);
					}
				} else {
					info.setReturnValue(false);
				}
			} else {
				info.setReturnValue(false);
			}
		}
//		System.out.println("End Zombie Method");
	}
	
}
