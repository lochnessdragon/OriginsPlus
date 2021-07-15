package com.github.originsplus.power;

import com.google.common.base.Function;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public class BlindnessPower extends Power {

    private final Function<LivingEntity, Float> strengthFunction;

    public BlindnessPower(PowerType<?> type, PlayerEntity player) {
        this(type, player, 1.0F);
    }

    public BlindnessPower(PowerType<?> type, PlayerEntity player, float strength) {
        this(type, player, pe -> strength);
    }

    public BlindnessPower(PowerType<?> type, PlayerEntity player, Function<LivingEntity, Float> strengthFunction) {
        super(type, player);
        this.strengthFunction = strengthFunction;
    }

    public float getStrength() {
        return strengthFunction.apply(this.entity);
    }

}
