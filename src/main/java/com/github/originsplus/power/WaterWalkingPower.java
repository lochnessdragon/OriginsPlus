package com.github.originsplus.power;

import io.github.apace100.origins.power.Power;
import io.github.apace100.origins.power.PowerType;
import net.minecraft.entity.player.PlayerEntity;

public class WaterWalkingPower extends Power {

	int strength;
	
	public WaterWalkingPower(PowerType<?> type, PlayerEntity player, int strength) {
		super(type, player);
		this.strength = strength;
	}
	
	public int getStrength() {
		return strength;
	}

}
