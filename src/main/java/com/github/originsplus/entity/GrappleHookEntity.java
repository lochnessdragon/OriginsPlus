package com.github.originsplus.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class GrappleHookEntity extends ThrownItemEntity {

	public GrappleHookEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
		super(entityType, world);
	}
 
	public GrappleHookEntity(World world, LivingEntity owner) {
		super(null, owner, world); // null will be changed later
	}
 
	public GrappleHookEntity(World world, double x, double y, double z) {
		super(null, x, y, z, world); // null will be changed later
	}
 
	@Override
	protected Item getDefaultItem() {
		return null; // We will configure this later, once we have created the ProjectileItem.
	}

}
