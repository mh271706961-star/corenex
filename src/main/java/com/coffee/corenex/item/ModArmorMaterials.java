package com.coffee.corenex.item;

import com.coffee.corenex.Corenex;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;

public class ModArmorMaterials {
    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS =
            DeferredRegister.create(BuiltInRegistries.ARMOR_MATERIAL, Corenex.MOD_ID);

    public static final Holder<ArmorMaterial> DRAGON_INGOT =
            ARMOR_MATERIALS.register("dragon_ingot", () -> new ArmorMaterial(
                    Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                        map.put(ArmorItem.Type.BOOTS, 5);
                        map.put(ArmorItem.Type.LEGGINGS, 8);
                        map.put(ArmorItem.Type.CHESTPLATE, 10);
                        map.put(ArmorItem.Type.HELMET, 5);
                        map.put(ArmorItem.Type.BODY, 4);
                    }),
                    30,
                    SoundEvents.ARMOR_EQUIP_GENERIC,
                    () -> Ingredient.of(Moditems.DRAGON_INGOT),
                    List.of(
                            new ArmorMaterial.Layer(
                                    ResourceLocation.fromNamespaceAndPath(Corenex.MOD_ID, "dragon_ingot")
                            )
//                            new ArmorMaterial.Layer(
//                                    ResourceLocation.fromNamespaceAndPath(Corenex.MOD_ID, "dragon_scale"), "_overlay", true
//                            )
                    ),
                    5,
                    0.2f

            ));




    // 注意：下面的 register 方法不再需要，因为 DeferredRegister 会自动处理注册
    // 您可以删除这个方法，或者保留它作为辅助方法（但需要修改参数顺序）
}

