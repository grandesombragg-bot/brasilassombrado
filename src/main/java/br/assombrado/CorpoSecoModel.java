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

/** Esqueleto alto, de braços longos, com a coluna e o pescoço soltos: balança, arrasta e se contorce. */
public class CorpoSecoModel extends SinglePartEntityModel<CorpoSecoEntity> {
    public static final EntityModelLayer LAYER = new EntityModelLayer(Identifier.of(BrasilAssombrado.MOD_ID, "corpo_seco"), "main");

    private final ModelPart raiz;
    private final ModelPart pelvis, spine, ribs, neck, head, jaw;
    private final ModelPart thighL, thighR, shinL, shinR, footL, footR;
    private final ModelPart upperL, upperR, foreL, foreR, handL, handR;
    private final ModelPart clothF, clothB, hair1, hair2, hair3;

    public CorpoSecoModel(ModelPart raiz) {
        this.raiz = raiz;
        pelvis = raiz.getChild("pelvis");
        spine = pelvis.getChild("spine");
        ribs = spine.getChild("ribs");
        neck = ribs.getChild("neck");
        head = neck.getChild("head");
        jaw = head.getChild("jaw");
        thighL = pelvis.getChild("thigh_l");
        thighR = pelvis.getChild("thigh_r");
        shinL = thighL.getChild("shin_l");
        shinR = thighR.getChild("shin_r");
        footL = shinL.getChild("foot_l");
        footR = shinR.getChild("foot_r");
        upperL = ribs.getChild("upper_l");
        upperR = ribs.getChild("upper_r");
        foreL = upperL.getChild("fore_l");
        foreR = upperR.getChild("fore_r");
        handL = foreL.getChild("hand_l");
        handR = foreR.getChild("hand_r");
        clothF = pelvis.getChild("cloth_f");
        clothB = pelvis.getChild("cloth_b");
        hair1 = head.getChild("hair_1");
        hair2 = head.getChild("hair_2");
        hair3 = head.getChild("hair_3");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData data = new ModelData();
        ModelPartData r = data.getRoot();
        ModelPartData pelvis = r.addChild("pelvis", ModelPartBuilder.create().uv(82, 116).cuboid(-14.0f, -12f, -8f, 28f, 12f, 16f), ModelTransform.pivot(0f, 12f, 0f));
        ModelPartData spine = pelvis.addChild("spine", ModelPartBuilder.create().uv(42, 150).cuboid(-4f, -16f, -4f, 8f, 16f, 8f), ModelTransform.pivot(0f, -12f, 0f));
        ModelPartData ribs = spine.addChild("ribs", ModelPartBuilder.create().uv(0, 0).cuboid(-16f, -40f, -10.0f, 32f, 40f, 20f), ModelTransform.pivot(0f, -16f, 0f));
        ModelPartData neck = ribs.addChild("neck", ModelPartBuilder.create().uv(76, 150).cuboid(-4f, -16f, -4f, 8f, 16f, 8f), ModelTransform.pivot(0f, -40f, 0f));
        ModelPartData head = neck.addChild("head", ModelPartBuilder.create().uv(140, 0).cuboid(-12f, -28f, -12f, 24f, 28f, 24f), ModelTransform.pivot(0f, -16f, 0f));
        ModelPartData jaw = head.addChild("jaw", ModelPartBuilder.create().uv(0, 116).cuboid(-10.0f, -2.0f, -20f, 20f, 12f, 20f), ModelTransform.pivot(0f, -4f, 8f));
        ModelPartData thigh_l = pelvis.addChild("thigh_l", ModelPartBuilder.create().uv(0, 62).cuboid(-6.0f, 0f, -6.0f, 12f, 40f, 12f), ModelTransform.pivot(10.0f, 0f, 0f));
        ModelPartData thigh_r = pelvis.addChild("thigh_r", ModelPartBuilder.create().uv(0, 62).cuboid(-6.0f, 0f, -6.0f, 12f, 40f, 12f), ModelTransform.pivot(-10.0f, 0f, 0f));
        ModelPartData shin_l = thigh_l.addChild("shin_l", ModelPartBuilder.create().uv(50, 62).cuboid(-4f, 0f, -4f, 8f, 44f, 8f), ModelTransform.pivot(0f, 40f, 0f));
        ModelPartData shin_r = thigh_r.addChild("shin_r", ModelPartBuilder.create().uv(50, 62).cuboid(-4f, 0f, -4f, 8f, 44f, 8f), ModelTransform.pivot(0f, 40f, 0f));
        ModelPartData foot_l = shin_l.addChild("foot_l", ModelPartBuilder.create().uv(172, 116).cuboid(-6.0f, -8f, -12f, 12f, 8f, 20f), ModelTransform.pivot(0f, 44f, 0f));
        ModelPartData foot_r = shin_r.addChild("foot_r", ModelPartBuilder.create().uv(172, 116).cuboid(-6.0f, -8f, -12f, 12f, 8f, 20f), ModelTransform.pivot(0f, 44f, 0f));
        ModelPartData upper_l = ribs.addChild("upper_l", ModelPartBuilder.create().uv(102, 62).cuboid(-4f, 0f, -4f, 8f, 40f, 8f), ModelTransform.pivot(22.0f, -36f, 0f));
        ModelPartData upper_r = ribs.addChild("upper_r", ModelPartBuilder.create().uv(102, 62).cuboid(-4f, 0f, -4f, 8f, 40f, 8f), ModelTransform.pivot(-22.0f, -36f, 0f));
        ModelPartData fore_l = upper_l.addChild("fore_l", ModelPartBuilder.create().uv(106, 0).cuboid(-4f, 0f, -4f, 8f, 48f, 8f), ModelTransform.pivot(0f, 40f, 0f));
        ModelPartData fore_r = upper_r.addChild("fore_r", ModelPartBuilder.create().uv(106, 0).cuboid(-4f, 0f, -4f, 8f, 48f, 8f), ModelTransform.pivot(0f, 40f, 0f));
        ModelPartData hand_l = fore_l.addChild("hand_l", ModelPartBuilder.create().uv(0, 150).cuboid(-6.0f, 0f, -4f, 12f, 20f, 8f), ModelTransform.pivot(0f, 48f, 0f));
        ModelPartData hand_r = fore_r.addChild("hand_r", ModelPartBuilder.create().uv(0, 150).cuboid(-6.0f, 0f, -4f, 12f, 20f, 8f), ModelTransform.pivot(0f, 48f, 0f));
        ModelPartData cloth_f = pelvis.addChild("cloth_f", ModelPartBuilder.create().uv(136, 62).cuboid(-12f, 0f, -2.0f, 24f, 36f, 4f), ModelTransform.pivot(0f, 0f, -8f));
        ModelPartData cloth_b = pelvis.addChild("cloth_b", ModelPartBuilder.create().uv(136, 62).cuboid(-12f, 0f, -2.0f, 24f, 36f, 4f), ModelTransform.pivot(0f, 0f, 8f));
        ModelPartData hair_1 = head.addChild("hair_1", ModelPartBuilder.create().uv(84, 62).cuboid(-2.0f, 0f, -2.0f, 4f, 48f, 4f), ModelTransform.pivot(-8f, -24f, 10.0f));
        ModelPartData hair_2 = head.addChild("hair_2", ModelPartBuilder.create().uv(84, 62).cuboid(-2.0f, 0f, -2.0f, 4f, 48f, 4f), ModelTransform.pivot(0f, -24f, 12f));
        ModelPartData hair_3 = head.addChild("hair_3", ModelPartBuilder.create().uv(84, 62).cuboid(-2.0f, 0f, -2.0f, 4f, 48f, 4f), ModelTransform.pivot(8f, -24f, 10.0f));
        return TexturedModelData.of(data, 256, 256);
    }

