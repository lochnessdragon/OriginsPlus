package com.github.originsplus.registry;

import java.util.List;

import com.github.originsplus.OriginsPlus;

import io.github.apace100.origins.power.factory.condition.ConditionFactory;
import io.github.apace100.origins.registry.ModRegistries;
import io.github.apace100.origins.util.SerializableData;
import io.github.apace100.origins.util.SerializableDataType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;

public class ModEntityConditions {

	@SuppressWarnings("unchecked")
	public static void register() {
		register(new ConditionFactory<>(
				OriginsPlus.identifier("entities_in_radius"), new SerializableData()
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

    private static void register(ConditionFactory<LivingEntity> conditionFactory) {
        Registry.register(ModRegistries.ENTITY_CONDITION, conditionFactory.getSerializerId(), conditionFactory);
    }
}
