package br.assombrado;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<CorpoSecoEntity> CORPO_SECO = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(BrasilAssombrado.MOD_ID, "corpo_seco"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, CorpoSecoEntity::new)
                    .dimensions(EntityDimensions.fixed(0.9f, 3.0f)).trackRangeBlocks(80).fireImmune().build());

    public static final EntityType<EnraizadoEntity> ENRAIZADO = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(BrasilAssombrado.MOD_ID, "enraizado"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, EnraizadoEntity::new)
                    .dimensions(EntityDimensions.fixed(0.8f, 1.0f)).trackRangeBlocks(48).build());

    public static void init() {
        FabricDefaultAttributeRegistry.register(CORPO_SECO, CorpoSecoEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(ENRAIZADO, EnraizadoEntity.createAttributes());
        ovo("corpo_seco_spawn_egg", new SpawnEggItem(CORPO_SECO, 0x54402C, 0xC01818, new Item.Settings()));
        ovo("enraizado_spawn_egg", new SpawnEggItem(ENRAIZADO, 0x3C462A, 0xC8FF50, new Item.Settings()));
    }

    private static void ovo(String nome, Item item) {
        Registry.register(Registries.ITEM, Identifier.of(BrasilAssombrado.MOD_ID, nome), item);
        ModItems.ALL.add(item);
    }
}