    @Override
    public ModelPart getPart() {
        return raiz;
    }

    private static float sin(float v) {
        return (float) Math.sin(v);
    }

    private static float cos(float v) {
        return (float) Math.cos(v);
    }

    @Override
    public void setAngles(CorpoSecoEntity e, float limbAngle, float limbDistance, float t, float headYaw, float headPitch) {
        raiz.traverse().forEach(ModelPart::resetTransform);
        float ld = Math.min(limbDistance, 1.0f);
        float w = limbAngle * 0.6662f;
        float rad = 0.017453292f;

        // balanço contínuo, mesmo parado
        spine.roll = sin(t * 0.05f) * 0.07f;
        spine.yaw = sin(t * 0.037f) * 0.09f;
        ribs.pitch = 0.14f + sin(t * 0.07f) * 0.05f + ld * 0.25f;
        ribs.yaw = -sin(w) * 0.32f * ld + sin(t * 0.045f) * 0.06f;
        ribs.roll = sin(t * 0.06f + 1f) * 0.06f;
        neck.pitch = -0.12f + sin(t * 0.09f) * 0.06f;
        neck.yaw = headYaw * rad * 0.45f + sin(w) * 0.2f * ld;
        head.yaw = headYaw * rad * 0.4f;
        head.pitch = headPitch * rad * 0.8f + 0.12f;
        head.roll = sin(t * 0.11f) * 0.13f + ((t % 47f) < 3f ? 0.35f : 0f);
        jaw.pitch = 0.12f + (sin(t * 0.3f) + 1f) * 0.06f;

        // marcha frouxa, com o quadril balançando
        pelvis.roll = sin(w) * 0.12f * ld;
        pelvis.yaw = -sin(w) * 0.16f * ld;
        thighL.pitch = cos(w) * 1.05f * ld;
        thighR.pitch = cos(w + 3.14159f) * 1.05f * ld;
        shinL.pitch = Math.max(0f, -sin(w + 1.0f)) * 1.25f * ld + 0.08f;
        shinR.pitch = Math.max(0f, -sin(w + 1.0f + 3.14159f)) * 1.25f * ld + 0.08f;
        footL.pitch = -thighL.pitch * 0.4f - shinL.pitch * 0.3f;
        footR.pitch = -thighR.pitch * 0.4f - shinR.pitch * 0.3f;
        upperL.pitch = cos(w + 3.14159f) * 0.75f * ld + 0.1f;
        upperR.pitch = cos(w) * 0.75f * ld + 0.1f;
        upperL.roll = -0.12f - sin(t * 0.06f) * 0.05f;
        upperR.roll = 0.12f + sin(t * 0.06f + 1f) * 0.05f;
        foreL.pitch = -0.35f - ld * 0.3f * sin(w) + sin(t * 0.08f) * 0.06f;
        foreR.pitch = -0.35f + ld * 0.3f * sin(w) + sin(t * 0.08f + 2f) * 0.06f;
        handL.pitch = sin(t * 0.1f) * 0.22f;
        handR.pitch = sin(t * 0.1f + 1.5f) * 0.22f;
        handL.roll = 0.15f;
        handR.roll = -0.15f;
        clothF.pitch = -sin(w) * 0.35f * ld - 0.05f + sin(t * 0.08f) * 0.05f;
        clothB.pitch = sin(w) * 0.35f * ld + 0.05f + sin(t * 0.08f + 1f) * 0.05f;
        hair1.pitch = 0.2f + sin(t * 0.12f) * 0.15f + ld * 0.5f;
        hair2.pitch = 0.2f + sin(t * 0.12f + 1f) * 0.15f + ld * 0.5f;
        hair3.pitch = 0.2f + sin(t * 0.12f + 2f) * 0.15f + ld * 0.5f;
        hair1.roll = sin(t * 0.1f) * 0.12f;
        hair3.roll = -sin(t * 0.1f + 1f) * 0.12f;

        // escalando: braços para o alto, pernas dobradas
        if (e.isClimbing()) {
            upperL.pitch = -2.6f + sin(t * 0.5f) * 0.4f;
            upperR.pitch = -2.6f + sin(t * 0.5f + 3.14159f) * 0.4f;
            foreL.pitch = -0.3f;
            foreR.pitch = -0.3f;
            thighL.pitch = -0.9f + sin(t * 0.5f + 3.14159f) * 0.4f;
            thighR.pitch = -0.9f + sin(t * 0.5f) * 0.4f;
            shinL.pitch = 1.3f;
            shinR.pitch = 1.3f;
            ribs.pitch = -0.1f;
        }

        // atacando: garras para a frente e boca aberta
        if (e.isAttacking()) {
            upperL.pitch = -1.6f + sin(t * 0.9f) * 0.3f;
            upperR.pitch = -1.6f + sin(t * 0.9f + 1.5f) * 0.3f;
            foreL.pitch = -0.5f + sin(t * 1.3f) * 0.4f;
            foreR.pitch = -0.5f + sin(t * 1.3f + 1f) * 0.4f;
            handL.pitch = sin(t * 1.5f) * 0.6f;
            handR.pitch = sin(t * 1.5f + 1f) * 0.6f;
            jaw.pitch = 0.85f + sin(t * 0.7f) * 0.1f;
            head.pitch -= 0.25f;
            ribs.pitch += 0.2f;
        }

        // surgindo da terra
        int em = e.getEmerge();
        if (em > 0) {
            float p = 1f - em / (float) CorpoSecoEntity.EMERGE_TOTAL;
            raiz.pivotY = (1f - p) * 204f;
            upperL.pitch = -2.8f + p * 1.4f;
            upperR.pitch = -2.8f + p * 1.4f;
            jaw.pitch = 0.95f - p * 0.6f;
            head.pitch = -0.7f * (1f - p);
            ribs.pitch = -0.4f * (1f - p);
        } else if (e.deathTime > 0) {
            // desmorona: joelhos cedem, coluna cai, a cabeça tomba e o corpo afunda
            float q = Math.min(1f, e.deathTime / (float) CorpoSecoEntity.MORTE_TOTAL);
            spine.pitch += q * 1.1f;
            neck.pitch += q * 0.8f;
            head.roll += q * 0.9f;
            thighL.pitch = -q * 1.4f;
            thighR.pitch = -q * 1.4f;
            shinL.pitch = q * 2.3f;
            shinR.pitch = q * 2.3f;
            upperL.pitch = -q * 0.4f;
            upperR.pitch = -q * 0.4f;
            jaw.pitch = q * 0.9f;
            raiz.pivotY = q * q * 190f;
        }
    }
}
