package br.assombrado;

import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;

/** Modelo magro e torto, com passos quebrados e espasmos. Serve ao Corpo Seco e ao Enraizado. */
public class SecoModel<T extends LivingEntity> extends SinglePartEntityModel<T> {
    public static final EntityModelLayer LAYER = new EntityModelLayer(Identifier.of(BrasilAssombrado.MOD_ID, "seco"), "main");

    private final ModelPart raiz;
    private final ModelPart corpo;
    private final ModelPart cabeca;
    private final ModelPart bracoE;
    private final ModelPart bracoD;
    private final ModelPart pernaE;
    private final ModelPart pernaD;

    public SecoModel(ModelPart raiz) {
        this.raiz = raiz;
        this.corpo = raiz.getChild("body");
        this.cabeca = raiz.getChild("head");
        this.bracoE = raiz.getChild("left_arm");
        this.bracoD = raiz.getChild("right_arm");
        this.pernaE = raiz.getChild("left_leg");
        this.pernaD = raiz.getChild("right_leg");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData data = new ModelData();
        ModelPartData r = data.getRoot();
        r.addChild("body", ModelPartBuilder.create().uv(16, 16).cuboid(-4f, 0f, -2f, 8f, 14f, 4f), ModelTransform.pivot(0f, -8f, 0f));
        r.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-3f, -8f, -3f, 6f, 8f, 6f), ModelTransform.pivot(0f, -8f, 0f));
        r.addChild("left_arm", ModelPartBuilder.create().uv(40, 16).cuboid(-1.5f, 0f, -1.5f, 3f, 20f, 3f), ModelTransform.pivot(5.5f, -7f, 0f));
        r.addChild("right_arm", ModelPartBuilder.create().uv(40, 16).cuboid(-1.5f, 0f, -1.5f, 3f, 20f, 3f), ModelTransform.pivot(-5.5f, -7f, 0f));
        r.addChild("left_leg", ModelPartBuilder.create().uv(0, 16).cuboid(-1.5f, 0f, -1.5f, 3f, 18f, 3f), ModelTransform.pivot(2.2f, 6f, 0f));
        r.addChild("right_leg", ModelPartBuilder.create().uv(0, 16).cuboid(-1.5f, 0f, -1.5f, 3f, 18f, 3f), ModelTransform.pivot(-2.2f, 6f, 0f));
        return TexturedModelData.of(data, 64, 64);
    }

    @Override
    public ModelPart getPart() {
        return raiz;
    }

    @Override
    public void setAngles(T entidade, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.raiz.traverse().forEach(ModelPart::resetTransform);
        float t = animationProgress;
        cabeca.yaw = headYaw * 0.017453292f * 0.6f;
        cabeca.pitch = headPitch * 0.017453292f;
        cabeca.roll = 0.25f + (float) Math.sin(t * 0.9f) * 0.06f + ((t % 40f) < 3f ? 0.3f : 0f);
        corpo.roll = (float) Math.sin(t * 0.07f) * 0.05f;
        corpo.pitch = 0.12f;

        // andar quebrado: o passo anda em degraus, como juntas estalando
        float passo = Math.round((float) Math.sin(limbAngle * 0.6f) * 3f) / 3f;
        pernaE.pitch = passo * limbDistance * 1.1f;
        pernaD.pitch = -passo * limbDistance * 1.1f;
        bracoE.pitch = -passo * limbDistance * 0.6f - 0.15f;
        bracoD.pitch = passo * limbDistance * 0.6f - 0.15f;
        bracoE.roll = -0.1f - (float) Math.sin(t * 0.06f) * 0.05f;
        bracoD.roll = 0.1f + (float) Math.sin(t * 0.06f + 1f) * 0.05f;

        if (entidade instanceof MobEntity mob && mob.isAttacking()) {
            bracoE.pitch = -1.45f + (float) Math.sin(t * 0.8f) * 0.12f;
            bracoD.pitch = -1.45f + (float) Math.sin(t * 0.8f + 1f) * 0.12f;
        }

        // surgindo da terra e desabando na morte
        float afundar = 0f;
        if (entidade instanceof CorpoSecoEntity c) {
            if (c.getEmerge() > 0) {
                float p = 1f - c.getEmerge() / (float) CorpoSecoEntity.EMERGE_TOTAL;
                afundar = (1f - p) * 44f;
                bracoE.pitch = -2.4f + p * 1.2f;
                bracoD.pitch = -2.4f + p * 1.2f;
                cabeca.pitch = -0.8f * (1f - p);
            } else if (c.deathTime > 0) {
                afundar = c.deathTime / (float) CorpoSecoEntity.MORTE_TOTAL * 44f;
                cabeca.roll = 0.9f;
                corpo.pitch = 0.5f;
            }
        }
        this.raiz.pivotY = afundar;
    }
}
