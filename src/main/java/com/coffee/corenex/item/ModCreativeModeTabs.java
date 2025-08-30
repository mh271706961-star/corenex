package com.coffee.corenex.item;

import com.coffee.corenex.Corenex;
import com.coffee.corenex.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Corenex.MOD_ID);

        // 模组自定义创造模式标签页的供应商（Supplier），用于延迟创建和注册标签页实例
    public static final Supplier<CreativeModeTab> Corenex_TAB =
            // 注册自定义创造模式标签页到游戏注册表，注册名为"corenex_tab"（需确保唯一）
            CREATIVE_MODE_TABS.register("corenex_tab", () -> CreativeModeTab.builder()
                    // 设置标签页在创造模式物品栏顶部显示的图标（此处使用末影龙鳞物品）
                    .icon(() -> new ItemStack(Moditems.DRAGON_SCALE.get()))
                    // 设置标签页的显示名称（使用国际化键值，支持多语言翻译）
                    .title(Component.translatable("itemgroup.corenex_tab"))
                    // 定义标签页中显示的物品列表
                    .displayItems((parameters, output) -> {
                          // 向标签页中添加"末影龙鳞"物品（可在此处继续添加其他模组物品）
                          output.accept(Moditems.DRAGON_SCALE);
                          output.accept(Moditems.WITHER_BONE);
                          output.accept(Moditems.CURSE_GOLD_INGOT);
                          output.accept(Moditems.DRAGON_SCALE_HELMET);
                          output.accept(Moditems.DRAGON_SCALE_CHESTPLATE);
                          output.accept(Moditems.DRAGON_SCALE_LEGGINGS);
                          output.accept(Moditems.DRAGON_SCALE_BOOTS);

                          output.accept(ModBlocks.DRAGON_SCALE_BLOCK);


                    }).build());

    public static void resgister(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}

