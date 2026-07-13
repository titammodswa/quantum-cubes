package com.titammods.quantumcubes.setup;

import com.titammods.quantumcubes.QuantumCubes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = QuantumCubes.MODID, value = Dist.CLIENT)
public class ClientModEvents {

    private ClientModEvents() {
    }
}