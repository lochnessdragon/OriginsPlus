package com.github.originsplus;

import com.github.originsplus.registry.ModEntityConditions;
import com.github.originsplus.registry.ModEvents;
import com.github.originsplus.registry.ModPowers;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.mob.ZombieVillagerEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.world.Difficulty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OriginsPlus implements ModInitializer {

	public static Logger LOGGER = LogManager.getLogger();
	public static String MOD_ID = "originsplus";

	@Override
	public void onInitialize() {
		LOGGER.info("Origins+ is Initializing - Have fun w/ the new origins!");

		ModPowers.register();
		ModEvents.register();
		ModEntityConditions.register();

		AttackEntityCallback.EVENT.register((player, world, hand, entity, hitresult) -> {
			if (!world.isClient) {
				ServerWorld serverWorld = (ServerWorld) world;
				if (ModPowers.CONVERT_VILLAGERS.isActive(player) && !player.isCreative()) {
					if ((serverWorld.getDifficulty() == Difficulty.NORMAL
							|| serverWorld.getDifficulty() == Difficulty.HARD)
							&& entity instanceof VillagerEntity villagerEntity) {
						if (serverWorld.getDifficulty() != Difficulty.HARD && serverWorld.random.nextBoolean()) {
							return ActionResult.PASS;
						}

						ZombieVillagerEntity zombieVillagerEntity = villagerEntity
								.convertTo(EntityType.ZOMBIE_VILLAGER, false);
						zombieVillagerEntity.initialize(serverWorld,
								serverWorld.getLocalDifficulty(zombieVillagerEntity.getBlockPos()),
								SpawnReason.CONVERSION, new ZombieEntity.ZombieData(false, true), null);
						zombieVillagerEntity.setVillagerData(villagerEntity.getVillagerData());
						zombieVillagerEntity
								.setGossipData(villagerEntity.getGossip().serialize(NbtOps.INSTANCE).getValue());
						zombieVillagerEntity.setOfferData(villagerEntity.getOffers().toNbt());
						zombieVillagerEntity.setXp(villagerEntity.getExperience());
						serverWorld.syncWorldEvent((PlayerEntity) null, 1026, player.getBlockPos(), 0);
					}
				}
				
			}

			return ActionResult.PASS;
		});

//		ModRegistries.ENTITY_CONDITION.getIds().forEach((id) -> { System.out.println("Entity Condition: " + id.toString());});
	}

	public static Identifier identifier(String path) {
		return new Identifier(MOD_ID, path);
	}

}
