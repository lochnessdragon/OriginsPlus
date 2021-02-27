package com.github.originsplus.registry;

import com.github.originsplus.OriginsPlus;
import com.github.originsplus.effects.BlurinessEffect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModStatusEffects {

	public static StatusEffect BLURINESS = new BlurinessEffect();
	
	private static StatusEffect register(String path, StatusEffect effect) {
		Registry.register(Registry.STATUS_EFFECT, new Identifier(OriginsPlus.MOD_ID, path), effect);
		return effect;
	}
	
	public static void register() {
		register("bluriness", BLURINESS);
	}
}
