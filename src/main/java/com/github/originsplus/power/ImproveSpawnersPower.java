package com.github.originsplus.power;

import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.ValueModifyingPower;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public class ImproveSpawnersPower extends ValueModifyingPower {

	float radius;
	
	public ImproveSpawnersPower(PowerType<?> type, LivingEntity player, float radius) {
		super(type, player);
		this.radius = radius;
	}
	
	public float getRadius() {
		return radius;
	}

}
