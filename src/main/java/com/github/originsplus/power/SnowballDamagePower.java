package com.github.originsplus.power;

import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.ValueModifyingPower;
import net.minecraft.entity.LivingEntity;

public class SnowballDamagePower extends ValueModifyingPower {

	public SnowballDamagePower(PowerType<?> type, LivingEntity player) {
		super(type, player);
	}

}
