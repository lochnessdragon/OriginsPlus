package com.github.originsplus.registry;

import java.util.List;

import com.github.originsplus.OriginsPlus;

import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataType;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registry;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class ModEntityConditions {

	@SuppressWarnings("unchecked")
	public static void register() {
		register(new ConditionFactory<>(
				// entities_in_radius:
				//   returns true if any of the specified entity types (minecraft ids) are in the specified radius
				OriginsPlus.identifier("entities_in_radius"), new SerializableData()
						.add("entities", SerializableDataType.list(SerializableDataTypes.ENTITY_TYPE)).add("radius", SerializableDataTypes.FLOAT),
				(data, player) -> {
					float radius = data.getFloat("radius");
					List<EntityType<?>> entityList = (List<EntityType<?>>) data.get("entities");
					
					boolean locatedEntity = false;
					
					// potential optimization: early return?
					for(EntityType type : entityList) {
						locatedEntity = locatedEntity || !player.world.getEntitiesByType(type, 
															new Box(new Vec3d(player.getX() - radius, player.getY() - radius, player.getZ() - radius), 
															new Vec3d(player.getX() + radius, player.getY() + radius, player.getZ() + radius)), 
															(entity) -> {return true;}).isEmpty();
					}
					
					return locatedEntity;
				}));
	}

    private static void register(ConditionFactory<Entity> conditionFactory) {
        Registry.register(ApoliRegistries.ENTITY_CONDITION, conditionFactory.getSerializerId(), conditionFactory);
    }
}
