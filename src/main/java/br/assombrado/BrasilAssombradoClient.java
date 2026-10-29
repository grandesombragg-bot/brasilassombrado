package br.assombrado;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class BrasilAssombradoClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // blocos com partes transparentes (cacos de vidro, vidro do lampião)
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),
                ModBlocks.MURO_TIJOLO_VIDRO, ModBlocks.MURO_PEDRA_VIDRO, ModBlocks.MURO_CONCRETO_VIDRO,
                ModBlocks.LAMPIAO_VELHO, ModBlocks.RADIO_ANTIGO, ModBlocks.VELA_DE_PROMESSA);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ESPELHO_RACHADO, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ESPELHO_OLHOS, RenderLayer.getCutout());
        BlockEntityRendererFactories.register(ModBlockEntities.ESPELHO, MirrorRenderer::new);
        MirrorEffect.init();
        EntityModelLayerRegistry.registerModelLayer(CorpoSecoModel.LAYER, CorpoSecoModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(EnraizadoModel.LAYER, EnraizadoModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.CORPO_SECO, CorpoSecoRenderer::new);
        EntityRendererRegistry.register(ModEntities.ENRAIZADO, EnraizadoRenderer::new);
    }
}
