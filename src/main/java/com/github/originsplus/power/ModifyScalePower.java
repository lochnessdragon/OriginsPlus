package com.github.originsplus.power;

import io.github.apace100.origins.power.Power;
import io.github.apace100.origins.power.PowerType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleType;
import virtuoel.pehkui.util.ScaleUtils;

public class ModifyScalePower extends Power {

	private static final ScaleType[] MODIFY_SIZE_TYPES = { ScaleType.WIDTH, ScaleType.HEIGHT, ScaleType.DROPS };

	public final float scale;

	public ModifyScalePower(PowerType<?> type, PlayerEntity player, float scale) {
		super(type, player);
		this.scale = scale;
	}

	@Override
	public void onAdded() {
		super.onAdded();
		System.out.println("OnAdded");
		for(ScaleType type : MODIFY_SIZE_TYPES) {
			setScale(scale, type);
		}
	}

	@Override
	public void onRemoved() {
		super.onRemoved();
		System.out.println("OnRemoved");
		for(ScaleType type : MODIFY_SIZE_TYPES) {
			setScale(1.0f, type);
		}
	}
	
	private void setScale(float scale, ScaleType type) {
		final ScaleData scaleData = ScaleData.of((Entity) player, type);
		scaleData.setScale(scale);
		scaleData.setTargetScale(scale);
		scaleData.markForSync();
	}

}
