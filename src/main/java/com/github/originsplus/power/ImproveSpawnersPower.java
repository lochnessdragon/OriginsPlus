package com.github.originsplus.power;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;

public class ImproveSpawnersPower extends Power {

	float radius;
	EntityAttributeModifier modifier;
	
	public ImproveSpawnersPower(PowerType<?> type, LivingEntity entity, float radius, EntityAttributeModifier modifier) {
		super(type, entity);
		this.radius = radius;
		this.modifier = modifier;
	}
	
	public float getRadius() {
		return radius;
	}
	
	public EntityAttributeModifier getModifier() {
		return modifier;
	}

}
