package br.assombrado;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

/**
 * Muro com cacos de vidro. Só a fileira de cima mostra os cacos (com_cacos = true).
 * Machuca quem encosta, como um cacto; criaturas muito pequenas evitam o muro.
 */
public class GlassWallBlock extends Block {
    public static final BooleanProperty COM_CACOS = BooleanProperty.of("com_cacos");
    private static final VoxelShape COLLISION = Block.createCuboidShape(1, 0, 1, 15, 16, 15);

    public GlassWallBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(COM_CACOS, true));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(COM_CACOS);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState acima = ctx.getWorld().getBlockState(ctx.getBlockPos().up());
        return getDefaultState().with(COM_CACOS, !(acima.getBlock() instanceof GlassWallBlock));
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState,
                                                   WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.UP) {
            return state.with(COM_CACOS, !(neighborState.getBlock() instanceof GlassWallBlock));
        }
        return state;
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
