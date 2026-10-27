package br.assombrado;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;

/** Maldição Seca: impede a regeneração e faz a fome vir mais rápido. */
public class MaldicaoSecaEffect extends StatusEffect {
    public MaldicaoSecaEffect() {
        super(StatusEffectCategory.HARMFUL, 0x5A4630);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        entity.removeStatusEffect(StatusEffects.REGENERATION);
        if (entity instanceof PlayerEntity jogador) {
            jogador.addExhaustion(0.02f);
        }
        return true;
    }
}
