package com.github.originsplus.registry;

import com.github.originsplus.OriginsPlus;
import com.github.originsplus.power.*;
import com.github.originsplus.power.ModifyBehavior.EntityBehavior;
import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.Active;
import io.github.apace100.apoli.power.PowerTypeReference;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.apoli.util.HudRender;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataType;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.registry.Registry;

import java.util.List;

public class ModPowers {

	public static final PowerTypeReference PREVENT_PHANTOM_SPAWN = new PowerTypeReference(
			OriginsPlus.identifier("block_sleep_prevent_phantom_spawn"));
	public static final PowerTypeReference CONVERT_VILLAGERS = new PowerTypeReference(
			OriginsPlus.identifier("convert_villagers"));

	public static void register() {
//		register(new PowerFactory<>(OriginsPlus.identifier("blindness"),
//				new SerializableData().add("strength", SerializableDataTypes.FLOAT, 1.0F), (data) -> (type, player) -> {
//					return new BlindnessPower(type, player, data.getFloat("strength"));
//				}).allowCondition());
		
		register(new PowerFactory<>(OriginsPlus.identifier("scale"),
				new SerializableData().add("scale_amount", SerializableDataTypes.FLOAT, 1.0F),
				(data) -> (type, player) -> {
					return new ModifyScalePower(type, player, data.getFloat("scale_amount"));
				}).allowCondition());
		register(new PowerFactory<>(OriginsPlus.identifier("modify_block_drops"),
				new SerializableData().add("chance", SerializableDataTypes.FLOAT, 1.0f)
						.add("extra_rolls", SerializableDataTypes.FLOAT, 1.0f).add("block_condition",
								ApoliDataTypes.BLOCK_CONDITION, null),
				(data) -> (type, player) -> {
					return new ModifyBlockDrop(type, player, data.getFloat("chance"), data.getFloat("extra_rolls"),
							data.isPresent("block_condition") ? (ConditionFactory.Instance) data.get("block_condition")
									: cbp -> true);
				}).allowCondition());
		register(new PowerFactory<>(OriginsPlus.identifier("grapple"),
				new SerializableData().add("cooldown", SerializableDataTypes.INT)
						.add("sound_on_thrown", SerializableDataTypes.SOUND_EVENT, null)
						.add("sound_on_retract", SerializableDataTypes.SOUND_EVENT, null)
						.add("strength", SerializableDataTypes.DOUBLE, 0.25)
						.add("hud_render", ApoliDataTypes.HUD_RENDER)
						.add("key", ApoliDataTypes.BACKWARDS_COMPATIBLE_KEY),
				data -> (type, player) -> {
					GrapplePower power = new GrapplePower(type, player, data.getInt("cooldown"),
							(HudRender) data.get("hud_render"), (SoundEvent) data.get("sound_on_thrown"),
							(SoundEvent) data.get("sound_on_retract"), data.getDouble("strength"));
					power.setKey((Active.Key) data.get("key"));
					return power;
				}).allowCondition());
		register(new PowerFactory<>(OriginsPlus.identifier("explode"), new SerializableData()
				.add("cooldown", SerializableDataTypes.INT).add("strength", SerializableDataTypes.FLOAT, 1.0f)
				.add("break_blocks", SerializableDataTypes.BOOLEAN, true)
				.add("self_damage", SerializableDataTypes.FLOAT, 1.0f).add("hud_render", ApoliDataTypes.HUD_RENDER)
				.add("key", ApoliDataTypes.BACKWARDS_COMPATIBLE_KEY)
				.add("ignitable", SerializableDataTypes.BOOLEAN, false), (data) -> (type, player) -> {
					ExplodePower power = new ExplodePower(type, player, data.getInt("cooldown"),
							(HudRender) data.get("hud_render"), data.getFloat("strength"),
							data.getBoolean("break_blocks"), data.getFloat("self_damage"),
							data.getBoolean("ignitable"));
					power.setKey((Active.Key) data.get("key"));
					return power;
				}).allowCondition());
		register(new PowerFactory<>(OriginsPlus.identifier("block_player_sleep"),
				new SerializableData().add("count_player_towards_sleep_goal", SerializableDataTypes.BOOLEAN, false),
				(data) -> (type, player) -> {
					return new BlockPlayerSleep(type, player, data.getBoolean("count_player_towards_sleep_goal"));
				}).allowCondition());
		register(new PowerFactory<>(OriginsPlus.identifier("modify_behavior"),
				new SerializableData()
						.add("behavior", SerializableDataType.enumValue(ModifyBehavior.EntityBehavior.class))
						.add("entities", SerializableDataType.list(SerializableDataTypes.ENTITY_TYPE)),
				(data) -> (type, player) -> {
					return new ModifyBehavior(type, player, (EntityBehavior) data.get("behavior"),
							(List<EntityType<?>>) data.get("entities"));
				}));
		register(new PowerFactory<>(OriginsPlus.identifier("improve_spawners"),
				new SerializableData().add("radius", SerializableDataTypes.FLOAT).add("modifier",
						SerializableDataTypes.ATTRIBUTE_MODIFIER, null),
				(data) -> (type, player) -> {
					ImproveSpawnersPower power = new ImproveSpawnersPower(type, player, data.getFloat("radius"));

					if (data.isPresent("modifier")) {
						power.addModifier(data.getModifier("modifier"));
					}

					return power;
				}).allowCondition());
		register(new PowerFactory<>(OriginsPlus.identifier("modify_snowball_damage"),
				new SerializableData().add("modifier", SerializableDataTypes.ATTRIBUTE_MODIFIER, null),
				(data) -> (type, player) -> {
					SnowballDamagePower power = new SnowballDamagePower(type, player);

					if (data.isPresent("modifier")) {
						power.addModifier(data.getModifier("modifier"));
					}

					return power;
				}).allowCondition());
		register(new PowerFactory<>(OriginsPlus.identifier("frost_walker"),
				new SerializableData().add("strength", SerializableDataTypes.INT, 1), (data) -> (type, player) -> {
					return new WaterWalkingPower(type, player, data.getInt("strength"));
				}).allowCondition());
	}

	private static void register(PowerFactory serializer) {
		Registry.register(ApoliRegistries.POWER_FACTORY, serializer.getSerializerId(), serializer);
	}

}
