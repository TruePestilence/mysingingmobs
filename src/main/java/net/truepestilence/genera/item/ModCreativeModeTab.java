package net.truepestilence.genera.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {
    public static final CreativeModeTab GENERA_TAB = new CreativeModeTab("generatab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.TEMPERA_STONE.get());
        }
    };
}
