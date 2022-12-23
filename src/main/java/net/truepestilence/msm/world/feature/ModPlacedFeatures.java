package net.truepestilence.msm.world.feature;

import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.truepestilence.msm.MySingingMod;

import java.util.List;

public class ModPlacedFeatures {
    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES =
            DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, MySingingMod.MOD_ID);

    public static void register(IEventBus eventBus) {
        PLACED_FEATURES.register(eventBus);
    }

    private static List<PlacementModifier> orePlacement(PlacementModifier p_195347_, PlacementModifier p_195348_) {
        return List.of(p_195347_, InSquarePlacement.spread(), p_195348_, BiomeFilter.biome());
    }

    private static List<PlacementModifier> commonOrePlacement(int p_195344_, PlacementModifier p_195345_) {
        return orePlacement(CountPlacement.of(p_195344_), p_195345_);
    }

    private static List<PlacementModifier> rareOrePlacement(int p_195350_, PlacementModifier p_195351_) {
        return orePlacement(RarityFilter.onAverageOnceEvery(p_195350_), p_195351_);
    }

    public static final RegistryObject<PlacedFeature> VEGIDIA_ORE_PLACED = PLACED_FEATURES.register("vegidia_ore_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.VEGIDIA_ORE.getHolder().get(),
                    commonOrePlacement(8, // VeinsPerChunk
                            HeightRangePlacement.triangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(128)))));
    public static final RegistryObject<PlacedFeature> FROZIUM_ORE_PLACED = PLACED_FEATURES.register("frozium_ore_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.FROZIUM_ORE.getHolder().get(),
                    commonOrePlacement(8, // VeinsPerChunk
                            HeightRangePlacement.triangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(128)))));
    public static final RegistryObject<PlacedFeature> SKYLITE_ORE_PLACED = PLACED_FEATURES.register("skylite_ore_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.SKYLITE_ORE.getHolder().get(),
                    commonOrePlacement(8, // VeinsPerChunk
                            HeightRangePlacement.triangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(128)))));
    public static final RegistryObject<PlacedFeature> AQUANINE_ORE_PLACED = PLACED_FEATURES.register("aquanine_ore_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.AQUANINE_ORE.getHolder().get(),
                    commonOrePlacement(8, // VeinsPerChunk
                            HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(64)))));
    public static final RegistryObject<PlacedFeature> STONYX_ORE_PLACED = PLACED_FEATURES.register("stonyx_ore_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.STONYX_ORE.getHolder().get(),
                    commonOrePlacement(8, // VeinsPerChunk
                            HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(64)))));

}
