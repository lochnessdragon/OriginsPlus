package com.github.originsplus.mixin;

import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.Power;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.originsplus.power.WaterWalkingPower;

import io.github.apace100.origins.component.OriginComponent;
import net.minecraft.enchantment.FrostWalkerEnchantment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

	@Inject(at = @At(value = "HEAD"), method = "applyMovementEffects")
	public void updateWaterWalkingPower(BlockPos pos, CallbackInfo info) {
		LivingEntity entity = (LivingEntity) (Object) this;
		
		if(PowerHolderComponent.hasPower(entity, WaterWalkingPower.class)) {
			int strength = PowerHolderComponent.getPowers(entity, WaterWalkingPower.class).stream().mapToInt(WaterWalkingPower::getStrength).sorted().findFirst().getAsInt();
			
			FrostWalkerEnchantment.freezeWater(entity, entity.world, pos, strength);
		}
	}
	
}
