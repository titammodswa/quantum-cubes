package com.titammods.quantumcubes.registry;

import com.titammods.quantumcubes.QuantumCubes;
import com.titammods.quantumcubes.block.entity.QuantumCubeBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, QuantumCubes.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<QuantumCubeBlockEntity>> QUANTUM_CUBE_BE =
            BLOCK_ENTITIES.register("quantum_cube", () -> new BlockEntityType<>(
                    QuantumCubeBlockEntity::new,
                    ModBlocks.TIER_1.get(),
                    ModBlocks.TIER_2.get(),
                    ModBlocks.TIER_3.get(),
                    ModBlocks.TIER_4.get(),
                    ModBlocks.TIER_5.get()
            ));

    public static void register(IEventBus modEventBus) {
        BLOCK_ENTITIES.register(modEventBus);
    }
}