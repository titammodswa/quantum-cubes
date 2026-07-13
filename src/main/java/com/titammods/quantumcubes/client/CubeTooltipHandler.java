package com.titammods.quantumcubes.client;

import com.titammods.quantumcubes.QuantumCubes;
import com.titammods.quantumcubes.block.CubeTier;
import com.titammods.quantumcubes.block.QuantumCubeBlock;
import com.titammods.quantumcubes.registry.ModDataComponents;
import com.titammods.quantumcubes.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.List;

@EventBusSubscriber(modid = QuantumCubes.MODID, value = Dist.CLIENT)
public final class CubeTooltipHandler {

    private CubeTooltipHandler() {
    }

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        if (!(event.getItemStack().getItem() instanceof BlockItem blockItem)) {
            return;
        }
        if (!(blockItem.getBlock() instanceof QuantumCubeBlock cube)) {
            return;
        }

        CubeTier tier = cube.getTier();
        int s = tier.interiorSize();
        List<Component> lines = event.getToolTip();

        lines.add(Component.translatable("tooltip.quantumcubes.interior",
                        Component.literal(s + " × " + s + " × " + s).withStyle(tier.accentColor()))
                .withStyle(ChatFormatting.GRAY));

        lines.add(Component.translatable("tooltip.quantumcubes.desc")
                .withStyle(ChatFormatting.DARK_GRAY));

        Integer boundIndex = event.getItemStack().get(ModDataComponents.CUBE_INDEX.get());
        if (boundIndex != null) {
            lines.add(Component.translatable("tooltip.quantumcubes.bound",
                            Component.literal("#" + boundIndex).withStyle(tier.accentColor()))
                    .withStyle(ChatFormatting.GRAY));
        }

        lines.add(Component.translatable("tooltip.quantumcubes.enter",
                        Component.translatable(ModItems.QUANTUM_REMOTE.get().getDescriptionId())
                                .withStyle(ChatFormatting.AQUA))
                .withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
    }
}