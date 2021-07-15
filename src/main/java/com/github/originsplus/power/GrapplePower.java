package com.github.originsplus.power;

import com.github.originsplus.entity.IGrappleHook;
import io.github.apace100.apoli.power.ActiveCooldownPower;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.util.HudRender;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.Vec3d;

public class GrapplePower extends ActiveCooldownPower {

	SoundEvent soundOnThrown;
	SoundEvent soundOnRetract;
	double strength;
	boolean thrown;
	FishingBobberEntity grappleHook;

	public GrapplePower(PowerType<?> type, LivingEntity player, int cooldownDuration, HudRender hudRender,
						SoundEvent soundOnThrownEvent, SoundEvent soundOnRetractEvent, double strength) {
		super(type, player, cooldownDuration, hudRender, null);

		this.soundOnThrown = soundOnThrownEvent;
		this.soundOnRetract = soundOnRetractEvent;
		this.strength = strength;
		this.thrown = false;
	}

	@Override
	public void onUse() {
		if (!entity.world.isClient) {
			if (thrown && !grappleHook.isRemoved()) {
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
		if (entity instanceof PlayerEntity player) {
			if (soundOnThrown != null) {
				player.world.playSound((PlayerEntity) null, player.getX(), player.getY(), player.getZ(), soundOnThrown,
						SoundCategory.NEUTRAL, 0.5F, 0.4F / (player.getRandom().nextFloat() * 0.4F + 0.8F));
			}

			FishingBobberEntity playerHook = player.fishHook;

			grappleHook = new FishingBobberEntity(player, player.world, 0, 0);
			((IGrappleHook) grappleHook).setGrapple(true);
			player.fishHook = playerHook; // reset player fishhook
			player.world.spawnEntity(grappleHook);

			this.thrown = true;
		}
	}

	private void retractGrapple() {
		if (entity instanceof PlayerEntity player) {
			if (soundOnRetract != null) {
				player.world.playSound((PlayerEntity) null, player.getX(), player.getY(), player.getZ(), soundOnRetract,
						SoundCategory.NEUTRAL, 0.5F, 0.4F / (player.getRandom().nextFloat() * 0.4F + 0.8F));
			}

//		System.out.println(strength);

			Vec3d bobberPos = grappleHook.getPos();

			Vec3d distance = new Vec3d(bobberPos.x - player.getPos().x, bobberPos.y - player.getPos().y, bobberPos.z - player.getPos().z);
			distance = distance.normalize();
			//System.out.println(distance);

			Vec3d velocity = new Vec3d(distance.x * strength, distance.y * strength, distance.z * strength);
			//System.out.println(velocity);

			Vec3d playerVelocity = player.getVelocity().add(velocity);

			player.setVelocity(playerVelocity);

			if (player instanceof ServerPlayerEntity) {
				ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
				serverPlayer.networkHandler.sendPacket(new EntityVelocityUpdateS2CPacket(player.getId(), playerVelocity));
			}

			grappleHook.kill();

			this.thrown = false;
		}
	}

}
