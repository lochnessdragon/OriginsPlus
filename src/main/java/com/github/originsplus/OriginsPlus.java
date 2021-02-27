package com.github.originsplus;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.github.apace100.origins.origin.Origin;
import net.fabricmc.api.ModInitializer;

public class OriginsPlus implements ModInitializer {

	public static Logger LOGGER = LogManager.getLogger();
	public static String MOD_ID = "originsclasses";
	
	@Override
	public void onInitialize() {
		LOGGER.info("OriginsPlus Initializing...");
		
	}

}
