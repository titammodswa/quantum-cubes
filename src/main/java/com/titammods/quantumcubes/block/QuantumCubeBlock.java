package com.titammods.quantumcubes.block;

import com.titammods.quantumcubes.block.entity.QuantumCubeBlockEntity;
import com.titammods.quantumcubes.dimension.QuantumDimension;
import com.titammods.quantumcubes.registry.ModDataComponents;
import com.titammods.quantumcubes.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import org.jspecify.annotations.Nullable;

public class QuantumCubeBlock extends Block implements EntityBlock {

    private final CubeTier tier;

    public QuantumCubeBlock(Properties properties, CubeTier tier) {
        super(properties);
        this.tier = tier;
    }

    public CubeTier getTier() {
        return tier;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new QuantumCubeBlockEntity(pos, state);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
                                          Player player, InteractionHand hand, BlockHitResult hit) {
        if (!stack.is(ModItems.QUANTUM_REMOTE.get())) {
            return InteractionResult.PASS;
        }
        if (!level.isClientSide()
                && player instanceof ServerPlayer serverPlayer
                && level.getBlockEntity(pos) instanceof QuantumCubeBlockEntity be) {
            QuantumDimension.enter(serverPlayer, be);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state,
                            @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        if (level.isClientSide()) {
            return;
        }
        Integer boundIndex = stack.get(ModDataComponents.CUBE_INDEX.get());
        if (boundIndex != null
                && level.getBlockEntity(pos) instanceof QuantumCubeBlockEntity be) {
            be.setCube(boundIndex);
        }
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide() && !player.isCreative()) {
            ItemStack stack = new ItemStack(this);
            if (level.getBlockEntity(pos) instanceof QuantumCubeBlockEntity be && be.isGenerated()) {
                stack.set(ModDataComponents.CUBE_INDEX.get(), be.getCubeIndex());
            }
            popResource(level, pos, stack);
        }
        return super.playerWillDestroy(level, pos, state, player);
    }
}