package com.titammods.quantumcubes.dimension;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public record ReturnPoint(ResourceKey<Level> dimension, double x, double y, double z, float yaw, float pitch) {

    public static final Codec<ReturnPoint> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceKey.codec(Registries.DIMENSION).fieldOf("dimension").forGetter(ReturnPoint::dimension),
            Codec.DOUBLE.fieldOf("x").forGetter(ReturnPoint::x),
            Codec.DOUBLE.fieldOf("y").forGetter(ReturnPoint::y),
            Codec.DOUBLE.fieldOf("z").forGetter(ReturnPoint::z),
            Codec.FLOAT.fieldOf("yaw").forGetter(ReturnPoint::yaw),
            Codec.FLOAT.fieldOf("pitch").forGetter(ReturnPoint::pitch)
    ).apply(instance, ReturnPoint::new));

    public static ReturnPoint of(ServerPlayer player) {
        Vec3 pos = player.position();
        return new ReturnPoint(player.level().dimension(),
                pos.x, pos.y, pos.z, player.getYRot(), player.getXRot());
    }

    public Vec3 pos() {
        return new Vec3(x, y, z);
    }
}