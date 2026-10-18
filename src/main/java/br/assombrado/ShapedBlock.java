package br.assombrado;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

/** Bloco decorativo com formato menor que um cubo (lampião, vela, totem, rádio). */
public class ShapedBlock extends Block {
    private final VoxelShape shape;

    public ShapedBlock(Settings settings, VoxelShape shape) {
        super(settings);
        this.shape = shape;
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return shape;
    }
}
