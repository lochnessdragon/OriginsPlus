package com.github.originsplus.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.originsplus.entity.IGrappleHook;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

@Mixin(FishingBobberEntity.class)
public abstract class FishingBobberEntityMixin implements IGrappleHook {
	
	boolean grappler = false;
	
	FishingBobberEntity previousFishHook;

	@Override
	public boolean isGrapple() {
		// TODO Auto-generated method stub
		return grappler;
	}

	@Override
	public void setGrapple(boolean grapple) {
		this.grappler = grapple;
	}

	@Inject(at = @At(value = "HEAD"), method = "removeIfInvalid", cancellable = true)
	public void checkGrappleInvalid(final PlayerEntity player, CallbackInfoReturnable<Boolean> info) {
		FishingBobberEntity entity = (FishingBobberEntity) (Object) this;

		if (((IGrappleHook) entity).isGrapple()) {
			info.setReturnValue(false);
		}
	}

	@Inject(at = @At(value = "HEAD"), method = "tickFishingLogic", cancellable = true)
	public void preventGrappleFishingLogic(BlockPos pos, CallbackInfo info) {
		FishingBobberEntity entity = (FishingBobberEntity) (Object) this;

		if (((IGrappleHook) entity).isGrapple()) {
			info.cancel();
		}
	}
	
	@Inject(at = @At(value = "HEAD"), method = "use", cancellable = true)
	public void preventGrappleFishingUse(ItemStack stack, CallbackInfoReturnable<Integer> info) {
		FishingBobberEntity entity = (FishingBobberEntity) (Object) this;

		if (((IGrappleHook) entity).isGrapple()) {
			info.setReturnValue(0);
		}
	}
	
	@Inject(at = @At(value = "HEAD"), method = "remove", cancellable = true)
	public void blockRemoveIfGrappleA(CallbackInfo info) {
		FishingBobberEntity entity = (FishingBobberEntity) (Object) this;
		
		if (((IGrappleHook) entity).isGrapple()) {
			if(entity.getPlayerOwner() != null) {
				previousFishHook = entity.getPlayerOwner().fishHook;
			}
		}
	}
	
	@Inject(at = @At(value = "RETURN"), method = "remove", cancellable = true)
	public void blockRemoveIfGrappleB(CallbackInfo info) {
		FishingBobberEntity entity = (FishingBobberEntity) (Object) this;
		
		if (((IGrappleHook) entity).isGrapple()) {
			if(entity.getPlayerOwner() != null) {
				entity.getPlayerOwner().fishHook = previousFishHook;
			}
		}
	}

}
