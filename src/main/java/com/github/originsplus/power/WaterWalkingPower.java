package com.github.originsplus.power;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import net.minecraft.entity.LivingEntity;

public class WaterWalkingPower extends Power {

	int strength;
	
	public WaterWalkingPower(PowerType<?> type, LivingEntity player, int strength) {
		super(type, player);
		this.strength = strength;
	}
	
	public int getStrength() {
		return strength;
	}

}
