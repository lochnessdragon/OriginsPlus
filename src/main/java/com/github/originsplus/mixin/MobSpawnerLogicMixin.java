package com.github.originsplus.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import io.github.apace100.origins.component.OriginComponent;
import net.minecraft.world.MobSpawnerLogic;

@Mixin(MobSpawnerLogic.class)
public class MobSpawnerLogicMixin {

	@Inject(at = @At(value = "HEAD"), method = "updateSpawns", cancellable = true)
	public void increaseSpawnRate() {
		MobSpawnerLogic logic = (MobSpawnerLogic) (Object) (this);
	}
	
}
