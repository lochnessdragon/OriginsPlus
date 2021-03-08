package com.github.originsplus;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.originsplus.events.BlockDropCallback;
import com.github.originsplus.power.ModifyBlockDrop;
import com.github.originsplus.registry.ModPowers;
import com.github.originsplus.registry.ModStatusEffects;

import io.github.apace100.origins.component.OriginComponent;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;

public class OriginsPlus implements ModInitializer {

	public static Logger LOGGER = LogManager.getLogger();
	public static String MOD_ID = "originsplus";

	@Override
	public void onInitialize() {
		LOGGER.info("Origins+ is Initializing - Have fun w/ the new origins!");
		ModStatusEffects.register();
		ModPowers.register();

		// register block broken callback
		BlockDropCallback.EVENT.register((block, state, world, pos, player) -> {
			if (!world.isClient) {
				//System.out.println("Block broken!");
				if (OriginComponent.hasPower(player, ModifyBlockDrop.class)) {
					//System.out.println("Player has the modify block drop power");
					Optional<ModifyBlockDrop> dropOptional = OriginComponent.getPowers(player, ModifyBlockDrop.class).stream()
							.filter((power) -> {
								return power.doesApply(pos);
							}).sorted((power1, power2) -> {
								if (power1.getChance() == power2.getChance()) {
									if (power1.getExtraRolls() == power2.getExtraRolls()) {
										return 0;
									} else {
										return power1.getExtraRolls() < power2.getExtraRolls() ? -1 : 1;
									}
								} else {
									return power1.getChance() < power2.getChance() ? -1 : 1;
								}
							}).findFirst();
					
					if(dropOptional.isPresent()) {
						//System.out.println("Potentially dropping extra blocks!");
						ModifyBlockDrop drop = dropOptional.get();
						if (drop.getChance() > world.getRandom().nextFloat()) {
							//System.out.println("Dropped extra blocks");
							for (int i = 0; i < drop.getExtraRolls(); i++) {
								block.dropStacks(state, world, pos);
							}
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
