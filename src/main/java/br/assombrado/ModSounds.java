package br.assombrado;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {
    public static final SoundEvent CS_AMBIENTE = reg("corpo_seco.ambiente");
    public static final SoundEvent CS_FERIDO = reg("corpo_seco.ferido");
    public static final SoundEvent CS_GRITO = reg("corpo_seco.grito");
    public static final SoundEvent CS_MORTE = reg("corpo_seco.morte");
    public static final SoundEvent CS_PASSO = reg("corpo_seco.passo");
    public static final SoundEvent CS_QUEBRAR = reg("corpo_seco.quebrar");
    public static final SoundEvent CS_INVOCAR = reg("corpo_seco.invocar");
    public static final SoundEvent ENR_AMBIENTE = reg("enraizado.ambiente");
    public static final SoundEvent ENR_MORTE = reg("enraizado.morte");

    private static SoundEvent reg(String nome) {
        Identifier id = Identifier.of(BrasilAssombrado.MOD_ID, nome);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void init() {
    }
}
