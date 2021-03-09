package com.github.originsplus.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class GrappleHookEntity extends FishingBobberEntity {

	public GrappleHookEntity(PlayerEntity thrower, World world, int lureLevel, int luckOfTheSeaLevel) {
		super(thrower, world, lureLevel, luckOfTheSeaLevel);
	}



}
