package net.truepestilence.msm;

import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.truepestilence.msm.block.ModBlocks;
import net.truepestilence.msm.block.entity.ModBlockEntities;
import net.truepestilence.msm.item.ModItems;
import net.truepestilence.msm.networking.ModNetworking;
import net.truepestilence.msm.screen.BreedingStructureScreen;
import net.truepestilence.msm.screen.ModMenuTypes;
import net.truepestilence.msm.world.feature.ModConfiguredFeatures;
import net.truepestilence.msm.world.feature.ModPlacedFeatures;
import org.slf4j.Logger;

import static net.truepestilence.msm.MySingingMod.MOD_ID;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MOD_ID)
public class MySingingMod
{
    public static final String MOD_ID = "msm";
    private static final Logger LOGGER = LogUtils.getLogger();
    public MySingingMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);

        ModConfiguredFeatures.register(modEventBus);
        ModPlacedFeatures.register(modEventBus);

        ModMenuTypes.register(modEventBus);

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(ModNetworking::register);
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(ModMenuTypes.BREEDING_STRUCTURE.get(), BreedingStructureScreen::new);
        }
    }
}
