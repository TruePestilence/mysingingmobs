package net.truepestilence.msm.world.feature;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.truepestilence.msm.MySingingMod;
import net.truepestilence.msm.block.ModBlocks;

import java.util.List;

public class ModConfiguredFeatures {
    public static final DeferredRegister<ConfiguredFeature<?,?>> CONFIGURED_FEATURES =
            DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, MySingingMod.MOD_ID);

    public static void register(IEventBus eventBus) {
        CONFIGURED_FEATURES.register(eventBus);
    }

    public static final Supplier<List<OreConfiguration.TargetBlockState>> VEGIDIA_ORES = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.VEGIDIA_ORE.get().defaultBlockState()),
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_VEGIDIA_ORE.get().defaultBlockState())));
    public static final Supplier<List<OreConfiguration.TargetBlockState>> FROZIUM_ORES = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.FROZIUM_ORE.get().defaultBlockState()),
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_FROZIUM_ORE.get().defaultBlockState())));
    public static final Supplier<List<OreConfiguration.TargetBlockState>> SKYLITE_ORES = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.SKYLITE_ORE.get().defaultBlockState()),
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_SKYLITE_ORE.get().defaultBlockState())));
    public static final Supplier<List<OreConfiguration.TargetBlockState>> AQUANINE_ORES = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.AQUANINE_ORE.get().defaultBlockState()),
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_AQUANINE_ORE.get().defaultBlockState())));
    public static final Supplier<List<OreConfiguration.TargetBlockState>> STONYX_ORES = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.STONYX_ORE.get().defaultBlockState()),
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_STONYX_ORE.get().defaultBlockState())));

    public static final Supplier<List<OreConfiguration.TargetBlockState>> OW_PYROZITE_ORES = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.PYROZITE_ORE.get().defaultBlockState()),
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_PYROZITE_ORE.get().defaultBlockState())));
    public static final Supplier<List<OreConfiguration.TargetBlockState>> N_PYROZITE_ORES = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(OreFeatures.NETHER_ORE_REPLACEABLES, ModBlocks.NETHER_PYROZITE_ORE.get().defaultBlockState())));
    public static final RegistryObject<ConfiguredFeature<?,?>> VEGIDIA_ORE = CONFIGURED_FEATURES.register("vegidia_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(VEGIDIA_ORES.get(),4)));
    public static final RegistryObject<ConfiguredFeature<?,?>> FROZIUM_ORE = CONFIGURED_FEATURES.register("frozium_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(FROZIUM_ORES.get(),4)));
    public static final RegistryObject<ConfiguredFeature<?,?>> SKYLITE_ORE = CONFIGURED_FEATURES.register("skylite_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(SKYLITE_ORES.get(),4)));
    public static final RegistryObject<ConfiguredFeature<?,?>> AQUANINE_ORE = CONFIGURED_FEATURES.register("aquanine_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(AQUANINE_ORES.get(),4)));
    public static final RegistryObject<ConfiguredFeature<?,?>> STONYX_ORE = CONFIGURED_FEATURES.register("stonyx_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(STONYX_ORES.get(),4)));

    public static final RegistryObject<ConfiguredFeature<?,?>> OW_PYROZITE_ORE = CONFIGURED_FEATURES.register("ow_pyrozite_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OW_PYROZITE_ORES.get(),4)));
    public static final RegistryObject<ConfiguredFeature<?,?>> N_PYROZITE_ORE = CONFIGURED_FEATURES.register("n_pyrozite_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(N_PYROZITE_ORES.get(),4)));
}
