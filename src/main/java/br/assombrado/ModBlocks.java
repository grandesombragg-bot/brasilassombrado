package br.assombrado;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class ModBlocks {
    public static final List<Block> ALL = new ArrayList<>();

    public static final Block MURO_TIJOLO_VIDRO = register("muro_tijolo_vidro",
            new GlassWallBlock(AbstractBlock.Settings.create().strength(2.0f, 6.0f).sounds(BlockSoundGroup.STONE)));
    public static final Block MURO_PEDRA_VIDRO = register("muro_pedra_vidro",
            new GlassWallBlock(AbstractBlock.Settings.create().strength(2.0f, 6.0f).sounds(BlockSoundGroup.STONE)));
    public static final Block MURO_CONCRETO_VIDRO = register("muro_concreto_vidro",
            new GlassWallBlock(AbstractBlock.Settings.create().strength(2.5f, 7.0f).sounds(BlockSoundGroup.STONE)));

    public static final Block TIJOLOS_ENVELHECIDOS = register("tijolos_envelhecidos",
            new Block(AbstractBlock.Settings.create().strength(2.0f, 6.0f).sounds(BlockSoundGroup.STONE)));
    public static final Block TABUAS_DESGASTADAS = register("tabuas_desgastadas",
            new Block(AbstractBlock.Settings.create().strength(1.5f, 3.0f).sounds(BlockSoundGroup.WOOD)));
    public static final Block ESPELHO_RACHADO = register("espelho_rachado",
            new Block(AbstractBlock.Settings.create().strength(0.5f).sounds(BlockSoundGroup.GLASS)));

    public static final Block LAMPIAO_VELHO = register("lampiao_velho",
            new ShapedBlock(AbstractBlock.Settings.create().strength(1.0f).sounds(BlockSoundGroup.LANTERN)
                    .nonOpaque().luminance(state -> 13),
                    Block.createCuboidShape(5, 0, 5, 11, 10, 11)));
    public static final Block VELA_DE_PROMESSA = register("vela_de_promessa",
            new VelaBlock(AbstractBlock.Settings.create().breakInstantly().sounds(BlockSoundGroup.CANDLE)
                    .nonOpaque().luminance(state -> 7),
                    Block.createCuboidShape(5.5, 0, 5.5, 10.5, 5, 10.5)));
    public static final Block TOTEM_DE_PROTECAO = register("totem_de_protecao",
            new ShapedBlock(AbstractBlock.Settings.create().strength(2.0f, 6.0f).sounds(BlockSoundGroup.WOOD)
                    .nonOpaque(),
                    Block.createCuboidShape(4, 0, 4, 12, 16, 12)));
    public static final Block RADIO_ANTIGO = register("radio_antigo",
            new RadioBlock(AbstractBlock.Settings.create().strength(1.0f).sounds(BlockSoundGroup.METAL)
                    .nonOpaque()));

    private static Block register(String name, Block block) {
        Identifier id = Identifier.of(BrasilAssombrado.MOD_ID, name);
        Registry.register(Registries.BLOCK, id, block);
        Registry.register(Registries.ITEM, id, new BlockItem(block, new Item.Settings()));
        ALL.add(block);
        return block;
    }

    public static void init() {
    }
}
