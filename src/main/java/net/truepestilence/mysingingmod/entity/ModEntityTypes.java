package net.truepestilence.mysingingmod.entity;

import com.google.common.collect.ImmutableSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.truepestilence.mysingingmod.MySingingMod;
import net.truepestilence.mysingingmod.entity.custom.*;

public class ModEntityTypes extends EntityType {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MySingingMod.MOD_ID);

    public static final RegistryObject<EntityType<MammottEntity>> MAMMOTT =
            ENTITY_TYPES.register("mammott",
                    () -> EntityType.Builder.of(MammottEntity::new, MobCategory.CREATURE)
                            .sized(1f, 2f)
                            .build(new ResourceLocation(MySingingMod.MOD_ID, "mammott").toString()));
    public static final RegistryObject<EntityType<NogginEntity>> NOGGIN =
            ENTITY_TYPES.register("noggin",
                () -> EntityType.Builder.of(NogginEntity::new, MobCategory.CREATURE)
                .sized(0.75f, 0.75f)
                .build(new ResourceLocation(MySingingMod.MOD_ID, "noggin").toString()));
    public static final RegistryObject<EntityType<PotbellyEntity>> POTBELLY =
            ENTITY_TYPES.register("potbelly",
                    () -> EntityType.Builder.of(PotbellyEntity::new, MobCategory.CREATURE)
                            .sized(0.5f, 1f)
                            .build(new ResourceLocation(MySingingMod.MOD_ID, "potbelly").toString()));
    public static final RegistryObject<EntityType<ToeJammerEntity>> TOE_JAMMER =
            ENTITY_TYPES.register("toe_jammer",
                    () -> EntityType.Builder.of(ToeJammerEntity::new, MobCategory.CREATURE)
                            .sized(1f, 1f)
                            .build(new ResourceLocation(MySingingMod.MOD_ID, "toe_jammer").toString()));
    public static final RegistryObject<EntityType<TweedleEntity>> TWEEDLE =
            ENTITY_TYPES.register("tweedle",
                    () -> EntityType.Builder.of(TweedleEntity::new, MobCategory.CREATURE)
                            .sized(1f, 1.5f)
                            .build(new ResourceLocation(MySingingMod.MOD_ID, "tweedle").toString()));


    public ModEntityTypes(EntityFactory<net.minecraft.world.entity.Entity> pFactory, MobCategory pCategory, boolean pSerialize, boolean pSummon, boolean pFireImmune, boolean pCanSpawnFarFromPlayer, ImmutableSet<net.minecraft.world.level.block.Block> pImmuneTo, EntityDimensions pDimensions, int pClientTrackingRange, int pUpdateInterval) {
        super(pFactory, pCategory, pSerialize, pSummon, pFireImmune, pCanSpawnFarFromPlayer, pImmuneTo, pDimensions, pClientTrackingRange, pUpdateInterval);
    }

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
