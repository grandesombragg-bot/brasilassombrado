package br.assombrado;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class ModItems {
    public static final List<Item> ALL = new ArrayList<>();

    public static final Item CACO_DE_VIDRO = register("caco_de_vidro");
    public static final Item OLEO_DE_LAMPIAO = register("oleo_de_lampiao");
    public static final Item SAL_DE_PROTECAO = register("sal_de_protecao");
    public static final Item CABELO_DO_CORPO_SECO = register("cabelo_do_corpo_seco");

    private static Item register(String name) {
        Item item = Registry.register(Registries.ITEM, Identifier.of(BrasilAssombrado.MOD_ID, name), new Item(new Item.Settings()));
        ALL.add(item);
        return item;
    }

    public static void init() {
    }
}
