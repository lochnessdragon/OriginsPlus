package com.github.originsplus;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.originsplus.registry.ModEntityConditions;
import com.github.originsplus.registry.ModEvents;
import com.github.originsplus.registry.ModPowers;

import io.github.apace100.origins.registry.ModRegistries;
import io.github.apace100.origins.util.OriginsConfig;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class OriginsPlus implements ModInitializer {

	public static Logger LOGGER = LogManager.getLogger();
	public static String MOD_ID = "originsplus";

	@Override
	public void onInitialize() {
		LOGGER.info("Origins+ is Initializing - Have fun w/ the new origins!");
		
		ModPowers.register();
		ModEvents.register();
		ModEntityConditions.register();
		
	
		
//		ModRegistries.ENTITY_CONDITION.getIds().forEach((id) -> { System.out.println("Entity Condition: " + id.toString());});
	}

	public static Identifier identifier(String path) {
		return new Identifier(MOD_ID, path);
	}

}
