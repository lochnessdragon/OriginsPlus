package com.github.originsplus.mixin;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.originsplus.power.ImproveSpawnersPower;

import io.github.apace100.origins.component.OriginComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.MobSpawnerLogic;

@Mixin(MobSpawnerLogic.class)
public class MobSpawnerLogicMixin {

	@Shadow private int spawnCount;
	private static int DEFAULT_SPAWN_COUNT = 4;
	
	@Inject(at = @At(value = "HEAD"), method = "serverTick", cancellable = true)
	public void increaseSpawnRate(ServerWorld world, BlockPos pos, CallbackInfo ci) {
		Stream<? extends PlayerEntity> players = world.getPlayers().stream().filter((player) -> PowerHolderComponent.hasPower(player, ImproveSpawnersPower.class));

		int base = DEFAULT_SPAWN_COUNT;
		int spawnCount = base;
		
		Iterator<? extends PlayerEntity> playerIterator = players.iterator();
		while (playerIterator.hasNext()) {
			PlayerEntity player = playerIterator.next();
			
			List<ImproveSpawnersPower> powers = PowerHolderComponent.getPowers(player, ImproveSpawnersPower.class);
			powers.sort((power1, power2) -> Float.compare(power1.getRadius(), power2.getRadius()));

			ImproveSpawnersPower power = powers.get(0);
			
			if (pos.isWithinDistance(player.getBlockPos(), power.getRadius())) {
				for(EntityAttributeModifier modifier : power.getModifiers()) {
					Operation operation = modifier.getOperation();
					double value = modifier.getValue();

					switch (operation) {
					case ADDITION:
						spawnCount += value;
						break;
					case MULTIPLY_BASE:
						int intermediatary = (int) ((double) base * value);
						int diff = intermediatary - base;
						spawnCount += diff;
						break;
					case MULTIPLY_TOTAL:
						spawnCount *= value;
						break;
					default:
						break;
					}
				}
			}
		}
		
		this.spawnCount = spawnCount;
	}

}
