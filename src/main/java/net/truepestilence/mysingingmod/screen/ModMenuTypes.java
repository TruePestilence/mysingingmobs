package net.truepestilence.mysingingmod.screen;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.truepestilence.mysingingmod.MySingingMod;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, MySingingMod.MOD_ID);

    public static final RegistryObject<MenuType<BreedingStructureMenu>> BREEDING_STRUCTURE =
            registerMenuType(BreedingStructureMenu::new, "breeding_structure_menu");
    public static final RegistryObject<MenuType<NurseryMenu>> NURSERY =
            registerMenuType(NurseryMenu::new, "nursery_menu");
    public static final RegistryObject<MenuType<CastleCoreMenu>> CASTLE_CORE =
            registerMenuType(CastleCoreMenu::new, "castle_core_menu");

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory, String name){
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus eventBus){
        MENUS.register(eventBus);
    }
}
