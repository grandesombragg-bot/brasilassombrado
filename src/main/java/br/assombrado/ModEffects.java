package br.assombrado;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class ModEffects {
    public static final RegistryEntry<StatusEffect> MALDICAO_SECA = Registry.registerReference(
            Registries.STATUS_EFFECT, Identifier.of(BrasilAssombrado.MOD_ID, "maldicao_seca"), new MaldicaoSecaEffect());

    public static void init() {
    }
}
