package com.titammods.quantumcubes.block.entity;

import com.titammods.quantumcubes.block.CubeTier;
import com.titammods.quantumcubes.block.QuantumCubeBlock;
import com.titammods.quantumcubes.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class QuantumCubeBlockEntity extends BlockEntity {

    private boolean generated = false;
    private int cubeIndex = -1;

    public QuantumCubeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.QUANTUM_CUBE_BE.get(), pos, state);
    }

    public boolean isGenerated() {
        return generated;
    }

    public int getCubeIndex() {
        return cubeIndex;
    }

    public void setCube(int index) {
        this.cubeIndex = index;
        this.generated = true;
        setChanged();
    }

    public CubeTier getTier() {
        if (getBlockState().getBlock() instanceof QuantumCubeBlock block) {
            return block.getTier();
        }
        return CubeTier.TIER_1;
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.putBoolean("generated", generated);
        output.putInt("cube_index", cubeIndex);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        this.generated = input.getBooleanOr("generated", false);
        this.cubeIndex = input.getIntOr("cube_index", -1);
    }
}