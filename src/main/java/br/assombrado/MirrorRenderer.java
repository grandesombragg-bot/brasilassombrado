package br.assombrado;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Desenha dentro do espelho o torso do jogador (do peito para cima), espelhado, com a skin e a pose.
 * De vez em quando um vulto espia por cima do ombro, e a tela do jogador treme e escurece.
 */
public class MirrorRenderer implements BlockEntityRenderer<MirrorBlockEntity> {
    public MirrorRenderer(BlockEntityRendererFactory.Context ctx) {
    }

    private static void esconderPernas(PlayerEntityRenderer pr, boolean esconder) {
        PlayerEntityModel<AbstractClientPlayerEntity> m = pr.getModel();
        m.leftLeg.hidden = esconder;
        m.rightLeg.hidden = esconder;
        m.leftPants.hidden = esconder;
        m.rightPants.hidden = esconder;
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
        if (front < 0.25 || front > 8.0 || Math.abs(side) > 3.0) return;

        // vulto do ciclo atual: 1 = espia pela direita, 2 = pela esquerda, 3 = dois, 0 = nenhum
        long t = world.getTime();
        int tipo = 0;
        if (t % 140 <= 110 && front <= 7.0) {
            int h = new Random(pos.asLong() * 31L + t / 140).nextInt(12);
            tipo = h < 3 ? 0 : (h < 6 ? 1 : (h < 9 ? 2 : 3));
        }

        // reflexo: torso do peito para cima, sempre dentro da janelinha
        Direction direita = f.getOpposite().rotateYClockwise();
        double desvio = tipo == 1 ? -0.12 : (tipo == 2 ? 0.12 : 0.0);
        double px = 0.5 + f.getOffsetX() * 0.15 + direita.getOffsetX() * desvio;
        double pz = 0.5 + f.getOffsetZ() * 0.15 + direita.getOffsetZ() * desvio;
        boolean alongX = f.getAxis() == Direction.Axis.X;
        int l = WorldRenderer.getLightmapCoordinates(world, pos.offset(f));
        EntityRenderer<? super ClientPlayerEntity> renderer = mc.getEntityRenderDispatcher().getRenderer(player);
        PlayerEntityRenderer pr = renderer instanceof PlayerEntityRenderer p ? p : null;
        if (pr != null) esconderPernas(pr, true);
        float s = 0.78f;
        matrices.push();
        matrices.translate(px, -0.60, pz);
        matrices.scale(alongX ? -0.12f : s, s, alongX ? s : -0.12f);
        renderer.render(player, player.getYaw(tickDelta), tickDelta, matrices, vcp, l);
        matrices.pop();
        if (pr != null) esconderPernas(pr, false);

        if (tipo == 0) return;
        BlockState olhos = ModBlocks.ESPELHO_OLHOS.getDefaultState().with(EyesBlock.TIPO, tipo);
        matrices.push();
        matrices.translate(0.5, 0.5, 0.5);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0f - f.asRotation()));
        matrices.translate(-0.5, -0.5, -0.5);
        mc.getBlockRenderManager().renderBlockAsEntity(olhos, matrices, vcp,
                LightmapTextureManager.MAX_LIGHT_COORDINATE, OverlayTexture.DEFAULT_UV);
        matrices.pop();

        // efeito na tela: quanto mais perto do espelho com o vulto aparecendo, mais forte
        if (front <= 2.5) {
            MirrorEffect.sinalizar(front < 1.0 ? 3 : (front < 1.7 ? 2 : 1));
        }
    }
}
