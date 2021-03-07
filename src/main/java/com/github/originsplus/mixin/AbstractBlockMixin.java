package com.github.originsplus.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.originsplus.events.BlockDropCallback;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;

@Mixin(AbstractBlock.class)
public class AbstractBlockMixin {
	  
	@Inject(at = @At(value = "HEAD"), method = "Lnet/minecraft/block/AbstractBlock;onStacksDropped()V", cancellable = true)
	public void onStacksDropped(final BlockState state, final ServerWorld world, final BlockPos pos, final ItemStack stack, final CallbackInfo info) {
        ActionResult result = BlockDropCallback.EVENT.invoker().onStacksDropped((AbstractBlock) (Object) this, state, world, pos, stack);
        
        if(result == ActionResult.FAIL) {
            info.cancel();
        }
	}
}
