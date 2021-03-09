package com.github.originsplus.registry;

import java.util.List;

import io.github.apace100.origins.Origins;
import io.github.apace100.origins.power.factory.condition.ConditionFactory;
import io.github.apace100.origins.registry.ModRegistries;
import io.github.apace100.origins.util.SerializableData;
import io.github.apace100.origins.util.SerializableDataType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;

public class ModPlayerConditions {

	public static void register() {
		register(new ConditionFactory<PlayerEntity>(
				Origins.identifier("entities_in_radius"), new SerializableData()
						.add("entities", SerializableDataType.list(SerializableDataType.ENTITY_TYPE)).add("radius", SerializableDataType.FLOAT),
				(data, player) -> {
					float radius = data.getFloat("radius");
					List<EntityType<?>> entityList = (List<EntityType<?>>) data.get("entities");
					
					boolean locatedEntity = false;
					
					for(EntityType type : entityList) {
						locatedEntity = locatedEntity || !player.world.getEntitiesByType(type, new Box(new Vec3d(player.getX() - radius, player.getY() - radius, player.getZ() - radius), new Vec3d(player.getX() + radius, player.getY() + radius, player.getZ() + radius)), (entity) -> {return true;}).isEmpty();
					}
					
					//List entities = player.world.getEntitiesByType((EntityType) data.get("entity"), new Box(new Vec3d(player.getX() - radius, player.getY() - radius, player.getZ() - radius), new Vec3d(player.getX() + radius, player.getY() + radius, player.getZ() + radius)), (entity) -> {return true;});
					return locatedEntity;
				}));
	}

	private static void register(ConditionFactory<PlayerEntity> conditionFactory) {
		Registry.register(ModRegistries.PLAYER_CONDITION, conditionFactory.getSerializerId(), conditionFactory);
	}

}
