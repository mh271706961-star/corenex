package com.coffee.corenex.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

import net.minecraft.world.entity.LivingEntity;

public class CurseEffect  extends MobEffect {
    public CurseEffect(MobEffectCategory category, int color) {
        super(category, color);
    }



    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        if (!entity.level().isClientSide) {

            // 添加效果
            entity.addEffect(new MobEffectInstance(MobEffects.WITHER, 200, 1, false, false, false)); // 凋零
            entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0, false, false, false)); // 反胃
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 1, false, false, false)); // 缓慢
            entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 200, 0, false, false, false)); // 失明
            entity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 200, 0, false, false, false)); // 饥饿
            entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 200, 10, false, false, false)); // 力量

        }
        return super.applyEffectTick(entity, amplifier);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }




}
