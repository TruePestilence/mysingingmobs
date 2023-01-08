package net.truepestilence.mysingingmod.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.truepestilence.mysingingmod.MySingingMod;
import net.truepestilence.mysingingmod.item.custom.MonsterSigil;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MySingingMod.MOD_ID);

    public static final RegistryObject<Item> VEGIDIA = ITEMS.register("vegidia",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.MSM_TAB)));
    public static final RegistryObject<Item> FROZIUM = ITEMS.register("frozium",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.MSM_TAB)));
    public static final RegistryObject<Item> SKYLITE = ITEMS.register("skylite",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.MSM_TAB)));
    public static final RegistryObject<Item> AQUANINE = ITEMS.register("aquanine",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.MSM_TAB)));
    public static final RegistryObject<Item> STONYX = ITEMS.register("stonyx",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.MSM_TAB)));
    public static final RegistryObject<Item> PYROZITE = ITEMS.register("pyrozite",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.MSM_TAB)));

    public static final RegistryObject<Item> CONFETTITE = ITEMS.register("confettite",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.MSM_TAB)));
    public static final RegistryObject<Item> NEBULOX = ITEMS.register("nebulox",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.MSM_TAB)));
    public static final RegistryObject<Item> AURORIUM = ITEMS.register("aurorium",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.MSM_TAB)));
    public static final RegistryObject<Item> QUARRITZ = ITEMS.register("quarritz",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.MSM_TAB)));

    public static final RegistryObject<Item> KRYSTILLIUM = ITEMS.register("krystillium",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.MSM_TAB)));
    public static final RegistryObject<Item> LIFE_FORMULA = ITEMS.register("life_formula",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.MSM_TAB).stacksTo(1)));
    public static final RegistryObject<Item> MONSTER_SIGIL = ITEMS.register("monster_sigil",
            () -> new MonsterSigil(new Item.Properties().tab(ModCreativeModeTab.MSM_TAB).stacksTo(1)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
