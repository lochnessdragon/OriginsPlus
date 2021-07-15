package com.github.originsplus.power;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;

import java.util.function.Predicate;

public class ModifyBlockDrop extends Power {

	float chance;
	float extraRolls;
	private final Predicate<CachedBlockPosition> predicate;

	public ModifyBlockDrop(PowerType<Power> type, LivingEntity player, float chance, float extraRolls,
						   Predicate<CachedBlockPosition> predicate) {
		super(type, player);
		this.chance = chance;
		this.extraRolls = extraRolls;
		this.predicate = predicate;
	}

	public boolean doesApply(BlockPos pos) {
		CachedBlockPosition cbp = new CachedBlockPosition(entity.world, pos, true);
		return predicate.test(cbp);
	}

	public boolean doesApply(CachedBlockPosition pos) {
		return predicate.test(pos);
	}

	public float getChance() {
		return chance;
	}

	public float getExtraRolls() {
		return extraRolls;
	}

}
