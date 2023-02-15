package com.github.originsplus.mixin;

import com.github.originsplus.power.BlockPlayerSleep;
import com.mojang.datafixers.util.Either;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Unit;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {
	
    @Inject(at = @At("HEAD"), method = "trySleep", cancellable = true)
    public void preventPlayerSleepNearZombified(BlockPos pos, CallbackInfoReturnable<Either<PlayerEntity.SleepFailureReason, Unit>> info) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        double horizontalStretch = 8.0D;
        double verticalStretch = 5.0D;
        Vec3d vec3d = Vec3d.ofBottomCenter(pos);
        List<PlayerEntity> list = player.world.getEntitiesByClass(PlayerEntity.class, new Box(vec3d.getX() - horizontalStretch, vec3d.getY() - verticalStretch, vec3d.getZ() - horizontalStretch, vec3d.getX() + horizontalStretch, vec3d.getY() + verticalStretch, vec3d.getZ() + horizontalStretch), (playerEntity) -> PowerHolderComponent.hasPower(playerEntity, BlockPlayerSleep.class) && !playerEntity.isCreative());
        if (!list.isEmpty() && !list.contains((PlayerEntity) (Object) this)) {
           info.setReturnValue(Either.left(PlayerEntity.SleepFailureReason.NOT_SAFE));
        }
    }

}
