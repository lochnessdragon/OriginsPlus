package com.github.originsplus.registry;

import com.github.originsplus.OriginsPlus;
import com.github.originsplus.power.BlindnessPower;
import com.github.originsplus.power.ModifyBlockDrop;
import com.github.originsplus.power.ModifyScalePower;
import com.google.common.base.Predicate;

import io.github.apace100.origins.power.factory.PowerFactory;
import io.github.apace100.origins.registry.ModRegistries;
import io.github.apace100.origins.util.SerializableData;
import io.github.apace100.origins.util.SerializableDataType;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.util.registry.Registry;

public class ModPowers {

	public static void register() {
		register(new PowerFactory<>(OriginsPlus.identifier("blindness"),
				new SerializableData().add("strength", SerializableDataType.FLOAT, 1.0F), (data) -> (type, player) -> {
					return new BlindnessPower(type, player, data.getFloat("strength"));
				}).allowCondition());
		register(new PowerFactory<>(OriginsPlus.identifier("scale"),
				new SerializableData().add("scale_amount", SerializableDataType.FLOAT, 1.0F),
				(data) -> (type, player) -> {
					return new ModifyScalePower(type, player, data.getFloat("scale_amount"));
				}).allowCondition());
		register(new PowerFactory<>(OriginsPlus.identifier("modify_block_drops"),
				new SerializableData().add("chance", SerializableDataType.FLOAT, 1.0f)
						.add("extra_rolls", SerializableDataType.FLOAT, 1.0f).add("block_condition",
								SerializableDataType.BLOCK_CONDITION, null),
				(data) -> (type, player) -> {
					return new ModifyBlockDrop(type, player, data.getFloat("chance"), data.getFloat("extra_rolls"),
							data.isPresent("block_condition")
									? (Predicate<CachedBlockPosition>) data.get("block_condition")
									: cbp -> true);
				}).allowCondition());
	}

	private static void register(PowerFactory serializer) {
		Registry.register(ModRegistries.POWER_FACTORY, serializer.getSerializerId(), serializer);
	}

}
