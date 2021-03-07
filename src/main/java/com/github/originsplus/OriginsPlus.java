package com.github.originsplus;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.util.tinyfd.TinyFileDialogs;

import com.github.originsplus.registry.ModPowers;
import com.github.originsplus.registry.ModStatusEffects;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class OriginsPlus implements ModInitializer {

	public static Logger LOGGER = LogManager.getLogger();
	public static String MOD_ID = "originsplus";
	
	@Override
	public void onInitialize() {
		LOGGER.info("Origins+ is Initializing - Have fun w/ the new origins!");
		ModStatusEffects.register();
		ModPowers.register();
	}
	
	public static Identifier identifier(String path) {
		return new Identifier(MOD_ID, path);
	}

}
