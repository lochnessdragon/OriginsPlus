package com.github.originsplus.power;

import io.github.apace100.origins.power.PowerType;
import io.github.apace100.origins.power.ValueModifyingPower;
import net.minecraft.entity.player.PlayerEntity;

public class SnowballDamagePower extends ValueModifyingPower {

	public SnowballDamagePower(PowerType<?> type, PlayerEntity player) {
		super(type, player);
	}

}
