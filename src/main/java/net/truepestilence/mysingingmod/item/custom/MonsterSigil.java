package net.truepestilence.mysingingmod.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.truepestilence.mysingingmod.block.ModBlocks;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MonsterSigil extends Item {
    public MonsterSigil(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean isFoil(ItemStack pStack) {
        CompoundTag nbt = pStack.getTag();
        if(nbt != null && nbt.contains("entity")) {
            return true;
        } return false;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack stack = pPlayer.getItemInHand(InteractionHand.MAIN_HAND);
        CompoundTag nbt = stack.getTag();
        if(nbt != null && nbt.contains("entity") && pUsedHand == InteractionHand.MAIN_HAND && pPlayer.isShiftKeyDown()) {
            nbt.remove("entity");
            stack.setTag(nbt);
            return InteractionResultHolder.success(stack);
        } return InteractionResultHolder.pass(stack);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        CompoundTag nbt = pStack.getTag();
        if(nbt != null && nbt.contains("entity")) {
            pTooltipComponents.add(Component.literal(nbt.getString("entity")).withStyle(ChatFormatting.GRAY));
        } else {
            pTooltipComponents.add(Component.literal("Empty").withStyle(ChatFormatting.GRAY));
        }
    }
}
