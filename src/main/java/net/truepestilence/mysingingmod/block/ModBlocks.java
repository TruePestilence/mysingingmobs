package net.truepestilence.mysingingmod.block;

import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.truepestilence.mysingingmod.MySingingMod;
import net.truepestilence.mysingingmod.block.custom.CastleCore;
import net.truepestilence.mysingingmod.block.custom.MonsterEgg;
import net.truepestilence.mysingingmod.block.custom.Nursery;
import net.truepestilence.mysingingmod.item.ModCreativeModeTab;
import net.truepestilence.mysingingmod.item.ModItems;
import net.truepestilence.mysingingmod.block.custom.BreedingStructure;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, MySingingMod.MOD_ID);

    public static final RegistryObject<Block> VEGIDIA_BLOCK = registerBlock("vegidia_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.AMETHYST)
                    .strength(5.0F,5.0F).requiresCorrectToolForDrops()),
            ModCreativeModeTab.MSM_TAB);
    public static final RegistryObject<Block> FROZIUM_BLOCK = registerBlock("frozium_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.AMETHYST)
                    .strength(5.0F,5.0F).requiresCorrectToolForDrops()),
            ModCreativeModeTab.MSM_TAB);
    public static final RegistryObject<Block> SKYLITE_BLOCK = registerBlock("skylite_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.AMETHYST)
                    .strength(5.0F,5.0F).requiresCorrectToolForDrops()),
            ModCreativeModeTab.MSM_TAB);
    public static final RegistryObject<Block> AQUANINE_BLOCK = registerBlock("aquanine_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.AMETHYST)
                    .strength(5.0F,5.0F).requiresCorrectToolForDrops()),
            ModCreativeModeTab.MSM_TAB);
    public static final RegistryObject<Block> STONYX_BLOCK = registerBlock("stonyx_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.AMETHYST)
                    .strength(5.0F,5.0F).requiresCorrectToolForDrops()),
            ModCreativeModeTab.MSM_TAB);
    public static final RegistryObject<Block> PYROZITE_BLOCK = registerBlock("pyrozite_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.AMETHYST)
                    .strength(5.0F,5.0F).requiresCorrectToolForDrops()),
            ModCreativeModeTab.MSM_TAB);

    public static final RegistryObject<Block> CONFETTITE_BLOCK = registerBlock("confettite_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.AMETHYST)
                    .strength(5.0F,5.0F).requiresCorrectToolForDrops()),
            ModCreativeModeTab.MSM_TAB);
    public static final RegistryObject<Block> NEBULOX_BLOCK = registerBlock("nebulox_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.AMETHYST)
                    .strength(5.0F,5.0F).requiresCorrectToolForDrops()),
            ModCreativeModeTab.MSM_TAB);
    public static final RegistryObject<Block> AURORIUM_BLOCK = registerBlock("aurorium_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.AMETHYST)
                    .strength(5.0F,5.0F).requiresCorrectToolForDrops()),
            ModCreativeModeTab.MSM_TAB);
    public static final RegistryObject<Block> QUARRITZ_BLOCK = registerBlock("quarritz_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.AMETHYST)
                    .strength(5.0F,5.0F).requiresCorrectToolForDrops()),
            ModCreativeModeTab.MSM_TAB);

    public static final RegistryObject<Block> VEGIDIA_ORE = registerBlock("vegidia_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(3.0F,3.0F).requiresCorrectToolForDrops(), UniformInt.of(1,4)),
            ModCreativeModeTab.MSM_TAB);
    public static final RegistryObject<Block> DEEPSLATE_VEGIDIA_ORE = registerBlock("deepslate_vegidia_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(4.5F,4.5F).requiresCorrectToolForDrops(), UniformInt.of(1,4)),
            ModCreativeModeTab.MSM_TAB);

    public static final RegistryObject<Block> FROZIUM_ORE = registerBlock("frozium_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(3.0F,3.0F).requiresCorrectToolForDrops(), UniformInt.of(1,4)),
            ModCreativeModeTab.MSM_TAB);
    public static final RegistryObject<Block> DEEPSLATE_FROZIUM_ORE = registerBlock("deepslate_frozium_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(4.5F,4.5F).requiresCorrectToolForDrops(), UniformInt.of(1,4)),
            ModCreativeModeTab.MSM_TAB);

    public static final RegistryObject<Block> SKYLITE_ORE = registerBlock("skylite_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(3.0F,3.0F).requiresCorrectToolForDrops(), UniformInt.of(1,4)),
            ModCreativeModeTab.MSM_TAB);
    public static final RegistryObject<Block> DEEPSLATE_SKYLITE_ORE = registerBlock("deepslate_skylite_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(4.5F,4.5F).requiresCorrectToolForDrops(), UniformInt.of(1,4)),
            ModCreativeModeTab.MSM_TAB);

    public static final RegistryObject<Block> AQUANINE_ORE = registerBlock("aquanine_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(3.0F,3.0F).requiresCorrectToolForDrops(), UniformInt.of(1,4)),
            ModCreativeModeTab.MSM_TAB);
    public static final RegistryObject<Block> DEEPSLATE_AQUANINE_ORE = registerBlock("deepslate_aquanine_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(4.5F,4.5F).requiresCorrectToolForDrops(), UniformInt.of(1,4)),
            ModCreativeModeTab.MSM_TAB);

    public static final RegistryObject<Block> STONYX_ORE = registerBlock("stonyx_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(3.0F,3.0F).requiresCorrectToolForDrops(), UniformInt.of(1,4)),
            ModCreativeModeTab.MSM_TAB);
    public static final RegistryObject<Block> DEEPSLATE_STONYX_ORE = registerBlock("deepslate_stonyx_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(4.5F,4.5F).requiresCorrectToolForDrops(), UniformInt.of(1,4)),
            ModCreativeModeTab.MSM_TAB);

    public static final RegistryObject<Block> PYROZITE_ORE = registerBlock("pyrozite_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(3.0F,3.0F).requiresCorrectToolForDrops(), UniformInt.of(1,4)),
            ModCreativeModeTab.MSM_TAB);
    public static final RegistryObject<Block> DEEPSLATE_PYROZITE_ORE = registerBlock("deepslate_pyrozite_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(4.5F,4.5F).requiresCorrectToolForDrops(), UniformInt.of(1,4)),
            ModCreativeModeTab.MSM_TAB);
    public static final RegistryObject<Block> NETHER_PYROZITE_ORE = registerBlock("nether_pyrozite_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(3F,3F).requiresCorrectToolForDrops(), UniformInt.of(1,4)),
            ModCreativeModeTab.MSM_TAB);

    public static final RegistryObject<Block> BREEDING_STRUCTURE = registerBlock("breeding_structure",
            () -> new BreedingStructure(BlockBehaviour.Properties.of(Material.WOOD)
                    .strength(2F,2F).noOcclusion()),
            ModCreativeModeTab.MSM_TAB);
    public static final RegistryObject<Block> NURSERY = registerBlock("nursery",
            () -> new Nursery(BlockBehaviour.Properties.of(Material.WOOD)
                    .strength(2F,2F).noOcclusion()),
            ModCreativeModeTab.MSM_TAB);
    public static final RegistryObject<Block> CASTLE_CORE = registerBlock("castle_core",
            () -> new CastleCore(BlockBehaviour.Properties.of(Material.AMETHYST)
                    .strength(3F,10F).requiresCorrectToolForDrops()),
            ModCreativeModeTab.MSM_TAB);

    //Eggs
    public static final RegistryObject<Block> EGG_NOGGIN = registerBlock("egg_noggin", () -> new MonsterEgg(BlockBehaviour.Properties.of(Material.EGG).strength(0.5F,0.5F).noOcclusion()), ModCreativeModeTab.EGG_TAB);
    public static final RegistryObject<Block> EGG_MAMMOTT = registerBlock("egg_mammott", () -> new MonsterEgg(BlockBehaviour.Properties.of(Material.EGG).strength(0.5F,0.5F).noOcclusion()), ModCreativeModeTab.EGG_TAB);
    public static final RegistryObject<Block> EGG_TOEJAMMER = registerBlock("egg_toejammer", () -> new MonsterEgg(BlockBehaviour.Properties.of(Material.EGG).strength(0.5F,0.5F).noOcclusion()), ModCreativeModeTab.EGG_TAB);
    public static final RegistryObject<Block> EGG_POTBELLY = registerBlock("egg_potbelly", () -> new MonsterEgg(BlockBehaviour.Properties.of(Material.EGG).strength(0.5F,0.5F).noOcclusion()), ModCreativeModeTab.EGG_TAB);
    public static final RegistryObject<Block> EGG_TWEEDLE = registerBlock("egg_tweedle", () -> new MonsterEgg(BlockBehaviour.Properties.of(Material.EGG).strength(0.5F,0.5F).noOcclusion()), ModCreativeModeTab.EGG_TAB);


    public static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}