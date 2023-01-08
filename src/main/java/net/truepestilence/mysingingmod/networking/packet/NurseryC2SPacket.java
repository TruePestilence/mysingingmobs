package net.truepestilence.mysingingmod.networking.packet;


import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import net.truepestilence.mysingingmod.entity.ModEntityTypes;
import net.truepestilence.mysingingmod.entity.custom.*;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

public class NurseryC2SPacket {
    private BlockPos spawn;
    private ItemStack stack;

    public NurseryC2SPacket(BlockPos spawn, ItemStack stack) {
        this.spawn = spawn;
        this.stack = stack;
    }

    public NurseryC2SPacket(FriendlyByteBuf buf){
        this.spawn = buf.readBlockPos();
        this.stack = buf.readItem();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeBlockPos(spawn);
        buf.writeItem(stack);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerLevel level = context.getSender().getLevel();
            EntityType type;
            List<String> list;
            switch (stack.getItem().toString()) {
                case "egg_mammott" -> {
                    type = ModEntityTypes.MAMMOTT.get();
                    list = MammottEntity.getNames();
                }
                case "egg_potbelly" -> {
                    type = ModEntityTypes.POTBELLY.get();
                    list = PotbellyEntity.getNames();
                }
                case "egg_toejammer" -> {
                    type = ModEntityTypes.TOE_JAMMER.get();
                    list = ToeJammerEntity.getNames();
                }
                case "egg_tweedle" -> {
                    type = ModEntityTypes.TWEEDLE.get();
                    list = TweedleEntity.getNames();
                }
                default -> {
                    type = ModEntityTypes.NOGGIN.get();
                    list = NogginEntity.getNames();
                }
            }
            String name = list.get(ThreadLocalRandom.current().nextInt(0, list.size()));
            type.spawn(level, null, Component.literal(name), null, spawn, MobSpawnType.MOB_SUMMONED, true, false);
        });
        context.setPacketHandled(true);
        return true;
    }
}
