package com.github.originsplus.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.originsplus.events.BlockDropCallback;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(Block.class)
public class BlockMixin {
	  
	@Inject(at = @At(value = "HEAD"), method = "Lnet/minecraft/block/Block;onBreak(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/entity/player/PlayerEntity;)V", cancellable = true)
	public void onStacksDropped(final World world, final BlockPos pos, final BlockState state, final PlayerEntity player, final CallbackInfo info) {
        ActionResult result = BlockDropCallback.EVENT.invoker().onBreak((Block) (Object) this, state, world, pos, player);
        
        if(result == ActionResult.FAIL) {
            info.cancel();
        }
	}
}
