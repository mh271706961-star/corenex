package com.coffee.corenex.item;

import com.coffee.corenex.Corenex;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

// 模组物品注册管理类，负责定义和注册所有自定义物品
public class Moditems {
    // 物品延迟注册器实例，用于将模组物品统一注册到游戏注册表
    // DeferredRegister 是 Forge 提供的延迟注册机制，避免注册顺序问题
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(Corenex.MOD_ID);

    // 末影龙鳞物品的延迟注册对象（DeferredItem）
    // 注册名为 "material/dragon_scale"（格式：路径/物品名，确保唯一性）
    // 第二个参数是物品实例的供应器（Supplier），使用默认物品属性（Item.Properties()）创建基础物品
    public static final DeferredItem<Item> DRAGON_SCALE =
            ITEMS.register("material/dragon_scale", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> WITHER_BONE =
            ITEMS.register("material/wither_bone", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CURSE_GOLD_INGOT =
            ITEMS.register("material/curse_gold_ingot", () -> new Item(new Item.Properties()));
    public static final DeferredItem<ArmorItem> DRAGON_SCALE_HELMET = ITEMS.register(
            "dragon_scale_helmet",
            () -> new ArmorItem(ModArmorMaterials.DRAGON_SCALE, ArmorItem.Type.HELMET,  new Item.Properties().stacksTo(1))
    );
    public static final DeferredItem<ArmorItem> DRAGON_SCALE_CHESTPLATE = ITEMS.register(
            "dragon_scale_chestplate",
            () -> new ArmorItem(ModArmorMaterials.DRAGON_SCALE, ArmorItem.Type.CHESTPLATE, new Item.Properties().stacksTo(1))
    );
    public static final DeferredItem<ArmorItem> DRAGON_SCALE_LEGGINGS = ITEMS.register(
            "dragon_scale_leggings",
            () -> new ArmorItem(ModArmorMaterials.DRAGON_SCALE, ArmorItem.Type.LEGGINGS, new Item.Properties().stacksTo(1))
    );
    public static final DeferredItem<ArmorItem> DRAGON_SCALE_BOOTS = ITEMS.register(
            "dragon_scale_boots",
            () -> new ArmorItem(ModArmorMaterials.DRAGON_SCALE, ArmorItem.Type.BOOTS, new Item.Properties().stacksTo(1))
    );






    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
        ModArmorMaterials.ARMOR_MATERIALS.register(eventBus);
    }
}
