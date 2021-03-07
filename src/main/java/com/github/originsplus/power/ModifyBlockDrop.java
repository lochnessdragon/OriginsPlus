package com.github.originsplus.power;

import java.util.function.Predicate;

import io.github.apace100.origins.power.Power;
import io.github.apace100.origins.power.PowerType;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;

public class ModifyBlockDrop extends Power {

	float chance;
	float extraRolls;
	private final Predicate<CachedBlockPosition> predicate;

	public ModifyBlockDrop(PowerType<Power> type, PlayerEntity player, float chance, float extraRolls,
			Predicate<CachedBlockPosition> predicate) {
		super(type, player);
		this.chance = chance;
		this.extraRolls = extraRolls;
		this.predicate = predicate;
	}

	public boolean doesApply(BlockPos pos) {
		CachedBlockPosition cbp = new CachedBlockPosition(player.world, pos, true);
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
