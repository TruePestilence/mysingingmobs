package net.truepestilence.mysingingmod.event;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.truepestilence.mysingingmod.MySingingMod;
import net.truepestilence.mysingingmod.entity.ModEntityTypes;
import net.truepestilence.mysingingmod.entity.custom.*;

public class ModEvents {
    @Mod.EventBusSubscriber(modid = MySingingMod.MOD_ID)
    public static class ForgeEvents {
    }

    @Mod.EventBusSubscriber(modid = MySingingMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventBusEvents {
        @SubscribeEvent
        public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
            event.put(ModEntityTypes.MAMMOTT.get(), MammottEntity.setAttributes());
            event.put(ModEntityTypes.NOGGIN.get(), NogginEntity.setAttributes());
            event.put(ModEntityTypes.POTBELLY.get(), PotbellyEntity.setAttributes());
            event.put(ModEntityTypes.TOE_JAMMER.get(), ToeJammerEntity.setAttributes());
            event.put(ModEntityTypes.TWEEDLE.get(), TweedleEntity.setAttributes());
        }
    }
}
