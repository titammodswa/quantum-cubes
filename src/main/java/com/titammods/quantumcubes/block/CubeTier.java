package com.titammods.quantumcubes.block;

import net.minecraft.ChatFormatting;

public enum CubeTier {
    TIER_1("quantum_cube_tier_1", 20, ChatFormatting.GRAY),
    TIER_2("quantum_cube_tier_2", 40, ChatFormatting.WHITE),
    TIER_3("quantum_cube_tier_3", 64, ChatFormatting.GOLD),
    TIER_4("quantum_cube_tier_4", 96, ChatFormatting.AQUA),
    TIER_5("quantum_cube_tier_5", 128, ChatFormatting.GREEN);

    private final String blockName;
    private final int interiorSize;
    private final ChatFormatting accentColor;

    CubeTier(String blockName, int interiorSize, ChatFormatting accentColor) {
        this.blockName = blockName;
        this.interiorSize = interiorSize;
        this.accentColor = accentColor;
    }

    public String blockName() {
        return blockName;
    }

    public int interiorSize() {
        return interiorSize;
    }

    public ChatFormatting accentColor() {
        return accentColor;
    }
}