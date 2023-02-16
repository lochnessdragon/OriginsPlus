package com.github.originsplus.power;

import java.util.List;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;

public class SnowballDamagePower extends Power {
	
	List<EntityAttributeModifier> modifiers;
	
	public SnowballDamagePower(PowerType<?> type, LivingEntity player) {
		super(type, player);
	}

}
