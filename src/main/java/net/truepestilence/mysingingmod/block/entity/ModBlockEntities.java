package net.truepestilence.mysingingmod.block.entity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.truepestilence.mysingingmod.MySingingMod;
import net.truepestilence.mysingingmod.block.ModBlocks;

public class ModBlockEntities {
    public static DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MySingingMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<BreedingStructureEntity>> BREEDING_STRUCTURE =
            BLOCK_ENTITIES.register("breeding_structure", () ->
                    BlockEntityType.Builder.of(BreedingStructureEntity::new,
                            ModBlocks.BREEDING_STRUCTURE.get()).build(null));
    public static final RegistryObject<BlockEntityType<NurseryEntity>> NURSERY =
            BLOCK_ENTITIES.register("nursery", () ->
                    BlockEntityType.Builder.of(NurseryEntity::new,
                            ModBlocks.NURSERY.get()).build(null));
    public static final RegistryObject<BlockEntityType<CastleCoreEntity>> CASTLE_CORE =
            BLOCK_ENTITIES.register("castle_core", () ->
                    BlockEntityType.Builder.of(CastleCoreEntity::new,
                            ModBlocks.CASTLE_CORE.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
