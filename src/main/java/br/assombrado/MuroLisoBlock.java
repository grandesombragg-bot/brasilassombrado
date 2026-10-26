package br.assombrado;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

/** Muro liso (sem cacos). A colisão é igual à do muro com cacos, para quem encosta tocar a fileira de cima. */
public class MuroLisoBlock extends Block {
    private static final VoxelShape COLLISION = Block.createCuboidShape(1, 0, 1, 15, 16, 15);

    public MuroLisoBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return COLLISION;
    }
}
