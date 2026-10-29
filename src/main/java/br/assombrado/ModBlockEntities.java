package br.assombrado;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static final BlockEntityType<MirrorBlockEntity> ESPELHO = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            Identifier.of(BrasilAssombrado.MOD_ID, "espelho"),
            FabricBlockEntityTypeBuilder.create(MirrorBlockEntity::new, ModBlocks.ESPELHO_RACHADO).build());

    public static final BlockEntityType<GatilhoBlockEntity> GATILHO = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            Identifier.of(BrasilAssombrado.MOD_ID, "gatilho"),
            FabricBlockEntityTypeBuilder.create(GatilhoBlockEntity::new, ModBlocks.GATILHO_CORPO_SECO).build());

    public static void init() {
    }
}
