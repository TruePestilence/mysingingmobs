package net.truepestilence.mysingingmod.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.RegistryObject;
import net.truepestilence.mysingingmod.MySingingMod;
import net.truepestilence.mysingingmod.block.ModBlocks;
import net.truepestilence.mysingingmod.block.custom.BreedingStructure;
import net.truepestilence.mysingingmod.item.ModItems;
import net.truepestilence.mysingingmod.networking.ModNetworking;
import net.truepestilence.mysingingmod.networking.packet.ItemStackSyncS2CPacket;
import net.truepestilence.mysingingmod.recipe.BreedingStructureRecipe;
import net.truepestilence.mysingingmod.screen.BreedingStructureMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class BreedingStructureEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch(slot) {
                case 0, 1 -> stack.is(ModItems.MONSTER_SIGIL.get());
                case 2 -> stack.is(monsterTag);
                default -> super.isItemValid(slot, stack);
            };
        }
    };

    private final Map<Direction, LazyOptional<WrappedHandler>> wrappedHandlerMap =
            Map.of(Direction.DOWN, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == 2,
                    (index, stack) -> itemHandler.isItemValid(0, stack) || itemHandler.isItemValid(1, stack))),
                    Direction.NORTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (index) -> index == 2,
                            (index, stack) -> itemHandler.isItemValid(0, stack) || itemHandler.isItemValid(1, stack))),
                    Direction.SOUTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == 2,
                            (index, stack) -> itemHandler.isItemValid(0, stack) || itemHandler.isItemValid(1, stack))),
                    Direction.EAST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == 2,
                            (index, stack) -> itemHandler.isItemValid(0, stack) || itemHandler.isItemValid(1, stack))),
                    Direction.WEST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (index) -> index == 2,
                            (index, stack) -> itemHandler.isItemValid(0, stack) || itemHandler.isItemValid(1, stack))));

    public static final TagKey<Item> monsterTag = ItemTags.create(new ResourceLocation(MySingingMod.MOD_ID, "all_monsters"));
    public static final TagKey<Item> earthTag = ItemTags.create(new ResourceLocation(MySingingMod.MOD_ID, "earth_element"));
    public static final TagKey<Item> coldTag = ItemTags.create(new ResourceLocation(MySingingMod.MOD_ID, "cold_element"));
    public static final TagKey<Item> airTag = ItemTags.create(new ResourceLocation(MySingingMod.MOD_ID, "air_element"));
    public static final TagKey<Item> plantTag = ItemTags.create(new ResourceLocation(MySingingMod.MOD_ID, "plant_element"));
    public static final TagKey<Item> waterTag = ItemTags.create(new ResourceLocation(MySingingMod.MOD_ID, "water_element"));
    public static final TagKey<Item> fireTag = ItemTags.create(new ResourceLocation(MySingingMod.MOD_ID, "fire_element"));
    public static final TagKey<Item> psychicTag = ItemTags.create(new ResourceLocation(MySingingMod.MOD_ID, "psychic_element"));
    public static final TagKey<Item> faerieTag = ItemTags.create(new ResourceLocation(MySingingMod.MOD_ID, "faerie_element"));
    public static final TagKey<Item> boneTag = ItemTags.create(new ResourceLocation(MySingingMod.MOD_ID, "bone_element"));
    public static final TagKey<Item> lightTag = ItemTags.create(new ResourceLocation(MySingingMod.MOD_ID, "light_element"));
    public static final TagKey<Item> plasmaTag = ItemTags.create(new ResourceLocation(MySingingMod.MOD_ID, "plasma_element"));
    public static final TagKey<Item> shadowTag = ItemTags.create(new ResourceLocation(MySingingMod.MOD_ID, "shadow_element"));
    public static final TagKey<Item> mechTag = ItemTags.create(new ResourceLocation(MySingingMod.MOD_ID, "mech_element"));
    public static final TagKey<Item> crystalTag = ItemTags.create(new ResourceLocation(MySingingMod.MOD_ID, "crystal_element"));
    public static final TagKey<Item> poisonTag = ItemTags.create(new ResourceLocation(MySingingMod.MOD_ID, "poison_element"));
    public static final TagKey<Item> seasonalTag = ItemTags.create(new ResourceLocation(MySingingMod.MOD_ID, "seasonal_element"));
    public static final TagKey<Item> mythicalTag = ItemTags.create(new ResourceLocation(MySingingMod.MOD_ID, "mythical_element"));
    public static final TagKey<Item> legendaryTag = ItemTags.create(new ResourceLocation(MySingingMod.MOD_ID, "legendary_element"));

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 60;
    protected boolean active = false;

    public BreedingStructureEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BREEDING_STRUCTURE.get(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> BreedingStructureEntity.this.progress;
                    case 1 -> BreedingStructureEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> BreedingStructureEntity.this.progress = value;
                    case 1 -> BreedingStructureEntity.this.maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Breeding Structure");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new BreedingStructureMenu(id, inventory, this, this.data);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            if (side == null) {
                return lazyItemHandler.cast();
            }

            if (wrappedHandlerMap.containsKey(side)) {
                Direction localDir = this.getBlockState().getValue(BreedingStructure.FACING);

                if (side == Direction.UP || side == Direction.DOWN) {
                    return wrappedHandlerMap.get(side).cast();
                }

                return switch (localDir) {
                    default -> wrappedHandlerMap.get(side.getOpposite()).cast();
                    case EAST -> wrappedHandlerMap.get(side.getClockWise()).cast();
                    case SOUTH -> wrappedHandlerMap.get(side).cast();
                    case WEST -> wrappedHandlerMap.get(side.getCounterClockWise()).cast();
                };
            }
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("inventory", itemHandler.serializeNBT());
        nbt.putInt("breeding_structure.progress", this.progress);
        nbt.putBoolean("breeding_structure.active", this.active);

        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        progress = nbt.getInt("breeding_structure.progress");
        active = nbt.getBoolean("breeding_structure.active");
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, BreedingStructureEntity entity) {
        if(level.isClientSide()) {
            return;
        }
        ModNetworking.sendToClients(new ItemStackSyncS2CPacket(pos, entity.itemHandler));
        if(hasRecipe(entity)) {
            entity.progress++;
            setChanged(level, pos, state);
            entity.maxProgress = breedingTime(entity);

            if(entity.progress >= entity.maxProgress) {
                craftItem(entity);
            }
        } else {
            entity.resetProgress();
            setChanged(level, pos, state);
        }
    }
    private static <T> boolean hasRecipe(BreedingStructureEntity entity) {
        if(entity.active) {
            Level level = entity.level;
            SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
            for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
                inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
            }

            Optional<BreedingStructureRecipe> recipe = level.getRecipeManager().getRecipeFor(BreedingStructureRecipe.Type.INSTANCE, inventory, level);
            if (recipe.isEmpty()) {
                for (int i = 0; i < 2; i++) {
                    if (inventory.getItem(i).getItem() != ModItems.MONSTER_SIGIL.get()) {
                        return false;
                    }
                    CompoundTag nbt = inventory.getItem(i).getTag();
                    if (nbt == null || !nbt.contains("entity")) {
                        return false;
                    }
                }
            }
            return canInsertItem(inventory);
        } else { return false; }
    }

    public static int breedingTime(BreedingStructureEntity entity) {
        int y = 0;
        for(int i = 0; i < 2; i++){
            ItemStack ing;
            try {
                ing = entity.itemHandler.getStackInSlot(i);
            } catch(Exception e) {
                return 0;
            }
            String s = "egg_".concat(ing.getTag().getString("entity").toLowerCase().replace(" ", ""));
            for (RegistryObject<Block> block : ModBlocks.BLOCKS.getEntries()) {
                if(block.get().asItem().toString().equals(s)) {
                    ing = new ItemStack(block.get().asItem(), 1);
                    if(ing.is(earthTag)) { y += 20*15; }
                    if(ing.is(coldTag)) { y += 20*15; }
                    if(ing.is(waterTag)) { y += 20*15; }
                    if(ing.is(plantTag)) { y += 20*15; }
                    if(ing.is(airTag)) { y += 20*15; }
                    if(ing.is(plasmaTag)) { y += 20*45; }
                    if(ing.is(shadowTag)) { y += 20*45; }
                    if(ing.is(mechTag)) { y += 20*45; }
                    if(ing.is(crystalTag)) { y += 20*45; }
                    if(ing.is(poisonTag)) { y += 20*45; }
                    if(ing.is(legendaryTag)) { y *= 20*45; }
                    if(ing.is(mythicalTag)) { y *= 20*45; }
                    if(ing.is(seasonalTag)) { y += 20*60; }
                    if(ing.is(fireTag)) { y += 20*30; }
                    if(ing.is(psychicTag)) { y += 20*45; }
                    if(ing.is(faerieTag)) { y += 20*45; }
                    if(ing.is(lightTag)) { y += 20*45; }
                    if(ing.is(boneTag)) { y += 20*45; }
                }
            }
        }
        return y;
    }

    private static boolean canInsertItem(SimpleContainer inventory) {
        return inventory.getItem(2).isEmpty();
    }

    private static void craftItem(BreedingStructureEntity pEntity) {
        Level level = pEntity.level;
        SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());
        for (int i = 0; i < pEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
        }
        if(hasRecipe(pEntity)) {
            Optional<BreedingStructureRecipe> recipe = level.getRecipeManager()
                    .getRecipeFor(BreedingStructureRecipe.Type.INSTANCE, inventory, level);
            if(recipe.isPresent()) {
                pEntity.itemHandler.setStackInSlot(2, new ItemStack(recipe.get().getResultEgg(), 1));
            } else {
                List<String> list = new ArrayList();
                for(int i = 0; i < 2; i++) {
                    if(inventory.getItem(i).getTag() != null && inventory.getItem(i).getTag().contains("entity")) {
                        list.add(inventory.getItem(i).getTag().getString("entity"));
                    }
                }
                pEntity.itemHandler.setStackInSlot(2, new ItemStack(BreedingStructureRecipe.getDefResult(list), 1));
            }
            pEntity.resetProgress();
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }

    public List<ItemStack> getRenderStacks() {
        List<ItemStack> list = new ArrayList();
        for (int i = 0; i < 2; i++) {
            if (itemHandler.getStackInSlot(i).getTag() != null && itemHandler.getStackInSlot(i).getTag().contains("entity")) {
                String s = "egg_".concat(itemHandler.getStackInSlot(i).getTag().getString("entity").toLowerCase().replace(" ", ""));
                for (RegistryObject<Block> block : ModBlocks.BLOCKS.getEntries()) {
                    if (block.get().asItem().toString().equals(s)) {
                        list.add(new ItemStack(block.get().asItem(), 1));
                    }
                }
            } else { list.add(ItemStack.EMPTY); }
        } if (itemHandler.getStackInSlot(2).isEmpty()) {
            list.add(ItemStack.EMPTY);
        } else if (itemHandler.getStackInSlot(2).is(monsterTag)) {
            list.add(itemHandler.getStackInSlot(2));
        } else {
            list.add(ItemStack.EMPTY);
        }
        return list;
    }

    public void setHandler(ItemStackHandler itemStackHandler) {
        for(int i = 0; i < itemStackHandler.getSlots(); i++) {
            itemHandler.setStackInSlot(i, itemStackHandler.getStackInSlot(i));
        }
    }

    public boolean canTakeItemThroughFace(int pIndex, ItemStack pStack, Direction pDirection) {
        return pDirection == Direction.DOWN && pIndex == 2;
    }
}
