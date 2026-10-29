package br.assombrado;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class CorpoSecoRenderer extends MobEntityRenderer<CorpoSecoEntity, CorpoSecoModel> {
    private static final Identifier TEXTURA = Identifier.of(BrasilAssombrado.MOD_ID, "textures/entity/corpo_seco.png");

    public CorpoSecoRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new CorpoSecoModel(ctx.getPart(CorpoSecoModel.LAYER)), 0.8f);
    }

    @Override
    public Identifier getTexture(CorpoSecoEntity entidade) {
        return TEXTURA;
    }

    @Override
    protected void scale(CorpoSecoEntity entidade, MatrixStack matrizes, float quantidade) {
        // o modelo é desenhado 4x maior (textura mais detalhada); aqui ele volta ao tamanho certo
        matrizes.scale(0.25f, 0.25f, 0.25f);
        matrizes.translate(0.0f, 1.501f * (1.0f - 1.0f / 0.25f), 0.0f);
    }

    @Override
    protected float getLyingAngle(CorpoSecoEntity entidade) {
        return 0.0f;
    }
}
