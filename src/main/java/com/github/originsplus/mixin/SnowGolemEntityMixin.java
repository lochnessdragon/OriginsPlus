package com.github.originsplus.mixin;

import java.util.List;

import io.github.apace100.apoli.component.PowerHolderComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.originsplus.power.ModifyBehavior;
import com.github.originsplus.power.ModifyBehavior.EntityBehavior;

import io.github.apace100.origins.component.OriginComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Shearable;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

@Mixin(SnowGolemEntity.class)
public abstract class SnowGolemEntityMixin extends GolemEntity implements Shearable, RangedAttackMob {

	SnowGolemEntityMixin(EntityType<? extends GolemEntity> entityType, World world) {
		super(entityType, world);
		// TODO Auto-generated constructor stub
	}
	
	@Inject(at = @At(value = "RETURN"), method = "initGoals", cancellable = true)
	public void initPlayerZombieGoals(CallbackInfo info) {
		this.targetSelector.add(1, new FollowTargetGoal(this, PlayerEntity.class, 10, true, false, (entity) -> {
			if(entity instanceof PlayerEntity player) {
				List<ModifyBehavior> powers = PowerHolderComponent.getPowers(player, ModifyBehavior.class);
				powers.removeIf((power) -> !power.checkEntity(this.getType()));
			
				if (!powers.isEmpty()) {
					ModifyBehavior.EntityBehavior behavior = powers.get(0).getDesiredBehavior();
					return behavior == EntityBehavior.HOSTILE;
				}
			}
			return false;
		}));
	}
}
