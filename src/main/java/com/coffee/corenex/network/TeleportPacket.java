package com.coffee.corenex.network;

import com.coffee.corenex.item.Moditems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForgeConfig;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import com.coffee.corenex.Corenex;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.shapes.VoxelShape;

public record TeleportPacket(double forward, double sideways) implements CustomPacketPayload {
    public static final Type<TeleportPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Corenex.MOD_ID, "teleport"));

    public static final StreamCodec<FriendlyByteBuf, TeleportPacket> STREAM_CODEC = StreamCodec.of(
            TeleportPacket::encode,
            TeleportPacket::decode
    );
    private static void encode(FriendlyByteBuf buf, TeleportPacket packet) {
        buf.writeDouble(packet.forward);
        buf.writeDouble(packet.sideways);
    }

    private static TeleportPacket decode(FriendlyByteBuf buf) {
        return new TeleportPacket(buf.readDouble(), buf.readDouble());
    }




    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(TeleportPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer serverPlayer) {
                // 检查是否穿着全套盔甲
                if (hasFullDragonScaleArmor(serverPlayer)) {
                    serverPlayer.getServer().execute(() -> {

                        // 获取玩家视线方向
                        var lookAngle = serverPlayer.getLookAngle();
                        var up = serverPlayer.getUpVector(1.0F);

                        // 计算移动方向向量
                        var forward = lookAngle.scale(packet.forward);
                        var right = lookAngle.cross(up).normalize().scale(packet.sideways);
                        var moveDirection = forward.add(right);
                        // 如果移动方向不是零向量，则归一化并缩放为5格距离
                        if (moveDirection.lengthSqr() > 1.0E-7D) {
                            moveDirection = moveDirection.normalize().scale(5);
                        } else {
                            // 如果没有移动输入，默认向前瞬移
                            moveDirection = lookAngle.scale(5);
                        }


                        // 首先进行视线碰撞检测，防止穿墙
                        Vec3 startPos = serverPlayer.getEyePosition();
                        Vec3 endPos = startPos.add(moveDirection);
                        BlockHitResult hitResult = serverPlayer.level().clip(new ClipContext(
                                startPos,
                                endPos,
                                ClipContext.Block.COLLIDER,
                                ClipContext.Fluid.NONE,
                                serverPlayer
                        ));

                        Vec3 targetPos;
                        if (hitResult.getType() == HitResult.Type.BLOCK) {
                            // 如果视线被方块阻挡，将目标位置调整到碰撞点前方
                            Vec3 normalVec = new Vec3(
                                    hitResult.getDirection().getStepX(),
                                    hitResult.getDirection().getStepY(),
                                    hitResult.getDirection().getStepZ()
                            );
                            targetPos = hitResult.getLocation().add(normalVec.scale(0.5));
                        } else {
                            // 如果没有碰撞，使用计算的目标位置
                            targetPos = serverPlayer.position().add(moveDirection);
                        }
                        // 安全检测：寻找安全的瞬移目标位置
                        Vec3 safeTargetPos = findSafeTeleportLocation(serverPlayer.level(), serverPlayer, targetPos);

                        // 在瞬移前的位置生成粒子
                        if (serverPlayer.level() instanceof ServerLevel serverLevel) {
                            // 修改1: 使用新的方式添加粒子效果
                            serverLevel.sendParticles(
                                    serverPlayer, // 发送给所有能看到该玩家的客户端
                                    ParticleTypes.PORTAL, // 粒子类型
                                    false, // 强制显示（即使玩家看不到）
                                    serverPlayer.getX(), serverPlayer.getY() + 1, serverPlayer.getZ(), // 位置
                                    20, // 数量
                                    0.5, 0.5, 0.5, // 偏移量
                                    0.1 // 速度
                            );
                        }

                        // 传送到目标位置
                        serverPlayer.teleportTo(targetPos.x, targetPos.y, targetPos.z);
                        // 传送到安全的目标位置
                        serverPlayer.teleportTo(safeTargetPos.x, safeTargetPos.y, safeTargetPos.z);

                        // 在瞬移后的位置生成粒子
                        if (serverPlayer.level() instanceof ServerLevel serverLevel) {
                            // 修改2: 使用新的方式添加粒子效果
                            serverLevel.sendParticles(
                                    serverPlayer,
                                    ParticleTypes.PORTAL,
                                    false,
                                    targetPos.x, targetPos.y + 1, targetPos.z,
                                    20,
                                    0.5, 0.5, 0.5,
                                    0.1
                            );
                        }

                        // 通过数据包通知客户端播放音效
                        serverPlayer.level().playSound(
                                null,
                                serverPlayer.getX(),
                                serverPlayer.getY(),
                                serverPlayer.getZ(),
                                SoundEvents.ENDERMAN_TELEPORT,
                                serverPlayer.getSoundSource(),
                                1.0F,
                                1.0F
                        );
                    });
                }
            }
        });
    }

    // 添加检查全套盔甲的方法
    private static boolean hasFullDragonScaleArmor(ServerPlayer player) {
        return player.getItemBySlot(EquipmentSlot.HEAD).getItem() == Moditems.DRAGON_SCALE_HELMET.get() &&
                player.getItemBySlot(EquipmentSlot.CHEST).getItem() == Moditems.DRAGON_SCALE_CHESTPLATE.get() &&
                player.getItemBySlot(EquipmentSlot.LEGS).getItem() == Moditems.DRAGON_SCALE_LEGGINGS.get() &&
                player.getItemBySlot(EquipmentSlot.FEET).getItem() == Moditems.DRAGON_SCALE_BOOTS.get();
    }


    /**
     * 寻找安全的瞬移位置（防止卡在方块内）
     * 改进版本：更好地处理看向地面或墙壁的情况
     */
    private static Vec3 findSafeTeleportLocation(Level level, Player player, Vec3 targetPos) {
        // 首先检查目标位置是否安全
        if (isPositionSafe(level, player, targetPos)) {
            return targetPos;
        }

        // 如果不安全，尝试在目标位置周围寻找安全位置
        Vec3 safePos = findNearbySafePosition(level, player, new BlockPos((int)targetPos.x, (int)targetPos.y, (int)targetPos.z));
        if (safePos != null && isPositionSafe(level, player, safePos)) {
            return safePos;
        }

        // 如果周围没有安全位置，尝试向上寻找
        for (int yOffset = 1; yOffset <= 3; yOffset++) {
            Vec3 higherPos = new Vec3(targetPos.x, targetPos.y + yOffset, targetPos.z);
            if (isPositionSafe(level, player, higherPos)) {
                return higherPos;
            }
        }

        // 如果向上寻找失败，尝试向下寻找
        for (int yOffset = 1; yOffset <= 3; yOffset++) {
            Vec3 lowerPos = new Vec3(targetPos.x, targetPos.y - yOffset, targetPos.z);
            if (isPositionSafe(level, player, lowerPos)) {
                return lowerPos;
            }
        }

        // 如果所有尝试都失败，返回原始位置（作为最后的手段）
        return targetPos;
    }

    /**
     * 检查位置是否安全（玩家不会卡在方块内）
     */
    private static boolean isPositionSafe(Level level, Player player, Vec3 position) {
        AABB playerBounds = player.getBoundingBox().move(position.subtract(player.position()));

        // 检查玩家边界框内是否有碰撞方块
        boolean noCollision = level.noCollision(player, playerBounds);

        // 检查是否在液体中
        boolean noLiquid = !level.containsAnyLiquid(playerBounds);

        // 检查下方是否有坚实地面（但允许在空中，如果玩家有意瞬移到空中）
        boolean hasGroundBelow = hasSolidGroundBelow(level, position, 3) ||
                position.y > player.getY(); // 如果目标位置比当前位置高，允许在空中

        return noCollision && noLiquid && hasGroundBelow;
    }

    /**
     * 检查下方是否有坚实地面
     */
    private static boolean hasSolidGroundBelow(Level level, Vec3 position, int maxSearchDistance) {
        for (int i = 1; i <= maxSearchDistance; i++) {
            BlockPos checkPos = BlockPos.containing(position.x, position.y - i, position.z);
            if (!level.getBlockState(checkPos).isAir() &&
                    level.getBlockState(checkPos).isSolid()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 在周围寻找安全位置
     */
    private static Vec3 findNearbySafePosition(Level level, Player player, BlockPos centerPos) {
        // 在5x5x5的范围内搜索安全位置
        for (int x = -2; x <= 2; x++) {
            for (int y = -2; y <= 2; y++) {
                for (int z = -2; z <= 2; z++) {
                    BlockPos checkPos = centerPos.offset(x, y, z);
                    Vec3 checkVec = new Vec3(checkPos.getX() + 0.5, checkPos.getY(), checkPos.getZ() + 0.5);

                    if (isPositionSafe(level, player, checkVec)) {
                        return checkVec;
                    }
                }
            }
        }

        // 如果没找到安全位置，返回null
        return null;
    }
}