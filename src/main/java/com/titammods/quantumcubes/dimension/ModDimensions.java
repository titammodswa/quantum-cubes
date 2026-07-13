package com.titammods.quantumcubes.dimension;

import com.titammods.quantumcubes.QuantumCubes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;

public final class ModDimensions {

    public static final ResourceKey<Level> QUANTUM_LEVEL =
            ResourceKey.create(Registries.DIMENSION, QuantumCubes.id("quantum_dimension"));

    public static final ResourceKey<DimensionType> QUANTUM_TYPE =
            ResourceKey.create(Registries.DIMENSION_TYPE, QuantumCubes.id("quantum_dimension"));

    private ModDimensions() {
    }
}