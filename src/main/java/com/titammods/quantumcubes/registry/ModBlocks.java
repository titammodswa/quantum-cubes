package com.titammods.quantumcubes.registry;

import com.titammods.quantumcubes.QuantumCubes;
import com.titammods.quantumcubes.block.CubeTier;
import com.titammods.quantumcubes.block.QuantumCubeBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

public class ModBlocks {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(QuantumCubes.MODID);

    public static final DeferredBlock<QuantumCubeBlock> TIER_1 = registerTier(CubeTier.TIER_1);
    public static final DeferredBlock<QuantumCubeBlock> TIER_2 = registerTier(CubeTier.TIER_2);
    public static final DeferredBlock<QuantumCubeBlock> TIER_3 = registerTier(CubeTier.TIER_3);
    public static final DeferredBlock<QuantumCubeBlock> TIER_4 = registerTier(CubeTier.TIER_4);
    public static final DeferredBlock<QuantumCubeBlock> TIER_5 = registerTier(CubeTier.TIER_5);

    private static DeferredBlock<QuantumCubeBlock> registerTier(CubeTier tier) {
        return registerBlock(tier.blockName(), key -> new QuantumCubeBlock(
                BlockBehaviour.Properties.of()
                        .setId(key)
                        .strength(2.0f, 6.0f)
                        .sound(SoundType.STONE)
                        .noOcclusion()
                        .noLootTable(),
                tier));
    }

    static <T extends Block> DeferredBlock<T> registerBlock(String name, Function<ResourceKey<Block>, T> factory) {
        ResourceKey<Block> key = ResourceKey.create(Registries.BLOCK, QuantumCubes.id(name));
        DeferredBlock<T> block = BLOCKS.register(name, () -> factory.apply(key));
        ModItems.registerBlockItem(name, block);
        return block;
    }

    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
    }
}