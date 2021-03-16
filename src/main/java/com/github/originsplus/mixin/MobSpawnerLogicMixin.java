package com.github.originsplus.mixin;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import org.spongepowered.asm.mixin.Mixin;
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

	private static int DEFAULT_SPAWN_COUNT = 4;
	
	@Inject(at = @At(value = "HEAD"), method = "update", cancellable = true)
	public void increaseSpawnRate(CallbackInfo info) {
		MobSpawnerLogic logic = (MobSpawnerLogic) (Object) (this);

		Stream<? extends PlayerEntity> players = logic.getWorld().getPlayers().stream().filter((player) -> {
			return OriginComponent.hasPower(player, ImproveSpawnersPower.class);
		});

		MobSpawnerLogicAccessor logicAccessor = (MobSpawnerLogicAccessor) logic;
		int base = DEFAULT_SPAWN_COUNT;
		int spawnCount = base;
		
		Iterator<? extends PlayerEntity> playerIterator = players.iterator();
		while (playerIterator.hasNext()) {
			PlayerEntity player = playerIterator.next();
			
			List<ImproveSpawnersPower> powers = OriginComponent.getPowers(player, ImproveSpawnersPower.class);
			powers.sort((power1, power2) -> {
				if (power1.getRadius() == power2.getRadius()) {
					return 0;
				} else if (power1.getRadius() < power2.getRadius()) {
					return -1;
				} else {
					return 1;
				}
			});

			ImproveSpawnersPower power = powers.get(0);
			
			if (logic.getPos().isWithinDistance(player.getBlockPos(), power.getRadius())) {
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
		
		//System.out.println("Spawn Count was: " + logicAccessor.getSpawnCount() + " and is now: " + spawnCount);
		logicAccessor.setSpawnCount(spawnCount);
	}

}
