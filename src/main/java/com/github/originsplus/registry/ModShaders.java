package com.github.originsplus.registry;

import java.util.List;
import java.util.Optional;

import com.github.originsplus.power.BlindnessPower;

import io.github.apace100.origins.registry.ModComponents;
import ladysnake.satin.api.event.ShaderEffectRenderCallback;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import ladysnake.satin.api.managed.uniform.Uniform1f;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ModShaders {

	public static final ManagedShaderEffect BLURINESS = ShaderEffectManager.getInstance()
			.manage(new Identifier("minecraft", "shaders/post/blur.json"));
	private static boolean BLURINESS_ENABLED = true;
	private static final Uniform1f BLURINESS_STRENGTH = BLURINESS.findUniform1f("Radius");

	public static void register() {
		ShaderEffectRenderCallback.EVENT.register((tickDelta) -> {
			List<BlindnessPower> bps = ModComponents.ORIGIN.get(MinecraftClient.getInstance().player).getPowers(BlindnessPower.class); 
			if(!bps.isEmpty()) {
				Optional<Float> strength = bps.stream().filter(BlindnessPower::isActive).map(BlindnessPower::getStrength).max(Float::compareTo);
				if(strength.isPresent()) {
					BLURINESS_STRENGTH.set(strength.get());
				}
				BLURINESS.render(tickDelta);
			}
		});
	}

}
