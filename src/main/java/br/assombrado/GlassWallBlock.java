package br.assombrado;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

/** Muro com cacos de vidro: machuca quem encosta (como um cacto). Criaturas muito pequenas evitam o muro. */
public class GlassWallBlock extends Block {
    private static final VoxelShape COLLISION = Block.createCuboidShape(1, 0, 1, 15, 16, 15);

    public GlassWallBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return COLLISION;
    }

    @Override
    protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (world.isClient) return;
        if (!(entity instanceof LivingEntity)) return;
        if (entity.getWidth() < 0.5f) return;
        entity.damage(world.getDamageSources().cactus(), 1.0f);
    }
}
