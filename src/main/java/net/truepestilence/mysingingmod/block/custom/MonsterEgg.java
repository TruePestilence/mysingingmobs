package net.truepestilence.mysingingmod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MonsterEgg extends Block {
    public MonsterEgg(Properties properties) {
        super(properties);
    }
    private static final VoxelShape SHAPE = Block.box(6, 0, 5, 10, 3, 11);

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return SHAPE;
    }
}
