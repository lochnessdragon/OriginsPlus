package com.github.originsplus.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.world.MobSpawnerLogic;

@Mixin(MobSpawnerLogic.class)
public interface MobSpawnerLogicAccessor {
	@Accessor("spawnCount")
	public void setSpawnCount(int spawnCount);
	
	@Accessor("spawnCount")
	public int getSpawnCount();
}
