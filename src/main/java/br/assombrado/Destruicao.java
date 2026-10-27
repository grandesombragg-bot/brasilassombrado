package br.assombrado;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

/** Quebra uma região 3x3x2 na frente da criatura, limitada pela dureza dos blocos. */
public class Destruicao {
    public static int quebrarFrente(ServerWorld mundo, Entity quem, Direction d, float dureza, float chance, boolean incluirMuros) {
        int quebrados = 0;
        BlockPos base = quem.getBlockPos();
        for (int f = 1; f <= 2; f++) {
            for (int l = -1; l <= 1; l++) {
                for (int y = 0; y <= 2; y++) {
                    BlockPos p = base.offset(d, f).offset(d.rotateYClockwise(), l).up(y);
                    BlockState s = mundo.getBlockState(p);
                    if (s.isAir()) continue;
                    float h = s.getHardness(mundo, p);
                    if (h < 0 || h > dureza) continue;
                    Block b = s.getBlock();
                    if (!incluirMuros && (b instanceof GlassWallBlock || b instanceof MuroLisoBlock)) continue;
                    if (mundo.random.nextFloat() > chance) continue;
                    mundo.breakBlock(p, false, quem);
                    quebrados++;
                }
            }
        }
        return quebrados;
    }
}
