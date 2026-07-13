package com.titammods.quantumcubes.dimension;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.titammods.quantumcubes.QuantumCubes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

public class QuantumSavedData extends SavedData {

    public static final Codec<QuantumSavedData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("next_index").forGetter(data -> data.nextIndex)
    ).apply(instance, QuantumSavedData::new));

    public static final SavedDataType<QuantumSavedData> TYPE = new SavedDataType<QuantumSavedData>(
            QuantumCubes.id("quantum_data"),
            () -> new QuantumSavedData(),
            CODEC,
            null);

    private int nextIndex;

    public QuantumSavedData() {
    }

    public QuantumSavedData(int nextIndex) {
        this.nextIndex = nextIndex;
    }

    public int allocate() {
        int result = nextIndex;
        nextIndex++;
        setDirty();
        return result;
    }

    public static QuantumSavedData get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(TYPE);
    }
}