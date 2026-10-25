package br.assombrado;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Desenha dentro do espelho uma cópia espelhada do jogador (skin e pose) e, de vez em quando,
 * vultos de olhos vermelhos ao fundo. A cópia é achatada contra o vidro, e os blocos ao redor
 * recortam a imagem no tamanho da janelinha.
 */
public class MirrorRenderer implements BlockEntityRenderer<MirrorBlockEntity> {
    public MirrorRenderer(BlockEntityRendererFactory.Context ctx) {
    }

    @Override
    public void render(MirrorBlockEntity be, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vcp, int light, int overlay) {
        World world = be.getWorld();
        MinecraftClient mc = MinecraftClient.getInstance();
        ClientPlayerEntity player = mc.player;
        if (world == null || player == null) return;
        BlockState state = be.getCachedState();
        if (!(state.getBlock() instanceof MirrorBlock)) return;

        Direction f = state.get(MirrorBlock.FACING);
        BlockPos pos = be.getPos();
        Vec3d pp = player.getLerpedPos(tickDelta);
        double cx = pos.getX() + 0.5;
        double cz = pos.getZ() + 0.5;
        double front = (pp.x - cx) * f.getOffsetX() + (pp.z - cz) * f.getOffsetZ();
        double side = -(pp.x - cx) * f.getOffsetZ() + (pp.z - cz) * f.getOffsetX();
        if (front < 0.4 || front > 8.0 || Math.abs(side) > 3.0) return;

        // reflexo do jogador
        boolean alongX = f.getAxis() == Direction.Axis.X;
        double ex = pp.x - pos.getX();
        double ey = pp.y - pos.getY();
        double ez = pp.z - pos.getZ();
        if (alongX) {
            ex = 0.5 + f.getOffsetX() * 0.15;
        } else {
            ez = 0.5 + f.getOffsetZ() * 0.15;
        }
        int l = WorldRenderer.getLightmapCoordinates(world, pos.offset(f));
        EntityRenderer<? super ClientPlayerEntity> renderer = mc.getEntityRenderDispatcher().getRenderer(player);
        matrices.push();
        matrices.translate(ex, ey, ez);
        matrices.scale(alongX ? -0.12f : 1.0f, 1.0f, alongX ? 1.0f : -0.12f);
        renderer.render(player, player.getYaw(tickDelta), tickDelta, matrices, vcp, l);
        matrices.pop();

        // vultos: aparecem e somem em ciclos curtos, sempre diferentes de espelho para espelho
        long t = world.getTime();
        long ciclo = t / 140;
        if (t % 140 > 90 || front > 6.0) return;
        int h = new Random(pos.asLong() * 31L + ciclo).nextInt(12);
        int tipo = h < 5 ? 0 : (h < 7 ? 1 : (h < 9 ? 2 : (h == 9 ? 3 : 0)));
        if (tipo == 0) return;
        BlockState olhos = ModBlocks.ESPELHO_OLHOS.getDefaultState().with(EyesBlock.TIPO, tipo);
        matrices.push();
        matrices.translate(0.5, 0.5, 0.5);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0f - f.asRotation()));
        matrices.translate(-0.5, -0.5, -0.5);
        mc.getBlockRenderManager().renderBlockAsEntity(olhos, matrices, vcp,
                LightmapTextureManager.MAX_LIGHT_COORDINATE, OverlayTexture.DEFAULT_UV);
        matrices.pop();
    }
}
