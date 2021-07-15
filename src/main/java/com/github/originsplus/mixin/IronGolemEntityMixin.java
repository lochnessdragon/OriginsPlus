package com.github.originsplus.mixin;

import java.util.List;

import io.github.apace100.apoli.component.PowerHolderComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.github.originsplus.power.ModifyBehavior;
import com.github.originsplus.power.ModifyBehavior.EntityBehavior;

import io.github.apace100.origins.component.OriginComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

@Mixin(IronGolemEntity.class)
public abstract class IronGolemEntityMixin extends GolemEntity implements Angerable {

	IronGolemEntityMixin(EntityType<? extends GolemEntity> entityType, World world) {
		super(entityType, world);
		// TODO Auto-generated constructor stub
	}

	@Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/goal/GoalSelector;add(ILnet/minecraft/entity/ai/goal/Goal;)V", ordinal = 9), method = "initGoals")
	public void overrideFollowTargetGoalForZombies(GoalSelector goalSelector, int priority, Goal goal) {
		Goal newGoal = new FollowTargetGoal(this, PlayerEntity.class, 10, true, false, (entity) -> {
			if(entity instanceof LivingEntity) {
				LivingEntity player = (LivingEntity) entity;
				
				List<ModifyBehavior> powers = PowerHolderComponent.getPowers(player, ModifyBehavior.class);
				powers.removeIf((power) -> {
					return !power.checkEntity(EntityType.IRON_GOLEM);
				});
				
				boolean zombified = false;
				
				if(!powers.isEmpty()) {
					zombified = powers.get(0).getDesiredBehavior() == EntityBehavior.HOSTILE;
				}
				
				return this.shouldAngerAt(player) || zombified;
			}
			
			return false;
		});
		goalSelector.add(priority, newGoal);
	}
	
//    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/goal/GoalSelector;add(ILnet/minecraft/entity/ai/goal/Goal;)V", ordinal = 8), method = "initGoals")
//    private void redirectTargetGoal(GoalSelector goalSelector, int priority, Goal goal) {
//        Goal newGoal = new FollowTargetGoal<PlayerEntity>(this, PlayerEntity.class, 10, true, false, e -> !PowerTypes.SCARE_CREEPERS.isActive(e));
//        goalSelector.add(priority, newGoal);
//    }
	
}
