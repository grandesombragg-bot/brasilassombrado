package br.assombrado;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

/** Só existe para o espelho poder ter um renderizador próprio (reflexo do jogador). */
public class MirrorBlockEntity extends BlockEntity {
    public MirrorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ESPELHO, pos, state);
    }
}
