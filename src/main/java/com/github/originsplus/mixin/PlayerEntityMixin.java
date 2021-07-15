package com.github.originsplus.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.originsplus.registry.ModPowers;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

	@Inject(at = @At("HEAD"), method = "incrementStat(Lnet/minecraft/util/Identifier;)V", cancellable = true)
	public void preventPhantomSpawn(Identifier stat, CallbackInfo ci) {
		if(stat == Stats.TIME_SINCE_REST) {
			if(ModPowers.PREVENT_PHANTOM_SPAWN.isActive((PlayerEntity) (Object) this)) {
				//System.out.println("prevented time since rest from incrementing");
				ci.cancel();
			}
		}
	}
	
}
