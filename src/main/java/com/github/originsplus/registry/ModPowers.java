package com.github.originsplus.registry;

import java.util.function.Predicate;

import com.github.originsplus.OriginsPlus;
import com.github.originsplus.power.BlindnessPower;
import com.github.originsplus.power.ExplodePower;
import com.github.originsplus.power.GrapplePower;
import com.github.originsplus.power.ModifyBlockDrop;
import com.github.originsplus.power.ModifyScalePower;

import io.github.apace100.origins.power.Active;
import io.github.apace100.origins.power.factory.PowerFactory;
import io.github.apace100.origins.registry.ModRegistries;
import io.github.apace100.origins.util.HudRender;
import io.github.apace100.origins.util.SerializableData;
import io.github.apace100.origins.util.SerializableDataType;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.sound.SoundEvent;
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
		register(new PowerFactory<>(OriginsPlus.identifier("grapple"),
				new SerializableData().add("cooldown", SerializableDataType.INT)
						.add("sound_on_thrown", SerializableDataType.SOUND_EVENT, null)
						.add("sound_on_retract", SerializableDataType.SOUND_EVENT, null)
						.add("strength", SerializableDataType.DOUBLE, 0.25)
						.add("hud_render", SerializableDataType.HUD_RENDER)
						.add("key", SerializableDataType.ACTIVE_KEY_TYPE, Active.KeyType.PRIMARY),
				data -> (type, player) -> {
					GrapplePower power = new GrapplePower(type, player, data.getInt("cooldown"),
							(HudRender) data.get("hud_render"), (SoundEvent) data.get("sound_on_thrown"),
							(SoundEvent) data.get("sound_on_retract"), data.getDouble("strength"));
					power.setKey((Active.KeyType) data.get("key"));
					return power;
				}).allowCondition());
		register(new PowerFactory<>(OriginsPlus.identifier("explode"), new SerializableData()
				.add("cooldown", SerializableDataType.INT).add("strength", SerializableDataType.FLOAT, 1.0f)
				.add("break_blocks", SerializableDataType.BOOLEAN, true)
				.add("self_damage", SerializableDataType.FLOAT, 1.0f).add("hud_render", SerializableDataType.HUD_RENDER)
				.add("key", SerializableDataType.ACTIVE_KEY_TYPE, Active.KeyType.PRIMARY)
				.add("ignitable", SerializableDataType.BOOLEAN, false), (data) -> (type, player) -> {
					ExplodePower power = new ExplodePower(type, player, data.getInt("cooldown"),
							(HudRender) data.get("hud_render"), data.getFloat("strength"),
							data.getBoolean("break_blocks"), data.getFloat("self_damage"),
							data.getBoolean("ignitable"));
					power.setKey((Active.KeyType) data.get("key"));
					return power;
				}));
	}

	private static void register(PowerFactory serializer) {
		Registry.register(ModRegistries.POWER_FACTORY, serializer.getSerializerId(), serializer);
	}

}
