package com.github.originsplus.registry;

import com.github.originsplus.OriginsPlus;
import com.github.originsplus.power.BlindnessPower;

import io.github.apace100.origins.power.factory.PowerFactory;
import io.github.apace100.origins.registry.ModRegistries;
import io.github.apace100.origins.util.SerializableData;
import io.github.apace100.origins.util.SerializableDataType;
import net.minecraft.util.registry.Registry;

public class ModPowers {

	public static void register() {
		register(new PowerFactory<>(OriginsPlus.identifier("blindness"),
				new SerializableData().add("strength", SerializableDataType.FLOAT, 1.0F), (data) -> (type, player) -> {
					return new BlindnessPower(type, player, data.getFloat("strength"));
				}).allowCondition());
	}

	private static void register(PowerFactory serializer) {
		Registry.register(ModRegistries.POWER_FACTORY, serializer.getSerializerId(), serializer);
	}

}
