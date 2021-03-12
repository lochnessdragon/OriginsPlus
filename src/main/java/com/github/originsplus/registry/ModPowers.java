package com.github.originsplus.registry;

import java.util.List;

import com.github.originsplus.OriginsPlus;
import com.github.originsplus.power.BlindnessPower;
import com.github.originsplus.power.BlockPlayerSleep;
import com.github.originsplus.power.ExplodePower;
import com.github.originsplus.power.GrapplePower;
import com.github.originsplus.power.ImproveSpawnersPower;
import com.github.originsplus.power.ModifyBehavior;
import com.github.originsplus.power.ModifyBehavior.EntityBehavior;
import com.github.originsplus.power.ModifyBlockDrop;
import com.github.originsplus.power.ModifyScalePower;
import com.google.common.base.Predicate;

import io.github.apace100.origins.power.Active;
import io.github.apace100.origins.power.PowerTypeReference;
import io.github.apace100.origins.power.factory.PowerFactory;
import io.github.apace100.origins.registry.ModRegistries;
import io.github.apace100.origins.util.HudRender;
import io.github.apace100.origins.util.SerializableData;
import io.github.apace100.origins.util.SerializableDataType;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.entity.EntityType;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.registry.Registry;

public class ModPowers {

	public static final PowerTypeReference PREVENT_PHANTOM_SPAWN = new PowerTypeReference(
			OriginsPlus.identifier("block_sleep_prevent_phantom_spawn"));

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
						.add("key", SerializableDataType.BACKWARDS_COMPATIBLE_KEY),
				data -> (type, player) -> {
					GrapplePower power = new GrapplePower(type, player, data.getInt("cooldown"),
							(HudRender) data.get("hud_render"), (SoundEvent) data.get("sound_on_thrown"),
							(SoundEvent) data.get("sound_on_retract"), data.getDouble("strength"));
					power.setKey((Active.Key) data.get("key"));
					return power;
				}).allowCondition());
		register(new PowerFactory<>(OriginsPlus.identifier("explode"), new SerializableData()
				.add("cooldown", SerializableDataType.INT).add("strength", SerializableDataType.FLOAT, 1.0f)
				.add("break_blocks", SerializableDataType.BOOLEAN, true)
				.add("self_damage", SerializableDataType.FLOAT, 1.0f).add("hud_render", SerializableDataType.HUD_RENDER)
				.add("key", SerializableDataType.BACKWARDS_COMPATIBLE_KEY)
				.add("ignitable", SerializableDataType.BOOLEAN, false), (data) -> (type, player) -> {
					ExplodePower power = new ExplodePower(type, player, data.getInt("cooldown"),
							(HudRender) data.get("hud_render"), data.getFloat("strength"),
							data.getBoolean("break_blocks"), data.getFloat("self_damage"),
							data.getBoolean("ignitable"));
					power.setKey((Active.Key) data.get("key"));
					return power;
				}));
		register(new PowerFactory<>(OriginsPlus.identifier("block_player_sleep"),
				new SerializableData().add("count_player_towards_sleep_goal", SerializableDataType.BOOLEAN, false),
				(data) -> (type, player) -> {
					return new BlockPlayerSleep(type, player, data.getBoolean("count_player_towards_sleep_goal"));
				}));
		register(new PowerFactory<>(OriginsPlus.identifier("modify_behavior"),
				new SerializableData()
						.add("behavior", SerializableDataType.enumValue(ModifyBehavior.EntityBehavior.class))
						.add("entities", SerializableDataType.list(SerializableDataType.ENTITY_TYPE)),
				(data) -> (type, player) -> {
					return new ModifyBehavior(type, player, (EntityBehavior) data.get("behavior"),
							(List<EntityType<?>>) data.get("entities"));
				}));
		register(new PowerFactory<>(OriginsPlus.identifier("improve_spawners"), new SerializableData()
				.add("radius", SerializableDataType.FLOAT).add("modifier", SerializableDataType.ATTRIBUTE_MODIFIER, null),
				(data) -> (type, player) -> {
					ImproveSpawnersPower power = new ImproveSpawnersPower(type, player, data.getFloat("radius"));
                    
					if(data.isPresent("modifier")) {
                        power.addModifier(data.getModifier("modifier"));
                    }
                    
					return power;
				}));
	}

	private static void register(PowerFactory serializer) {
		Registry.register(ModRegistries.POWER_FACTORY, serializer.getSerializerId(), serializer);
	}

}
