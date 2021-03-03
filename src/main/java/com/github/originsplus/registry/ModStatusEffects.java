package com.github.originsplus.registry;

import com.github.originsplus.OriginsPlus;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModStatusEffects {
	
	private static StatusEffect register(String path, StatusEffect effect) {
		Registry.register(Registry.STATUS_EFFECT, new Identifier(OriginsPlus.MOD_ID, path), effect);
		return effect;
	}
	
	public static void register() {
	}
}
