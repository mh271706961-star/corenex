package com.coffee.corenex.potions;

import com.coffee.corenex.Corenex;
import com.coffee.corenex.effect.ModEffects;
import com.coffee.corenex.item.Moditems;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModPotions {

    public static DeferredRegister<Potion> POTIONS = DeferredRegister.create(Registries.POTION, Corenex.MOD_ID);

    public static DeferredHolder<Potion, Potion> CURSE_POTION = registerPotion("curse_potion",3600,5, ModEffects.CURSE);


    public static void register(IEventBus eventBus){
        POTIONS.register(eventBus);
    }

    public static DeferredHolder<Potion, Potion> registerPotion(String name, int duration, int amplifier, Holder<MobEffect> statusEffects) {
        return POTIONS.register(name,()-> new Potion(new MobEffectInstance(statusEffects,duration,amplifier)));
    }

    @EventBusSubscriber(modid = Corenex.MOD_ID)
    public class registerPotionsBrewingEvent {

        @SubscribeEvent
        public static void onRegisterPotionsBrewing(RegisterBrewingRecipesEvent event){ // 注册药水的合成表的事件
            PotionBrewing.Builder builder = event.getBuilder();

            // 诅咒药水
            builder.addMix(Potions.AWKWARD, // 使用的药水,使用的材料,获得的药水
                    Moditems.CURSE_SOUL.get(), ModPotions.CURSE_POTION);


        }
    }
}
