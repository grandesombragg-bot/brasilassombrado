package br.assombrado;

import net.fabricmc.api.ModInitializer;

public class BrasilAssombrado implements ModInitializer {
    public static final String MOD_ID = "brasil_assombrado";

    @Override
    public void onInitialize() {
        ModItems.init();
        ModBlocks.init();
        ModBlockEntities.init();
        ModSounds.init();
        ModEffects.init();
        ModEntities.init();
        ModItemGroup.init();
    }
}
