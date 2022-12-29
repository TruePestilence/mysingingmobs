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
import net.truepestilence.mysingingmod.entity.custom.NogginEntity;

public class ModEntityTypes extends EntityType {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MySingingMod.MOD_ID);

    public static final RegistryObject<EntityType<NogginEntity>> NOGGIN =
            ENTITY_TYPES.register("noggin",
                () -> EntityType.Builder.of(NogginEntity::new, MobCategory.CREATURE)
                .sized(0.75f, 0.5625f)
                .build(new ResourceLocation(MySingingMod.MOD_ID, "noggin").toString()));

    public ModEntityTypes(EntityFactory pFactory, MobCategory pCategory, boolean pSerialize, boolean pSummon, boolean pFireImmune, boolean pCanSpawnFarFromPlayer, ImmutableSet pImmuneTo, EntityDimensions pDimensions, int pClientTrackingRange, int pUpdateInterval) {
        super(pFactory, pCategory, pSerialize, pSummon, pFireImmune, pCanSpawnFarFromPlayer, pImmuneTo, pDimensions, pClientTrackingRange, pUpdateInterval);
    }

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
