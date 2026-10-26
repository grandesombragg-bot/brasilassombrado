package br.assombrado;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;

import java.util.Random;

/** Efeito de medo na tela perto do espelho: nível 1 (leve), 2 (médio) e 3 (forte, estilo Hello Neighbor). */
public class MirrorEffect {
    private static final Random RNG = new Random();
    private static int pendente = 0;
    private static long ultimoSinal = 0;
    private static float intensidade = 0f;
    private static float tremorYaw = 0f;
    private static float tremorPitch = 0f;

    public static void sinalizar(int nivel) {
        if (nivel > pendente) pendente = nivel;
        ultimoSinal = System.currentTimeMillis();
    }

    public static void init() {
        ClientTickEvents.END_CLIENT_TICK.register(MirrorEffect::tick);
        HudRenderCallback.EVENT.register((ctx, tickCounter) -> desenhar(ctx));
    }

    private static void tick(MinecraftClient client) {
        ClientPlayerEntity p = client.player;
        boolean ativo = System.currentTimeMillis() - ultimoSinal < 150;
        float alvo = ativo ? pendente : 0f;
        pendente = 0;
        intensidade += (alvo - intensidade) * 0.2f;
        if (p == null) {
            tremorYaw = 0f;
            tremorPitch = 0f;
            return;
        }
        // desfaz o tremor do tick anterior e aplica um novo
        p.setYaw(p.getYaw() - tremorYaw);
        p.prevYaw -= tremorYaw;
        p.setPitch(p.getPitch() - tremorPitch);
        p.prevPitch -= tremorPitch;
        float amp = intensidade < 0.05f ? 0f : intensidade * intensidade * 0.3f;
        tremorYaw = (RNG.nextFloat() - 0.5f) * 2f * amp;
        tremorPitch = (RNG.nextFloat() - 0.5f) * 2f * amp;
        p.setYaw(p.getYaw() + tremorYaw);
        p.prevYaw += tremorYaw;
        p.setPitch(p.getPitch() + tremorPitch);
        p.prevPitch += tremorPitch;
    }

    private static int argb(int a, int r, int g, int b) {
        return ((a & 255) << 24) | ((r & 255) << 16) | ((g & 255) << 8) | (b & 255);
    }

    private static void desenhar(DrawContext ctx) {
        if (intensidade < 0.05f) return;
        int w = ctx.getScaledWindowWidth();
        int h = ctx.getScaledWindowHeight();
        float t = Math.min(intensidade, 3f) / 3f;
        float pulso = 0.85f + 0.15f * (float) Math.sin(System.currentTimeMillis() / 70.0);
        float k = t * pulso;
        ctx.fill(0, 0, w, h, argb((int) (25 + 100 * k * k), 0, 0, 0));
        int vermelho = (int) (70 * k);
        int borda = (int) (h * (0.12f + 0.3f * k));
        int aForte = (int) (80 + 150 * k);
        ctx.fillGradient(0, 0, w, borda, argb(aForte, vermelho, 0, 0), argb(0, vermelho, 0, 0));
        ctx.fillGradient(0, h - borda, w, h, argb(0, vermelho, 0, 0), argb(aForte, vermelho, 0, 0));
        int faixa = Math.max(1, (int) (w * (0.1f + 0.2f * k)) / 12);
        for (int i = 0; i < 12; i++) {
            int a = (int) (aForte * (1f - i / 12f) * 0.8f);
            ctx.fill(i * faixa, 0, (i + 1) * faixa, h, argb(a, vermelho, 0, 0));
            ctx.fill(w - (i + 1) * faixa, 0, w - i * faixa, h, argb(a, vermelho, 0, 0));
        }
    }
}
