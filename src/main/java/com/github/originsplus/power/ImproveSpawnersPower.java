package com.github.originsplus.power;

import io.github.apace100.origins.power.Power;
import io.github.apace100.origins.power.PowerType;
import io.github.apace100.origins.power.ValueModifyingPower;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;

public class ImproveSpawnersPower extends ValueModifyingPower {

	float radius;
	
	public ImproveSpawnersPower(PowerType<?> type, PlayerEntity player, float radius) {
		super(type, player);
		this.radius = radius;
	}
	
	public float getRadius() {
		return radius;
	}

}
