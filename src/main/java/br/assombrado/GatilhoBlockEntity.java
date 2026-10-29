package br.assombrado;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;

/** Vigia da capela: quando um jogador chega perto, o Corpo Seco surge da terra no cemitério. */
public class GatilhoBlockEntity extends BlockEntity {
    private int espera = 0;

    public GatilhoBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GATILHO, pos, state);
    }

    public void tick(ServerWorld mundo) {
        if (++espera < 10) return;
        espera = 0;
        BlockPos pos = getPos();
        PlayerEntity jogador = mundo.getClosestPlayer(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 5.5, false);
        if (jogador == null) return;
        double ang = mundo.random.nextDouble() * Math.PI * 2;
        double dist = 9 + mundo.random.nextDouble() * 4;
        int x = (int) (pos.getX() + Math.cos(ang) * dist);
        int z = (int) (pos.getZ() + Math.sin(ang) * dist);
        int y = mundo.getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, x, z);
        CorpoSecoEntity chefe = ModEntities.CORPO_SECO.create(mundo);
        if (chefe == null) return;
        chefe.refreshPositionAndAngles(x + 0.5, y, z + 0.5, mundo.random.nextFloat() * 360f, 0f);
        chefe.iniciarEmersao();
        chefe.setPersistent();
        mundo.spawnEntity(chefe);
        mundo.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
    }
}
