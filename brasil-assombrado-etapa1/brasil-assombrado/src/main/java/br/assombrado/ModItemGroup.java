package br.assombrado;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroup {
    public static final ItemGroup GERAL = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(BrasilAssombrado.MOD_ID, "geral"),
            FabricItemGroup.builder()
                    .icon(() -> new ItemStack(ModBlocks.LAMPIAO_VELHO))
                    .displayName(Text.translatable("itemGroup.brasil_assombrado.geral"))
                    .entries((context, entries) -> {
                        ModItems.ALL.forEach(entries::add);
                        ModBlocks.ALL.forEach(entries::add);
                    })
                    .build());

    public static void init() {
    }
}
