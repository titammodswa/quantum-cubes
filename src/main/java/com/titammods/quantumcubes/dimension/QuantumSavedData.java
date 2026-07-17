package com.titammods.quantumcubes.dimension;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.titammods.quantumcubes.QuantumCubes;
import net.minecraft.core.UUIDUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.jspecify.annotations.Nullable;

public class QuantumSavedData extends SavedData {

    public static final Codec<QuantumSavedData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("next_index").forGetter(data -> data.nextIndex),
            Codec.unboundedMap(UUIDUtil.STRING_CODEC, ReturnPoint.CODEC)
                    .optionalFieldOf("returns", Map.of())
                    .forGetter(data -> data.returns)
    ).apply(instance, QuantumSavedData::new));

    public static final SavedDataType<QuantumSavedData> TYPE = new SavedDataType<QuantumSavedData>(
            QuantumCubes.id("quantum_data"),
            () -> new QuantumSavedData(),
            CODEC,
            null);

    private int nextIndex;
    private final Map<UUID, ReturnPoint> returns;

    public QuantumSavedData() {
        this(0, Map.of());
    }

    public QuantumSavedData(int nextIndex, Map<UUID, ReturnPoint> returns) {
        this.nextIndex = nextIndex;
        this.returns = new HashMap<>(returns);
    }

    public int allocate() {
        int result = nextIndex;
        nextIndex++;
        setDirty();
        return result;
    }

    public void putReturn(UUID player, ReturnPoint point) {
        returns.put(player, point);
        setDirty();
    }

    @Nullable
    public ReturnPoint takeReturn(UUID player) {
        ReturnPoint point = returns.remove(player);
        if (point != null) {
            setDirty();
        }
        return point;
    }

    public static QuantumSavedData get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(TYPE);
    }
}