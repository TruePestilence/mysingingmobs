package net.truepestilence.genera.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.truepestilence.genera.Genera;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Genera.MOD_ID);

    public static final RegistryObject<Item> TEMPERA_SHARD = ITEMS.register("tempera_shard",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.GENERA_TAB)));

    public static final RegistryObject<Item> TEMPERA_STONE = ITEMS.register("tempera_stone",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.GENERA_TAB).stacksTo(1)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
