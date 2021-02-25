package com.github.originsplus;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.api.ModInitializer;

public class OriginsPlus implements ModInitializer {

	public static Logger LOGGER = LogManager.getLogger();
	
	@Override
	public void onInitialize() {
		LOGGER.info("OriginsPlus Initializing...");
	}

}
