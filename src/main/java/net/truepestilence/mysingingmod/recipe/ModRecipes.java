package net.truepestilence.mysingingmod.recipe;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.truepestilence.mysingingmod.MySingingMod;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MySingingMod.MOD_ID);

    public static final RegistryObject<RecipeSerializer<BreedingStructureRecipe>> BREEDING_STRUCTURE_SERIALIZER =
            SERIALIZERS.register("breeding", () -> BreedingStructureRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}
