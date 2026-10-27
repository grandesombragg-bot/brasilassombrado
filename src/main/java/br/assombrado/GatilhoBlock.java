package br.assombrado;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;

/** Bloco invisível no corredor da capela: quando um jogador chega perto, o Corpo Seco surge da terra no cemitério. */
public class GatilhoBlock extends Block {
    public GatilhoBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }

    @Override
    protected void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        world.scheduleBlockTick(pos, this, 20);
    }

    @Override
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        PlayerEntity jogador = world.getClosestPlayer(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 7.0, false);
        if (jogador == null) {
            world.scheduleBlockTick(pos, this, 20);
            return;
        }
        double ang = random.nextDouble() * Math.PI * 2;
        double dist = 9 + random.nextDouble() * 4;
        int x = (int) (pos.getX() + Math.cos(ang) * dist);
        int z = (int) (pos.getZ() + Math.sin(ang) * dist);
        int y = world.getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, x, z);
        CorpoSecoEntity chefe = ModEntities.CORPO_SECO.create(world);
        if (chefe == null) {
            world.scheduleBlockTick(pos, this, 20);
            return;
        }
        chefe.refreshPositionAndAngles(x + 0.5, y, z + 0.5, random.nextFloat() * 360f, 0f);
        chefe.iniciarEmersao();
        chefe.setPersistent();
        world.spawnEntity(chefe);
        world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
    }
}
