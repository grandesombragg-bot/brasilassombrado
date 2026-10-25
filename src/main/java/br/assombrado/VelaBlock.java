package br.assombrado;

import net.minecraft.block.BlockState;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;

/** Vela de promessa: luz fraquinha (como tocha de redstone) e faíscas pequenas na chama. */
public class VelaBlock extends ShapedBlock {
    public VelaBlock(Settings settings, VoxelShape shape) {
        super(settings, shape);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (random.nextInt(3) != 0) return;
        double x = pos.getX() + 0.5;
        double y = pos.getY() + 0.47;
        double z = pos.getZ() + 0.5;
        world.addParticle(ParticleTypes.SMALL_FLAME, x, y, z, 0.0, 0.0, 0.0);
    }
}
