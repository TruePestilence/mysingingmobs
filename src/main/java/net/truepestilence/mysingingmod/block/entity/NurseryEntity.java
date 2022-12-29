package net.truepestilence.mysingingmod.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
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
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.ForgeRegistries;
import net.truepestilence.mysingingmod.MySingingMod;
import net.truepestilence.mysingingmod.block.custom.MonsterEgg;
import net.truepestilence.mysingingmod.block.custom.Nursery;
import net.truepestilence.mysingingmod.entity.ModEntityTypes;
import net.truepestilence.mysingingmod.entity.custom.NogginEntity;
import net.truepestilence.mysingingmod.networking.ModNetworking;
import net.truepestilence.mysingingmod.networking.packet.NurseryC2SPacket;
import net.truepestilence.mysingingmod.screen.NurseryMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class NurseryEntity extends BlockEntity implements MenuProvider {
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
    public static List<TagKey<Item>> getTags() {
        List<TagKey<Item>> list = new ArrayList();
        list.add(earthTag);
        list.add(coldTag);
        list.add(waterTag);
        list.add(plantTag);
        list.add(airTag);
        list.add(plasmaTag);
        list.add(shadowTag);
        list.add(mechTag);
        list.add(crystalTag);
        list.add(poisonTag);
        list.add(psychicTag);
        list.add(faerieTag);
        list.add(boneTag);
        list.add(lightTag);
        list.add(legendaryTag);
        list.add(seasonalTag);
        list.add(mythicalTag);
        list.add(fireTag);
        return list;
    }
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 60;
    private Direction facing;

    public NurseryEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.NURSERY.get(), pos, state);
        facing = state.getValue(Nursery.FACING);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> NurseryEntity.this.progress;
                    case 1 -> NurseryEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> NurseryEntity.this.progress = value;
                    case 1 -> NurseryEntity.this.maxProgress = value;
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
        return Component.literal("Nursery");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new NurseryMenu(id, inventory, this, this.data);
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
        nbt.putInt("nursery.progress", this.progress);

        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        progress = nbt.getInt("nursery.progress");
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, NurseryEntity entity) {
        if(level.isClientSide()) {
            return;
        }
        if(hasRecipe(entity)) {
            entity.progress++;
            setChanged(level, pos, state);
            if(entity.progress >= entity.maxProgress) {
                craftItem(entity, pos);
            }
        } else {
            entity.resetProgress();
            setChanged(level, pos, state);
        }
    }
    private static <T> boolean hasRecipe(NurseryEntity entity) {
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }
        List<TagKey<Item>> list = getTags();
        for (int i = 0; i < list.size(); i++) {
            if(inventory.getItem(0).is(list.get(i))) { return true; }
        }
        return false;
    }

    private static void craftItem(NurseryEntity pEntity, BlockPos pos) {
        SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());
        for (int i = 0; i < pEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
        }

        BlockPos spawn = pos.relative(pEntity.facing, 1);
        pEntity.itemHandler.extractItem(0, 1, false);
        ModNetworking.sendToServer(new NurseryC2SPacket(spawn, inventory.getItem(0)));
        pEntity.resetProgress();
    }

    private void resetProgress() {
        this.progress = 0;
    }
}
