package net.truepestilence.msm.block.entity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.truepestilence.msm.MySingingMod;
import net.truepestilence.msm.block.ModBlocks;
import net.truepestilence.msm.block.custom.BreedingStructure;

public class ModBlockEntities {
    public static DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MySingingMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<BreedingStructureEntity>> BREEDING_STRUCTURE =
            BLOCK_ENTITIES.register("breeding_structure", () ->
                    BlockEntityType.Builder.of(BreedingStructureEntity::new,
                            ModBlocks.BREEDING_STRUCTURE.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
