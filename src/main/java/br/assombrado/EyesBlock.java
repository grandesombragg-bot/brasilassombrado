package br.assombrado;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;

/** Bloco invisível ao jogador: só serve de modelo para os vultos que aparecem dentro do espelho. */
public class EyesBlock extends Block {
    public static final IntProperty TIPO = IntProperty.of("tipo", 1, 3);

    public EyesBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(TIPO, 1));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(TIPO);
    }
}
