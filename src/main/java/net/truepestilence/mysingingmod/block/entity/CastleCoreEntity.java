package net.truepestilence.mysingingmod.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.ForgeRegistries;
import net.truepestilence.mysingingmod.MySingingMod;
import net.truepestilence.mysingingmod.block.custom.CastleCore;
import net.truepestilence.mysingingmod.screen.CastleCoreMenu;
import net.truepestilence.mysingingmod.screen.NurseryMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CastleCoreEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    protected final ContainerData data;
    private int beds = 5;
    private List<Entity> monsters = new ArrayList<>();
    private final Direction facing;

    public CastleCoreEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CASTLE_CORE.get(), pos, state);
        facing = state.getValue(CastleCore.FACING);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> CastleCoreEntity.this.beds;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> CastleCoreEntity.this.beds = value;
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
        return Component.literal("Castle Core");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new CastleCoreMenu(id, inventory, this, this.data);
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
        nbt.putInt("castle_core.beds", this.beds);

        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        beds = nbt.getInt("castle_core.beds");
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, CastleCoreEntity entity) {
        if(level.isClientSide()) {
            return;
        }
        int chunkPosX = pos.getX() >> 4;
        int chunkPosZ = pos.getZ() >> 4;
        for (int x = chunkPosX - 8; x < chunkPosX + 8; x++) {
            for (int z = chunkPosZ - 8; z < chunkPosZ + 8; z++) {
                LevelChunk currentChunk = level.getChunk(chunkPosX, chunkPosZ);
                BlockPos chunk = currentChunk.getPos().getWorldPosition();
                List<Entity> ent = level.getEntities(null, new AABB(chunk.getX(), pos.getY() + 64, chunk.getZ(), chunk.getX() + 16, pos.getY() - 64, chunk.getZ() + 16));
                EntityType type;
                for(String i : getMonsters()) {
                    try { type = ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(MySingingMod.MOD_ID, i)); }
                    catch (Exception e) {
                       type = null;
                    } for(Entity e : ent) {
                        if(e.getType() == type) {
                            if(!entity.monsters.contains(e)) {
                                entity.monsters.add(e);
                            }
                        }
                    }
                }
            }
        }
    }

    public static List<String> getMonsters() {
        List<String> list = new ArrayList();
        list.add("noggin");
        list.add("noggin_rare");
        list.add("noggin_epic");
        list.add("mammott");
        list.add("mammott_rare");
        list.add("mammott_epic");
        list.add("toe_jammer");
        list.add("toe_jammer_rare");
        list.add("toe_jammer_epic");
        list.add("potbelly");
        list.add("potbelly_rare");
        list.add("potbelly_epic");
        list.add("tweedle");
        list.add("tweedle_rare");
        list.add("tweedle_epic");
        list.add("dandidoo");
        list.add("dandidoo_rare");
        list.add("dandidoo_epic");
        list.add("cybop");
        list.add("cybop_rare");
        list.add("cybop_epic");
        list.add("quibble");
        list.add("quibble_rare");
        list.add("quibble_epic");
        list.add("pango");
        list.add("pango_rare");
        list.add("pango_epic");
        list.add("shrubb");
        list.add("shrubb_rare");
        list.add("shrubb_epic");
        list.add("oaktopus");
        list.add("oaktopus_rare");
        list.add("oaktopus_epic");
        list.add("furcorn");
        list.add("furcorn_rare");
        list.add("furcorn_epic");
        list.add("fwog");
        list.add("fwog_rare");
        list.add("fwog_epic");
        list.add("drumpler");
        list.add("drumpler_rare");
        list.add("drumpler_epic");
        list.add("maw");
        list.add("maw_rare");
        list.add("maw_epic");
        list.add("reedling");
        list.add("reedling_rare");
        list.add("reedling_epic");
        list.add("spunge");
        list.add("spunge_rare");
        list.add("spunge_epic");
        list.add("thumpies");
        list.add("thumpies_rare");
        list.add("thumpies_epic");
        list.add("scups");
        list.add("scups_rare");
        list.add("scups_epic");
        list.add("pompom");
        list.add("pompom_rare");
        list.add("pompom_epic");
        list.add("congle");
        list.add("congle_rare");
        list.add("congle_epic");
        list.add("pummel");
        list.add("pummel_rare");
        list.add("pummel_epic");
        list.add("clamble");
        list.add("clamble_rare");
        list.add("clamble_epic");
        list.add("bowgart");
        list.add("bowgart_rare");
        list.add("bowgart_epic");
        list.add("trox");
        list.add("trox_rare");
        list.add("trox_epic");
        list.add("shellbeat");
        list.add("shellbeat_rare");
        list.add("shellbeat_epic");
        list.add("quarrister");
        list.add("quarrister_rare");
        list.add("quarrister_epic");
        list.add("entbrat");
        list.add("entbrat_rare");
        list.add("entbrat_epic");
        list.add("deedge");
        list.add("deedge_rare");
        list.add("deedge_epic");
        list.add("riff");
        list.add("riff_rare");
        list.add("riff_epic");
        list.add("kayna");
        list.add("kayna_rare");
        list.add("kayna_epic");
        list.add("stogg");
        list.add("stogg_rare");
        list.add("stogg_epic");
        list.add("phangler");
        list.add("phangler_rare");
        list.add("phangler_epic");
        list.add("boskus");
        list.add("boskus_rare");
        list.add("boskus_epic");
        list.add("flowah");
        list.add("flowah_rare");
        list.add("flowah_epic");
        list.add("glowl");
        list.add("glowl_rare");
        list.add("glowl_epic");
        list.add("barrb");
        list.add("barrb_rare");
        list.add("barrb_epic");
        list.add("floogull");
        list.add("floogull_rare");
        list.add("floogull_epic");
        list.add("whaddle");
        list.add("whaddle_rare");
        list.add("whaddle_epic");
        list.add("wynq");
        list.add("wynq_rare");
        list.add("wynq_epic");
        list.add("woolabee");
        list.add("woolabee_rare");
        list.add("woolabee_epic");
        list.add("repatillo");
        list.add("repatillo_rare");
        list.add("repatillo_epic");
        list.add("rootitoot");
        list.add("rootitoot_rare");
        list.add("rootitoot_epic");
        list.add("ziggurab");
        list.add("ziggurab_rare");
        list.add("ziggurab_epic");
        list.add("thrumble");
        list.add("thrumble_rare");
        list.add("thrumble_epic");
        list.add("sooza");
        list.add("sooza_rare");
        list.add("sooza_epic");
        list.add("tring");
        list.add("tring_rare");
        list.add("tring_epic");
        list.add("sneyser");
        list.add("sneyser_rare");
        list.add("yelmut");
        list.add("yelmut_rare");
        list.add("flumox");
        list.add("bisonorous");
        list.add("incisaur");
        list.add("edamimi");
        list.add("tiawa");
        list.add("pongping");
        list.add("krillby");
        list.add("candelavra");
        list.add("drummidary");
        list.add("theremind");
        list.add("theremind_rare");
        list.add("floot_fly");
        list.add("floot_fly_rare");
        list.add("clackula");
        list.add("fluoress");
        list.add("bonkers");
        list.add("gob");
        list.add("gob_rare");
        list.add("peckidna");
        list.add("hippityhop");
        list.add("poppette");
        list.add("denchuhs");
        list.add("bulbo");
        list.add("squot");
        list.add("yuggler");
        list.add("yuggler_rare");
        list.add("hawlo");
        list.add("hawlo_rare");
        list.add("pluckbill");
        list.add("wimmzies");
        list.add("wimmzies_rare");
        list.add("xyster");
        list.add("cahoot");
        list.add("knucklehead");
        list.add("roarick");
        list.add("dejajin");
        list.add("osstax");
        list.add("tapricorn");
        list.add("spytrap");
        list.add("rooba");
        list.add("tootoo");
        list.add("tootoo_rare");
        list.add("withur");
        list.add("cantorell");
        list.add("uuduk");
        list.add("bridgit");
        list.add("bridgit_rare");
        list.add("periscorp");
        list.add("banjaw");
        list.add("banjaw_rare");
        list.add("fiddlement");
        list.add("clavi_gnat");
        list.add("gday");
        list.add("larvaluss");
        list.add("mushaboom");
        list.add("frondley");
        list.add("gloptic");
        list.add("pladdie");
        list.add("plinkajou");
        list.add("blowt");
        list.add("enchantling");
        list.add("ghazt");
        list.add("ghazt_rare");
        list.add("ghazt_epic");
        list.add("grumpyre");
        list.add("grumpyre_rare");
        list.add("grumpyre_epic");
        list.add("reebro");
        list.add("reebro_rare");
        list.add("reebro_epic");
        list.add("jeeode");
        list.add("jeeode_rare");
        list.add("jeeode_epic");
        list.add("humbug");
        list.add("humbug_rare");
        list.add("whisp");
        list.add("whisp_rare");
        list.add("nebulob");
        list.add("nebulob_rare");
        list.add("sox");
        list.add("sox_rare");
        list.add("jellbilly");
        list.add("jellbilly_rare");
        list.add("arackulele");
        list.add("arackulele_rare");
        list.add("boodoo");
        list.add("boodoo_rare");
        list.add("kazilleon");
        list.add("kazilleon_rare");
        list.add("bellowfish");
        list.add("bellowfish_rare");
        list.add("dragong");
        list.add("dragong_rare");
        list.add("fung_pray");
        list.add("fung_pray_rare");
        list.add("punkleton");
        list.add("punkleton_rare");
        list.add("punkleton_epic");
        list.add("yool");
        list.add("yool_rare");
        list.add("yool_epic");
        list.add("schmoochle");
        list.add("schmoochle_rare");
        list.add("schmoochle_epic");
        list.add("blabbit");
        list.add("blabbit_rare");
        list.add("blabbit_epic");
        list.add("hoola");
        list.add("hoola_rare");
        list.add("hoola_epic");
        list.add("gobbleygourd");
        list.add("gobbleygourd_rare");
        list.add("gobbleygourd_epic");
        list.add("clavavera");
        list.add("viveine");
        list.add("jam_boree");
        list.add("jam_boree_rare");
        list.add("carillong");
        list.add("whizbang");
        list.add("monculus");
        list.add("ffidyll");
        list.add("booqwurm");
        list.add("spurrit");
        list.add("shugabush");
        list.add("shugabeats");
        list.add("shugabass");
        list.add("shugarock");
        list.add("shugajo");
        list.add("shugabuzz");
        list.add("shugavox");
        list.add("shugitar");
        list.add("tawkerr");
        list.add("parlsona");
        list.add("maggpi");
        list.add("stoowarb");
        list.add("gjoob");
        list.add("gjoob_rare");
        list.add("strombonin");
        list.add("strombonin_rare");
        list.add("yawstrich");
        list.add("cherubble");
        list.add("cataliszt");
        list.add("sporerow");
        list.add("cranchee");
        list.add("bleatnik");
        list.add("shlep");
        list.add("wubbox");
        list.add("wubbox_rare");
        list.add("wubbox_epic");
        list.add("brump");
        list.add("zynth");
        list.add("poewk");
        list.add("thwok");
        list.add("dwumrohl");
        list.add("zuuker");
        list.add("screemu");
        list.add("tympa");
        list.add("dermit");
        list.add("gheegur");
        list.add("whajje");
        list.add("creepuscule");
        list.add("blipsqueak");
        list.add("scargo");
        list.add("astropod");
        list.add("pixolotl");
        list.add("bonapetite");
        list.add("maulch");
        list.add("fleechwurm");
        list.add("torrt");
        list.add("blasoom");
        list.add("hornacle");
        list.add("attmoz");
        list.add("glaishur");
        list.add("syncopite");
        list.add("scaratar");
        list.add("loodvigg");
        list.add("plixie");
        list.add("furnoss");
        list.add("vhamp");
        list.add("galvana");
        list.add("do");
        list.add("re");
        list.add("mi");
        list.add("fa");
        list.add("sol");
        list.add("la");
        list.add("ti");
        return list;
    }
}
