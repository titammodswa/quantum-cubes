package com.titammods.quantumcubes.registry;

import com.titammods.quantumcubes.QuantumCubes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, QuantumCubes.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> QUANTUM_CUBES_TAB = CREATIVE_TABS.register(
            "quantumcubes_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.quantumcubes"))
                    .icon(() -> new ItemStack(ModBlocks.TIER_1.get()))
                    .displayItems((params, output) -> {
                        output.accept(ModItems.QUANTUM_REMOTE.get());
                        output.accept(ModBlocks.TIER_1.get());
                        output.accept(ModBlocks.TIER_2.get());
                        output.accept(ModBlocks.TIER_3.get());
                        output.accept(ModBlocks.TIER_4.get());
                        output.accept(ModBlocks.TIER_5.get());
                        output.accept(ModBlocks.QUANTUM_BLOCK.get());
                    })
                    .build());

    public static void register(IEventBus modEventBus) {
        CREATIVE_TABS.register(modEventBus);
    }
}