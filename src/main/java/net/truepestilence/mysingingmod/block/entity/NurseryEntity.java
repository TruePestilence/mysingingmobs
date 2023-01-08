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
import net.truepestilence.mysingingmod.item.ModItems;
import net.truepestilence.mysingingmod.networking.ModNetworking;
import net.truepestilence.mysingingmod.networking.packet.ItemStackSyncS2CPacket;
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

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch(slot) {
                case 0 -> stack.is(monsterTag);
                default -> super.isItemValid(slot, stack);
            };
        }
    };
    public static final TagKey<Item> monsterTag = ItemTags.create(new ResourceLocation(MySingingMod.MOD_ID, "all_monsters"));
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 60;
    private final Direction facing;

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
        ModNetworking.sendToClients(new ItemStackSyncS2CPacket(pos, entity.itemHandler));
        if(hasRecipe(entity)) {
            entity.progress++;
            setChanged(level, pos, state);
            entity.maxProgress = getIncTime(entity.itemHandler.getStackInSlot(0));
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
        if(inventory.getItem(0).is(monsterTag)) { return true; }
        return false;
    }

    private static void craftItem(NurseryEntity pEntity, BlockPos pos) {
        SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());
        for (int i = 0; i < pEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
        }

        BlockPos spawn = pos.relative(pEntity.facing, 1);
        ModNetworking.sendToServer(new NurseryC2SPacket(spawn, inventory.getItem(0)));
        pEntity.itemHandler.extractItem(0, 1, false);
        pEntity.resetProgress();
    }

    private static int getIncTime(ItemStack pStack) {
        return switch (pStack.getItem().toString()) {
            case "egg_noggin", "egg_mammott", "egg_toejammer", "egg_potbelly", "egg_tweedle" -> 200;
            default -> 60;
        };
    }

    private void resetProgress() {
        this.progress = 0;
    }

    public ItemStack getRenderStack() {
        return itemHandler.getStackInSlot(0);
    }

    public void setHandler(ItemStackHandler itemStackHandler) {
        for(int i = 0; i < itemStackHandler.getSlots(); i++) {
            itemHandler.setStackInSlot(i, itemStackHandler.getStackInSlot(i));
        }
    }
}
