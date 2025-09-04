package com.coffee.corenex.effect;

import com.coffee.corenex.Corenex;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEffects {
    // 获得药水效果的注册器
    public static DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, Corenex.MOD_ID);
    // 注册诅咒效果
    public static final DeferredHolder<MobEffect, MobEffect> CURSE =
            registerDeferredHolder("curse", () -> new CurseEffect(MobEffectCategory.HARMFUL, 0x8B0000));

    // 辅助的注册的方法
    public static DeferredHolder<MobEffect, MobEffect> registerDeferredHolder(String name, Supplier<MobEffect> supplier) {
        return EFFECTS.register(name, supplier);
    }

    // 将使用的的注册器添加到总线
    public static void register(IEventBus eventBus) {
        EFFECTS.register(eventBus);
    }
}


