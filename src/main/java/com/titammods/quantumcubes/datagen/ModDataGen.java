package com.titammods.quantumcubes.datagen;

import com.titammods.quantumcubes.QuantumCubes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = QuantumCubes.MODID)
public final class ModDataGen {

    private ModDataGen() {
    }

    @SubscribeEvent
    public static void gatherServerData(GatherDataEvent.Server event) {
        event.createProvider(ModRecipeProvider.Runner::new);
    }
}