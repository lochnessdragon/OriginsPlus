package com.github.originsplus.power;

import io.github.apace100.origins.power.ActiveCooldownPower;
import io.github.apace100.origins.power.PowerType;
import io.github.apace100.origins.util.HudRender;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class GrapplePower extends ActiveCooldownPower {

	SoundEvent soundOnThrown;
	SoundEvent soundOnRetract;
	double strength;
	boolean thrown;
	FishingBobberEntity grappleHook;

	public GrapplePower(PowerType<?> type, PlayerEntity player, int cooldownDuration, HudRender hudRender,
			SoundEvent soundOnThrownEvent, SoundEvent soundOnRetractEvent, double strength) {
		super(type, player, cooldownDuration, hudRender, null);

		this.soundOnThrown = soundOnThrownEvent;
		this.soundOnRetract = soundOnRetractEvent;
		this.strength = strength;
		this.thrown = false;
	}

	@Override
	public void onUse() {
		if (!player.world.isClient) {
			if (thrown) {
				// retract
				retractGrapple();
			} else if (canUse()) {
				// throw projectile
				throwGrapple();
				use();
			}
		}
	}
	
	private void throwGrapple() {
		if (soundOnThrown != null) {
			player.world.playSound((PlayerEntity) null, player.getX(), player.getY(), player.getZ(), soundOnThrown,
					SoundCategory.NEUTRAL, 0.5F, 0.4F / (player.getRandom().nextFloat() * 0.4F + 0.8F));
		}
		
		grappleHook = new FishingBobberEntity(player, player.world, 0, 0);
		player.world.spawnEntity(grappleHook);

		this.thrown = true;
	}

	private void retractGrapple() {
		if (soundOnRetract != null) {
			player.world.playSound((PlayerEntity) null, player.getX(), player.getY(), player.getZ(), soundOnRetract,
					SoundCategory.NEUTRAL, 0.5F, 0.4F / (player.getRandom().nextFloat() * 0.4F + 0.8F));
		}
		
//		System.out.println(strength);
		
		Vec3d bobberPos = grappleHook.getPos();
		
		Vec3d distance = new Vec3d(bobberPos.x - player.getPos().x, bobberPos.y - player.getPos().y, bobberPos.z - player.getPos().z);
		distance = distance.normalize();
		System.out.println(distance);
		
		Vec3d velocity = new Vec3d(distance.x * strength, distance.y * strength, distance.z * strength);
		System.out.println(velocity);
		
		Vec3d playerVelocity = player.getVelocity().add(velocity);
		
		player.setVelocity(playerVelocity);
		
		if(player instanceof ServerPlayerEntity) {
			ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
			serverPlayer.networkHandler.sendPacket(new EntityVelocityUpdateS2CPacket(player.getEntityId(), playerVelocity));
		}
		
		grappleHook.kill();
		
		this.thrown = false;
	}

}
