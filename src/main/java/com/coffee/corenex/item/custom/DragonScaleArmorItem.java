package com.coffee.corenex.item.custom;

import com.coffee.corenex.Corenex;
import com.coffee.corenex.item.Moditems;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DragonScaleArmorItem extends ArmorItem {

    // 为每件盔甲定义唯一的ResourceLocation（用于属性修改器）
    private static final ResourceLocation[] ARMOR_RLS = {
            ResourceLocation.fromNamespaceAndPath(Corenex.MOD_ID, "dragon_scale_helmet_bonus"),
            ResourceLocation.fromNamespaceAndPath(Corenex.MOD_ID, "dragon_scale_chestplate_bonus"),
            ResourceLocation.fromNamespaceAndPath(Corenex.MOD_ID, "dragon_scale_leggings_bonus"),
            ResourceLocation.fromNamespaceAndPath(Corenex.MOD_ID, "dragon_scale_boots_bonus")
    };

    // 全套盔甲生效时的额外生命值ResourceLocation
    private static final ResourceLocation FULL_SET_HEALTH_RL = ResourceLocation.fromNamespaceAndPath(Corenex.MOD_ID, "dragon_scale_full_set_bonus");
    // 全套盔甲生效时的额外伤害ResourceLocation
    private static final ResourceLocation FULL_SET_DAMAGE_RL = ResourceLocation.fromNamespaceAndPath(Corenex.MOD_ID, "dragon_scale_full_set_damage_bonus");

    // 在类中添加计时器字段
    private static final Map<UUID, Integer> PLAYER_COOLDOWNS = new HashMap<>();


    public DragonScaleArmorItem(Holder<ArmorMaterial> material, Type type, Properties properties) {
        super(material, type, properties);
    }




    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);

        // 只在服务端执行，且实体是玩家
        if (!level.isClientSide && entity instanceof Player player) {
        // 检查玩家是否穿戴了完整的末影龙鳞盔甲套装
            boolean hasFullSet = hasFullDragonScaleArmor(player);

            // 应用或移除全套盔甲的生命值加成
            handleFullSetHealthBonus(player, hasFullSet);
            // 应用或移除全套盔甲的伤害加成
            handleFullSetDamageBonus(player, hasFullSet);



            // 检查玩家是否穿戴了完整的末影龙鳞盔甲套装
            if (hasFullDragonScaleArmor(player)) {
                UUID playerId = player.getUUID();
                int cooldown = PLAYER_COOLDOWNS.getOrDefault(playerId, 0);

                if (cooldown <= 0) {

                // 添加生命恢复1效果
                player.addEffect(new MobEffectInstance(
                        MobEffects.REGENERATION,
                        100, // 5秒
                        0,   // I级效果

                        false,
                        false,
                        true
                ));
                // 设置冷却时间（例如5秒，100 ticks）
                PLAYER_COOLDOWNS.put(playerId, 100);
            } else {
                PLAYER_COOLDOWNS.put(playerId, cooldown - 1);
            }


                // 新增的跳跃提升效果（跳跃提升II）
                player.addEffect(new MobEffectInstance(
                        MobEffects.JUMP, // 跳跃提升效果
                        100,             // 持续时间(5秒)
                        1,               // 放大倍数(1=II级效果)
                        false,
                        false,
                        true
                ));
            }
        }
    }

    private boolean hasFullDragonScaleArmor(Player player) {
        // 检查所有装备槽位是否都是末影龙鳞盔甲
        return player.getItemBySlot(EquipmentSlot.HEAD).getItem() == Moditems.DRAGON_SCALE_HELMET.get() &&
                player.getItemBySlot(EquipmentSlot.CHEST).getItem() == Moditems.DRAGON_SCALE_CHESTPLATE.get() &&
                player.getItemBySlot(EquipmentSlot.LEGS).getItem() == Moditems.DRAGON_SCALE_LEGGINGS.get() &&
                player.getItemBySlot(EquipmentSlot.FEET).getItem() == Moditems.DRAGON_SCALE_BOOTS.get();
    }
    private void handleFullSetHealthBonus(Player player, boolean hasFullSet) {
        // 获取玩家的最大生命值属性
        var healthAttribute = player.getAttribute(Attributes.MAX_HEALTH);
        if (healthAttribute == null) return;

        // 检查是否已经应用了全套盔甲的生命值加成
        boolean hasBonus = healthAttribute.getModifier(FULL_SET_HEALTH_RL) != null;

        if (hasFullSet && !hasBonus) {
            // 添加30点最大生命值加成
            healthAttribute.addTransientModifier(new AttributeModifier(
                    FULL_SET_HEALTH_RL,
                    20.0, // 增加20点生命值
                    AttributeModifier.Operation.ADD_VALUE
            ));

            // 确保玩家的当前生命值不会超过新的最大值
            if (player.getHealth() > player.getMaxHealth()) {
                player.setHealth(player.getMaxHealth());
            }
        } else if (!hasFullSet && hasBonus) {
            // 移除生命值加成
            healthAttribute.removeModifier(FULL_SET_HEALTH_RL);
        }
    }
    private void handleFullSetDamageBonus(Player player, boolean hasFullSet) {
        // 获取玩家的攻击伤害属性
        var damageAttribute = player.getAttribute(Attributes.ATTACK_DAMAGE);
        if (damageAttribute == null) return;

        // 检查是否已经应用了全套盔甲的伤害加成
        boolean hasBonus = damageAttribute.getModifier(FULL_SET_DAMAGE_RL) != null;

        if (hasFullSet && !hasBonus) {
            // 添加10点攻击伤害加成
            damageAttribute.addTransientModifier(new AttributeModifier(
                    FULL_SET_DAMAGE_RL,
                    10.0, // 增加10点伤害
                    AttributeModifier.Operation.ADD_VALUE
            ));
        } else if (!hasFullSet && hasBonus) {
            // 移除伤害加成
            damageAttribute.removeModifier(FULL_SET_DAMAGE_RL);
        }
    }



}

