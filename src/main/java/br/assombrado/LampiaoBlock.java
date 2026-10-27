package br.assombrado;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;

/** Lampião velho: o Corpo Seco o apaga; só reacende com Óleo de Lampião. */
public class LampiaoBlock extends ShapedBlock {
    public static final BooleanProperty ACESO = BooleanProperty.of("aceso");

    public LampiaoBlock(Settings settings, VoxelShape shape) {
        super(settings, shape);
        setDefaultState(getDefaultState().with(ACESO, true));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ACESO);
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos,
                                             PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!state.get(ACESO) && stack.isOf(ModItems.OLEO_DE_LAMPIAO)) {
            if (!world.isClient) {
                world.setBlockState(pos, state.with(ACESO, true), 3);
                world.playSound(null, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0f, 0.8f);
                if (!player.isCreative()) stack.decrement(1);
            }
            return ItemActionResult.success(world.isClient);
        }
        return ItemActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
    }
}
