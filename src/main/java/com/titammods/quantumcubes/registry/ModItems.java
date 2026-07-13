package com.titammods.quantumcubes.registry;

import com.titammods.quantumcubes.QuantumCubes;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(QuantumCubes.MODID);

    public static final DeferredItem<Item> QUANTUM_REMOTE = ITEMS.registerSimpleItem("quantum_remote");

    static <T extends Block> DeferredItem<BlockItem> registerBlockItem(String name, DeferredHolder<Block, T> block) {
        return ITEMS.registerSimpleBlockItem(name, block);
    }

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }
}