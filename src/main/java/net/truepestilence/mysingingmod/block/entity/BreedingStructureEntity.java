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
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.truepestilence.mysingingmod.MySingingMod;
import net.truepestilence.mysingingmod.recipe.BreedingStructureRecipe;
import net.truepestilence.mysingingmod.screen.BreedingStructureMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

public class BreedingStructureEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };
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
    private int maxProgress = 69;

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
        if(cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
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

        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        progress = nbt.getInt("breeding_structure.progress");
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
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<BreedingStructureRecipe> recipe = level.getRecipeManager().getRecipeFor(BreedingStructureRecipe.Type.INSTANCE, inventory, level);
        return recipe.isPresent() && canInsertAmount(inventory) &&
                canInsertItem(inventory, recipe.get().getResultItem());
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
            if(ing.is(earthTag)) { y += 23*15; }
            if(ing.is(coldTag)) { y += 23*15; }
            if(ing.is(waterTag)) { y += 23*15; }
            if(ing.is(plantTag)) { y += 23*15; }
            if(ing.is(airTag)) { y += 23*15; }
            if(ing.is(plasmaTag)) { y += 23*45; }
            if(ing.is(shadowTag)) { y += 23*45; }
            if(ing.is(mechTag)) { y += 23*45; }
            if(ing.is(crystalTag)) { y += 23*45; }
            if(ing.is(poisonTag)) { y += 23*45; }
            if(ing.is(legendaryTag)) { y *= 23*45; }
            if(ing.is(mythicalTag)) { y *= 23*45; }
            if(ing.is(seasonalTag)) { y += 23*60; }
            if(ing.is(fireTag)) { y += 23*30; }
            if(ing.is(psychicTag)) { y += 23*45; }
            if(ing.is(faerieTag)) { y += 23*45; }
            if(ing.is(lightTag)) { y += 23*45; }
            if(ing.is(boneTag)) { y += 23*45; }
        }
        return y;
    }

    private static boolean canInsertItem(SimpleContainer inventory, ItemStack stack) {
        return inventory.getItem(2).getItem() == stack.getItem() || inventory.getItem(2).isEmpty();
    }

    private static boolean canInsertAmount(SimpleContainer inventory) {
        return inventory.getItem(2).getMaxStackSize() > inventory.getItem(2).getCount();
    }

    private static void craftItem(BreedingStructureEntity pEntity) {
        Level level = pEntity.level;
        SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());
        for (int i = 0; i < pEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
        }

        Optional<BreedingStructureRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(BreedingStructureRecipe.Type.INSTANCE, inventory, level);

        if(hasRecipe(pEntity)) {
            pEntity.itemHandler.extractItem(0, 1, false);
            pEntity.itemHandler.extractItem(1, 1, false);
            pEntity.itemHandler.setStackInSlot(2, new ItemStack(recipe.get().getResultItem().getItem(),
                    pEntity.itemHandler.getStackInSlot(2).getCount() + 1));

            pEntity.resetProgress();
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }
}
