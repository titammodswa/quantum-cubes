package com.titammods.quantumcubes;

import com.titammods.quantumcubes.registry.ModBlockEntities;
import com.titammods.quantumcubes.registry.ModBlocks;
import com.titammods.quantumcubes.registry.ModCreativeTabs;
import com.titammods.quantumcubes.registry.ModDataComponents;
import com.titammods.quantumcubes.registry.ModItems;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(QuantumCubes.MODID)
public class QuantumCubes {

    public static final String MODID = "quantumcubes";

    public QuantumCubes(IEventBus modEventBus) {
        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);
        ModDataComponents.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModCreativeTabs.register(modEventBus);
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MODID, path);
    }
}