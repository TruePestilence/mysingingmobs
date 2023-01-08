package net.truepestilence.mysingingmod.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.truepestilence.mysingingmod.MySingingMod;
import net.truepestilence.mysingingmod.block.ModBlocks;
import net.truepestilence.mysingingmod.item.ModItems;
import org.jetbrains.annotations.Nullable;

import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BreedingStructureRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final Hashtable<ItemStack, Double> outputs;
    private final NonNullList<String> recipeInputs;

    public BreedingStructureRecipe(ResourceLocation id, Hashtable<ItemStack, Double> outputs,
                                    NonNullList<String> recipeInputs) {
        this.id = id;
        this.outputs = outputs;
        this.recipeInputs = recipeInputs;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if(pLevel.isClientSide()) {
            return false;
        }

        int confirm = 0;
        String ent1 = null;
        for(int i = 0; i < 2; i++) {
            ItemStack stack = pContainer.getItem(i);
            if(stack.getItem() == ModItems.MONSTER_SIGIL.get()) {
                CompoundTag nbt = stack.getTag();
                if(nbt != null && nbt.contains("entity")) {
                    if(recipeInputs.contains(nbt.getString("entity")) && !nbt.getString("entity").equals(ent1)) {
                        ent1 = nbt.getString("entity");
                        confirm++;
                    }
                }
            }
        }
        return confirm >= 2;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.create();
    }

    public NonNullList<String> getMonsters() {
        return recipeInputs;
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    public Hashtable<ItemStack, Double> getOutputs() { return outputs; }

    public Item getResultEgg() {
        Hashtable<ItemStack, Double> outputs = getOutputs();
        double rand = ThreadLocalRandom.current().nextDouble(0d,100d);
        double weightMin = 0d;
        for(ItemStack s : outputs.keySet()){
            if(weightMin <= rand && rand <= outputs.get(s)) {
                Item item = null;
                try {
                    item = s.getItem();
                } catch (Exception e) { }
                if(item != null) {
                    return item;
                }
                weightMin += outputs.get(s);
            }
        }
        return ModBlocks.EGG_NOGGIN.get().asItem();
    }

    public static Item getDefResult(List<String> pMonsters) {
        Hashtable<Item, Long> results = new Hashtable();
        Boolean got = false;
        Item larger = null;
        Item smaller = null;
        for(String s : pMonsters) {
            s = "egg_".concat(s.toLowerCase().replace(" ", ""));
            for (RegistryObject<Block> block : ModBlocks.BLOCKS.getEntries()) {
                if(block.get().asItem().toString().equals(s)) {
                    long weight = new ItemStack(block.get().asItem(), 1).getTags().count();
                    results.put(block.get().asItem(), weight);
                    if(larger == null) {
                        larger = block.get().asItem();
                    } else {
                        if(results.get(block.get().asItem()) > results.get(larger)) {
                            smaller = larger;
                            larger = block.get().asItem();
                        } else { smaller = block.get().asItem(); }
                    }
                    got = true;
                }
            } if(!got) {
                results.put(ModBlocks.EGG_NOGGIN.get().asItem(), Integer.toUnsignedLong(1));
            }
        } if(results.get(larger).equals(results.get(smaller))) {
            if(ThreadLocalRandom.current().nextBoolean()) { return larger; }
            else { return smaller; }
        } switch(results.get(larger).toString().concat(results.get(smaller).toString())) {
            case "21": return ThreadLocalRandom.current().nextFloat() > 0.25 ? smaller : larger;
            case "31":
            case "43":
                return ThreadLocalRandom.current().nextFloat() > 0.10 ? smaller : larger;
            case "32": return ThreadLocalRandom.current().nextFloat() > 0.20 ? smaller : larger;
            case "41":
            case "51":
            case "52":
                return smaller;
            case "42":
            case "53":
                return ThreadLocalRandom.current().nextFloat() > 0.05 ? smaller : larger;
            case "54": return ThreadLocalRandom.current().nextFloat() > 0.01 ? smaller : larger;
        } return ModBlocks.EGG_NOGGIN.get().asItem();
    }

    @Override
    public ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<BreedingStructureRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "breeding";
    }


    public static class Serializer implements RecipeSerializer<BreedingStructureRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(MySingingMod.MOD_ID, "breeding");

        @Override
        public BreedingStructureRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {

            JsonArray monsters = GsonHelper.getAsJsonArray(pSerializedRecipe, "monsters");
            NonNullList<String> inputs = NonNullList.withSize(2, "");
            JsonArray special = GsonHelper.getAsJsonArray(pSerializedRecipe, "special");
            Hashtable<ItemStack, Double> outputs = new Hashtable();

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, monsters.get(i).getAsString());
            }

            for (int i = 0; i < special.size(); i++) {
                JsonArray j = special.get(i).getAsJsonArray();
                outputs.put(new ItemStack(GsonHelper.getAsItem(j.get(0).getAsJsonObject(), "item")), j.get(1).getAsDouble());
            }

            return new BreedingStructureRecipe(pRecipeId, outputs, inputs);
        }

        @Override
        public @Nullable BreedingStructureRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            NonNullList<String> inputs = NonNullList.withSize(buf.readInt(), "");
            Hashtable<ItemStack, Double> outputs = new Hashtable();
            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, buf.readUtf());
            }
            for (int i = 0; i < buf.readInt(); i++) {
                outputs.put(buf.readItem(), buf.readDouble());
            }
            return new BreedingStructureRecipe(id, outputs, inputs);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, BreedingStructureRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());

            for (String s : recipe.getMonsters()) {
                buf.writeUtf(s);
            }

            buf.writeInt(recipe.getOutputs().size());
            for (ItemStack s : recipe.getOutputs().keySet()) {
                buf.writeItem(s);
                buf.writeDouble(recipe.getOutputs().get(s));
            }
        }
    }
}