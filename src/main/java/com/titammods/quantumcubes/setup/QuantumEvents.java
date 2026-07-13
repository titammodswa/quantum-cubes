package com.titammods.quantumcubes.setup;

import com.titammods.quantumcubes.QuantumCubes;
import com.titammods.quantumcubes.dimension.ModDimensions;
import com.titammods.quantumcubes.dimension.QuantumDimension;
import net.minecraft.server.level.ServerPlayer;
import com.titammods.quantumcubes.registry.ModItems;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = QuantumCubes.MODID)
public final class QuantumEvents {

    private QuantumEvents() {
    }

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }
        if (!player.level().dimension().equals(ModDimensions.QUANTUM_LEVEL)) {
            return;
        }
        if (!event.getItemStack().is(ModItems.QUANTUM_REMOTE.get())) {
            return;
        }
        QuantumDimension.exit(player);
        event.setCanceled(true);
    }
}