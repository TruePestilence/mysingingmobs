package net.truepestilence.mysingingmod;

import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.truepestilence.mysingingmod.block.ModBlocks;
import net.truepestilence.mysingingmod.block.entity.ModBlockEntities;
import net.truepestilence.mysingingmod.entity.ModEntityTypes;
import net.truepestilence.mysingingmod.entity.client.*;
import net.truepestilence.mysingingmod.item.ModItems;
import net.truepestilence.mysingingmod.networking.ModNetworking;
import net.truepestilence.mysingingmod.recipe.ModRecipes;
import net.truepestilence.mysingingmod.screen.BreedingStructureScreen;
import net.truepestilence.mysingingmod.screen.CastleCoreScreen;
import net.truepestilence.mysingingmod.screen.ModMenuTypes;
import net.truepestilence.mysingingmod.screen.NurseryScreen;
import net.truepestilence.mysingingmod.world.feature.ModConfiguredFeatures;
import net.truepestilence.mysingingmod.world.feature.ModPlacedFeatures;
import org.slf4j.Logger;
import software.bernie.geckolib3.GeckoLib;

import static net.truepestilence.mysingingmod.MySingingMod.MOD_ID;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MOD_ID)
public class MySingingMod
{
    public static final String MOD_ID = "mysingingmod";
    private static final Logger LOGGER = LogUtils.getLogger();
    public MySingingMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);

        ModEntityTypes.register(modEventBus);

        ModConfiguredFeatures.register(modEventBus);
        ModPlacedFeatures.register(modEventBus);

        ModMenuTypes.register(modEventBus);
        ModRecipes.register(modEventBus);

        GeckoLib.initialize();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ModNetworking.register();
        });
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(ModMenuTypes.BREEDING_STRUCTURE.get(), BreedingStructureScreen::new);
            MenuScreens.register(ModMenuTypes.NURSERY.get(), NurseryScreen::new);
            MenuScreens.register(ModMenuTypes.CASTLE_CORE.get(), CastleCoreScreen::new);

            EntityRenderers.register(ModEntityTypes.MAMMOTT.get(), MammottRenderer::new);
            EntityRenderers.register(ModEntityTypes.NOGGIN.get(), NogginRenderer::new);
            EntityRenderers.register(ModEntityTypes.POTBELLY.get(), PotbellyRenderer::new);
            EntityRenderers.register(ModEntityTypes.TOE_JAMMER.get(), ToeJammerRenderer::new);
            EntityRenderers.register(ModEntityTypes.TWEEDLE.get(), TweedleRenderer::new);
        }
    }
}
