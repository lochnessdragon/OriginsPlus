package com.github.originsplus.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.originsplus.power.SnowballDamagePower;

import io.github.apace100.origins.component.OriginComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.util.hit.EntityHitResult;

@Mixin(SnowballEntity.class)
public class SnowballEntityMixin {

	@Inject(at = @At(value = "HEAD"), method = "onEntityHit")
	public void increaseSnowballDamage(EntityHitResult entityHitResult, CallbackInfo info) {
		SnowballEntity entity = (SnowballEntity) (Object) this;

		if (OriginComponent.hasPower(entity.getOwner(), SnowballDamagePower.class)) {
			List<SnowballDamagePower> powers = OriginComponent.getPowers(entity.getOwner(), SnowballDamagePower.class);
			double damage = 0;
			for (SnowballDamagePower power : powers) {
				for (EntityAttributeModifier modifier : power.getModifiers()) {
					Operation operation = modifier.getOperation();
					double value = modifier.getValue();

					switch (operation) {
					case ADDITION:
						damage += value;
						break;
					case MULTIPLY_TOTAL:
						damage *= value;
						break;
					case MULTIPLY_BASE:
						// technically, the base is zero, so...
						break;
					default:
						break;
					}
				}
			}
			
			entityHitResult.getEntity().damage(DamageSource.thrownProjectile(entity, entity.getOwner()), (float) damage);
		}
	}

}
