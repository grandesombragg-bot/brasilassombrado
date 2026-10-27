package br.assombrado;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.util.Identifier;

public class SecoRenderer<T extends HostileEntity> extends MobEntityRenderer<T, SecoModel<T>> {
    private final Identifier textura;
    private final float escala;

    public SecoRenderer(EntityRendererFactory.Context ctx, Identifier textura, float escala) {
        super(ctx, new SecoModel<T>(ctx.getPart(SecoModel.LAYER)), 0.7f * escala);
        this.textura = textura;
        this.escala = escala;
    }

    @Override
    public Identifier getTexture(T entidade) {
        return textura;
    }

    @Override
    protected void scale(T entidade, MatrixStack matrizes, float quantidade) {
        matrizes.scale(escala, escala, escala);
    }

    @Override
    protected float getLyingAngle(T entidade) {
        return 0.0f;
    }
}
