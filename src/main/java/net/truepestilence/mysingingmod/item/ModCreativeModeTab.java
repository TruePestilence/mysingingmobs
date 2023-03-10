package net.truepestilence.mysingingmod.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.truepestilence.mysingingmod.block.ModBlocks;

public class ModCreativeModeTab {
    public static final CreativeModeTab MSM_TAB = new CreativeModeTab("msmtab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.VEGIDIA.get());
        }
    };
    public static final CreativeModeTab EGG_TAB = new CreativeModeTab("eggtab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModBlocks.EGG_NOGGIN.get());
        }
    };
}
