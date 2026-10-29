package br.assombrado;

import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.Identifier;

/** Capanga rastejante de raízes e osso, que corre de quatro e se contorce. */
public class EnraizadoModel extends SinglePartEntityModel<EnraizadoEntity> {
    public static final EntityModelLayer LAYER = new EntityModelLayer(Identifier.of(BrasilAssombrado.MOD_ID, "enraizado"), "main");

    private final ModelPart raiz;
    private final ModelPart body, head, jaw;
    private final ModelPart legFL, legFR, legBL, legBR, lowFL, lowFR, lowBL, lowBR;
    private final ModelPart tail1, tail2, tail3, spike1, spike2, spike3;

    public EnraizadoModel(ModelPart raiz) {
        this.raiz = raiz;
        body = raiz.getChild("body");
        head = body.getChild("head");
        jaw = head.getChild("jaw");
        spike1 = body.getChild("spike_1");
        spike2 = body.getChild("spike_2");
        spike3 = body.getChild("spike_3");
        legFL = body.getChild("leg_fl");
        legFR = body.getChild("leg_fr");
        legBL = body.getChild("leg_bl");
        legBR = body.getChild("leg_br");
        lowFL = legFL.getChild("low_fl");
        lowFR = legFR.getChild("low_fr");
        lowBL = legBL.getChild("low_bl");
        lowBR = legBR.getChild("low_br");
        tail1 = body.getChild("tail_1");
        tail2 = body.getChild("tail_2");
        tail3 = body.getChild("tail_3");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData data = new ModelData();
        ModelPartData r = data.getRoot();
        ModelPartData body = r.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-12f, -12f, -20f, 24f, 20f, 40f), ModelTransform.pivot(0f, 52f, 0f));
        ModelPartData head = body.addChild("head", ModelPartBuilder.create().uv(0, 62).cuboid(-10.0f, -10.0f, -16f, 20f, 20f, 16f), ModelTransform.pivot(0f, -4f, -20f));
        ModelPartData jaw = head.addChild("jaw", ModelPartBuilder.create().uv(108, 62).cuboid(-8f, 0f, -12f, 16f, 8f, 12f), ModelTransform.pivot(0f, 6.0f, -4f));
        ModelPartData spike_1 = body.addChild("spike_1", ModelPartBuilder.create().uv(74, 62).cuboid(-4f, -16f, -4f, 8f, 16f, 8f), ModelTransform.pivot(0f, -12f, -8f));
        ModelPartData spike_2 = body.addChild("spike_2", ModelPartBuilder.create().uv(74, 62).cuboid(-4f, -16f, -4f, 8f, 16f, 8f), ModelTransform.pivot(0f, -12f, 4f));
        ModelPartData spike_3 = body.addChild("spike_3", ModelPartBuilder.create().uv(74, 62).cuboid(-4f, -16f, -4f, 8f, 16f, 8f), ModelTransform.pivot(0f, -12f, 16f));
        ModelPartData leg_fl = body.addChild("leg_fl", ModelPartBuilder.create().uv(148, 0).cuboid(-4f, 0f, -4f, 8f, 32f, 8f), ModelTransform.pivot(12f, 4f, -12f));
        ModelPartData leg_fr = body.addChild("leg_fr", ModelPartBuilder.create().uv(148, 0).cuboid(-4f, 0f, -4f, 8f, 32f, 8f), ModelTransform.pivot(-12f, 4f, -12f));
        ModelPartData leg_bl = body.addChild("leg_bl", ModelPartBuilder.create().uv(148, 0).cuboid(-4f, 0f, -4f, 8f, 32f, 8f), ModelTransform.pivot(12f, 4f, 12f));
        ModelPartData leg_br = body.addChild("leg_br", ModelPartBuilder.create().uv(148, 0).cuboid(-4f, 0f, -4f, 8f, 32f, 8f), ModelTransform.pivot(-12f, 4f, 12f));
        ModelPartData low_fl = leg_fl.addChild("low_fl", ModelPartBuilder.create().uv(182, 0).cuboid(-2.0f, 0f, -2.0f, 4f, 36f, 4f), ModelTransform.pivot(0f, 32f, 0f));
        ModelPartData low_fr = leg_fr.addChild("low_fr", ModelPartBuilder.create().uv(182, 0).cuboid(-2.0f, 0f, -2.0f, 4f, 36f, 4f), ModelTransform.pivot(0f, 32f, 0f));
        ModelPartData low_bl = leg_bl.addChild("low_bl", ModelPartBuilder.create().uv(182, 0).cuboid(-2.0f, 0f, -2.0f, 4f, 36f, 4f), ModelTransform.pivot(0f, 32f, 0f));
        ModelPartData low_br = leg_br.addChild("low_br", ModelPartBuilder.create().uv(182, 0).cuboid(-2.0f, 0f, -2.0f, 4f, 36f, 4f), ModelTransform.pivot(0f, 32f, 0f));
        ModelPartData tail_1 = body.addChild("tail_1", ModelPartBuilder.create().uv(130, 0).cuboid(-2.0f, 0f, -2.0f, 4f, 40f, 4f), ModelTransform.pivot(-6.0f, 0f, 20f));
        ModelPartData tail_2 = body.addChild("tail_2", ModelPartBuilder.create().uv(130, 0).cuboid(-2.0f, 0f, -2.0f, 4f, 40f, 4f), ModelTransform.pivot(0f, 0f, 20f));
        ModelPartData tail_3 = body.addChild("tail_3", ModelPartBuilder.create().uv(130, 0).cuboid(-2.0f, 0f, -2.0f, 4f, 40f, 4f), ModelTransform.pivot(6.0f, 0f, 20f));
        return TexturedModelData.of(data, 256, 256);
    }

    @Override
    public ModelPart getPart() {
        return raiz;
    }

    private static float sin(float v) {
        return (float) Math.sin(v);
    }

    @Override
    public void setAngles(EnraizadoEntity e, float limbAngle, float limbDistance, float t, float headYaw, float headPitch) {
        raiz.traverse().forEach(ModelPart::resetTransform);
        float ld = Math.min(limbDistance, 1.0f);
        float w = limbAngle * 1.1f;
        float rad = 0.017453292f;

        body.pitch = 0.05f + sin(t * 0.2f) * 0.04f + ld * 0.1f;
        body.roll = sin(w) * 0.12f * ld + sin(t * 0.13f) * 0.04f;
        head.yaw = headYaw * rad * 0.7f;
        head.pitch = headPitch * rad * 0.7f - 0.1f;
        head.roll = sin(t * 0.37f) * 0.15f + ((t % 31f) < 2f ? 0.4f : 0f);
        jaw.pitch = 0.1f + (sin(t * 0.45f) + 1f) * 0.1f;

        // quatro patas, pisando em diagonal, bem soltas
        legFL.pitch = sin(w) * 0.9f * ld;
        legBR.pitch = sin(w) * 0.9f * ld;
        legFR.pitch = sin(w + 3.14159f) * 0.9f * ld;
        legBL.pitch = sin(w + 3.14159f) * 0.9f * ld;
        legFL.roll = -1.0f;
        legBL.roll = -1.0f;
        legFR.roll = 1.0f;
        legBR.roll = 1.0f;
        lowFL.roll = 0.9f;
        lowBL.roll = 0.9f;
        lowFR.roll = -0.9f;
        lowBR.roll = -0.9f;
        lowFL.pitch = Math.max(0f, sin(w + 1f)) * 0.8f * ld;
        lowBR.pitch = Math.max(0f, sin(w + 1f)) * 0.8f * ld;
        lowFR.pitch = Math.max(0f, sin(w + 4.14159f)) * 0.8f * ld;
        lowBL.pitch = Math.max(0f, sin(w + 4.14159f)) * 0.8f * ld;

        tail1.pitch = 1.1f + sin(t * 0.15f) * 0.15f + ld * 0.2f;
        tail2.pitch = 1.2f + sin(t * 0.15f + 1f) * 0.15f + ld * 0.2f;
        tail3.pitch = 1.1f + sin(t * 0.15f + 2f) * 0.15f + ld * 0.2f;
        tail1.roll = -0.3f + sin(t * 0.1f) * 0.2f;
        tail3.roll = 0.3f - sin(t * 0.1f) * 0.2f;
        spike1.pitch = sin(t * 0.2f) * 0.1f;
        spike2.pitch = sin(t * 0.2f + 1f) * 0.1f;
        spike3.pitch = sin(t * 0.2f + 2f) * 0.1f;

        if (e.isAttacking()) {
            body.pitch = -0.45f;
            head.pitch = 0.3f;
            jaw.pitch = 0.8f + sin(t * 0.9f) * 0.15f;
            legFL.pitch = -1.2f + sin(t * 1.2f) * 0.4f;
            legFR.pitch = -1.2f + sin(t * 1.2f + 1f) * 0.4f;
        }
    }
}
