package com.github.originsplus;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.originsplus.registry.ModEntityConditions;
import com.github.originsplus.registry.ModEvents;
import com.github.originsplus.registry.ModPowers;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.loader.api.FabricLoader;
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
import net.minecraft.world.WorldEvents;

public class OriginsPlus implements ModInitializer {

	public static Logger LOGGER = LogManager.getLogger();
	public static String MOD_ID = "originsplus";
	public static String VERSION = "";

	@Override
	public void onInitialize() {
		FabricLoader.getInstance().getModContainer(MOD_ID).ifPresent(modContainer -> {
			VERSION = modContainer.getMetadata().getVersion().getFriendlyString();
		});
		
		LOGGER.info("Origins+ v" + VERSION + " is Initializing - Have fun w/ the new origins!");

		ModPowers.register();
		ModEvents.register();
		ModEntityConditions.register();

		AttackEntityCallback.EVENT.register((player, world, hand, entity, hitresult) -> {
			if (!world.isClient) {
				ServerWorld serverWorld = (ServerWorld) world;
				if (ModPowers.CONVERT_VILLAGERS.isActive(player) && !player.isCreative()) {
					// only convert villagers to zombie villagers in normal/hard modes
					if ((serverWorld.getDifficulty() == Difficulty.NORMAL
							|| serverWorld.getDifficulty() == Difficulty.HARD)
							&& entity instanceof VillagerEntity villagerEntity) {
						// if we are in normal mode, there is a 50/50 chance of not converting the villager.
						if (serverWorld.getDifficulty() == Difficulty.NORMAL && serverWorld.random.nextBoolean()) {
							return ActionResult.PASS;
						}
						
						// this is basically what minecraft does to summon a zombie villager
						ZombieVillagerEntity zombieVillagerEntity = villagerEntity
								.convertTo(EntityType.ZOMBIE_VILLAGER, false);
						if(zombieVillagerEntity != null) {
							zombieVillagerEntity.initialize(serverWorld,
								serverWorld.getLocalDifficulty(zombieVillagerEntity.getBlockPos()),
								SpawnReason.CONVERSION, new ZombieEntity.ZombieData(false, true), null);
							zombieVillagerEntity.setVillagerData(villagerEntity.getVillagerData());
							zombieVillagerEntity
								.setGossipData(villagerEntity.getGossip().serialize(NbtOps.INSTANCE));
							zombieVillagerEntity.setOfferData(villagerEntity.getOffers().toNbt());
							zombieVillagerEntity.setXp(villagerEntity.getExperience());
							serverWorld.syncWorldEvent((PlayerEntity) null, WorldEvents.ZOMBIE_INFECTS_VILLAGER, player.getBlockPos(), 0);
						}
					}
				}
				
			}

			return ActionResult.PASS;
		});
	}

	public static Identifier identifier(String path) {
		return new Identifier(MOD_ID, path);
	}

}
