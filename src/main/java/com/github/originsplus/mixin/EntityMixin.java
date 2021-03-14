package com.github.originsplus.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.originsplus.events.EntityInteractCallback;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

@Mixin(Entity.class)
public class EntityMixin {
	
	@Inject(at = @At(value = "HEAD"), method = "interact(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;", cancellable = true)
	public void onInteract(final PlayerEntity player, final Hand hand, CallbackInfoReturnable<ActionResult> info) {
		ActionResult result = EntityInteractCallback.EVENT.invoker().onInteract(player, hand, (Entity) (Object) this);
	
		if(result == ActionResult.FAIL) {
			info.cancel();
		}
	}
	
}
