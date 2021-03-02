package com.github.originsplus.power;

import com.google.common.base.Function;

import io.github.apace100.origins.power.Power;
import io.github.apace100.origins.power.PowerType;
import net.minecraft.entity.player.PlayerEntity;

public class BlindnessPower extends Power {

    private final Function<PlayerEntity, Float> strengthFunction;

    public BlindnessPower(PowerType<?> type, PlayerEntity player) {
        this(type, player, 1.0F);
    }

    public BlindnessPower(PowerType<?> type, PlayerEntity player, float strength) {
        this(type, player, pe -> strength);
    }

    public BlindnessPower(PowerType<?> type, PlayerEntity player, Function<PlayerEntity, Float> strengthFunction) {
        super(type, player);
        this.strengthFunction = strengthFunction;
    }

    public float getStrength() {
        return strengthFunction.apply(this.player);
    }

}
