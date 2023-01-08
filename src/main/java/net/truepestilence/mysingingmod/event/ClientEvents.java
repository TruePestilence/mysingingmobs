package net.truepestilence.mysingingmod.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.truepestilence.mysingingmod.MySingingMod;
import net.truepestilence.mysingingmod.block.entity.ModBlockEntities;
import net.truepestilence.mysingingmod.block.entity.renderer.BreedingStructureEntityRenderer;
import net.truepestilence.mysingingmod.block.entity.renderer.NurseryEntityRenderer;
import net.truepestilence.mysingingmod.entity.ModEntityTypes;
import net.truepestilence.mysingingmod.entity.custom.NogginEntity;
import net.truepestilence.mysingingmod.entity.custom.ToeJammerEntity;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = MySingingMod.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
    }

    @Mod.EventBusSubscriber(modid = MySingingMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(ModBlockEntities.BREEDING_STRUCTURE.get(),
                    BreedingStructureEntityRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntities.NURSERY.get(),
                    NurseryEntityRenderer::new);
        }
    }
}
