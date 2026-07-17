package com.titammods.quantumcubes.dimension;

import com.titammods.quantumcubes.block.CubeTier;
import com.titammods.quantumcubes.block.entity.QuantumCubeBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.Vec3;

public final class QuantumDimension {

    public static final int SPACING = 2000;

    public static final int FLOOR_Y = 64;

    private QuantumDimension() {
    }

    public static void enter(ServerPlayer player, QuantumCubeBlockEntity be) {
        MinecraftServer server = ((ServerLevel) player.level()).getServer();
        if (server == null) {
            return;
        }
        ServerLevel dimension = server.getLevel(ModDimensions.QUANTUM_LEVEL);
        if (dimension == null) {
            player.sendSystemMessage(Component.translatable("gui.quantumcubes.dim_missing"));
            return;
        }

        CubeTier tier = be.getTier();
        if (!be.isGenerated()) {
            int index = QuantumSavedData.get(dimension).allocate();
            be.setCube(index);
            generateCube(dimension, index, tier);
        }

        QuantumSavedData.get(dimension).putReturn(player.getUUID(), ReturnPoint.of(player));

        Vec3 spawn = interiorSpawn(be.getCubeIndex(), tier);
        teleport(player, dimension, spawn, player.getYRot(), player.getXRot());
    }

    public static void exit(ServerPlayer player) {
        MinecraftServer server = ((ServerLevel) player.level()).getServer();
        if (server == null) {
            return;
        }
        ServerLevel dimension = server.getLevel(ModDimensions.QUANTUM_LEVEL);
        if (dimension == null) {
            return;
        }

        ReturnPoint ret = QuantumSavedData.get(dimension).takeReturn(player.getUUID());
        ServerLevel returnLevel = (ret != null) ? server.getLevel(ret.dimension()) : null;

        ServerLevel target;
        Vec3 pos;
        float yaw;
        float pitch;

        if (returnLevel != null) {
            target = returnLevel;
            pos = ret.pos();
            yaw = ret.yaw();
            pitch = ret.pitch();
        } else {
            target = server.overworld();
            pos = new Vec3(0.5, 128.0, 0.5);
            yaw = 0.0f;
            pitch = 0.0f;
        }

        teleport(player, target, pos, yaw, pitch);
    }

    public static void generateCube(ServerLevel level, int index, CubeTier tier) {
        int size = tier.interiorSize();
        int x0 = index * SPACING;
        int y0 = FLOOR_Y;
        int z0 = 0;
        int x1 = x0 + size + 1;
        int y1 = y0 + size + 1;
        int z1 = z0 + size + 1;

        BlockState bedrock = Blocks.BEDROCK.defaultBlockState();
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();

        for (int x = x0; x <= x1; x++) {
            for (int y = y0; y <= y1; y++) {
                for (int z = z0; z <= z1; z++) {
                    boolean boundary = x == x0 || x == x1 || y == y0 || y == y1 || z == z0 || z == z1;
                    if (boundary) {
                        cursor.set(x, y, z);
                        level.setBlock(cursor, bedrock, Block.UPDATE_CLIENTS);
                    }
                }
            }
        }
    }

    public static Vec3 interiorSpawn(int index, CubeTier tier) {
        int size = tier.interiorSize();
        int x0 = index * SPACING;
        int y0 = FLOOR_Y;
        int z0 = 0;
        double cx = x0 + size / 2.0 + 0.5;
        double cz = z0 + size / 2.0 + 0.5;
        double py = y0 + 1;
        return new Vec3(cx, py, cz);
    }

    private static void teleport(ServerPlayer player, ServerLevel level, Vec3 pos, float yaw, float pitch) {
        player.teleport(new TeleportTransition(
                level, pos, Vec3.ZERO, yaw, pitch, TeleportTransition.DO_NOTHING));
    }
}