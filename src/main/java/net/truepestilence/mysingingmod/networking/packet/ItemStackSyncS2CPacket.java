package net.truepestilence.mysingingmod.networking.packet;


import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkEvent;
import net.truepestilence.mysingingmod.block.entity.BreedingStructureEntity;
import net.truepestilence.mysingingmod.entity.ModEntityTypes;
import net.truepestilence.mysingingmod.entity.custom.NogginEntity;
import net.truepestilence.mysingingmod.entity.custom.ToeJammerEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

public class ItemStackSyncS2CPacket {
    private BlockPos pos;
    private ItemStackHandler itemHandler;

    public ItemStackSyncS2CPacket(BlockPos pos, ItemStackHandler itemHandler) {
        this.pos = pos;
        this.itemHandler = itemHandler;
    }

    public ItemStackSyncS2CPacket(FriendlyByteBuf buf){
        this.pos = buf.readBlockPos();

        List<ItemStack> collection = buf.readCollection(ArrayList::new, FriendlyByteBuf::readItem);
        itemHandler = new ItemStackHandler(collection.size());
        for(int i = 0; i < collection.size(); i++) {
            itemHandler.insertItem(i, collection.get(i), false);
        }
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeBlockPos(pos);

        Collection<ItemStack> list = new ArrayList();
        for(int i = 0; i < itemHandler.getSlots(); i++) {
            list.add(itemHandler.getStackInSlot(i));
        }
        buf.writeCollection(list, FriendlyByteBuf::writeItem);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            if (Minecraft.getInstance().level.getBlockEntity(pos) instanceof BreedingStructureEntity entity) {
                entity.setHandler(this.itemHandler);
            }
        });
        context.setPacketHandled(true);
        return true;
    }
}
