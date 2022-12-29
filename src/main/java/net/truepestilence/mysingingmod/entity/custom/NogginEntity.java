package net.truepestilence.mysingingmod.entity.custom;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class NogginEntity extends Mob implements IAnimatable {
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    public NogginEntity(EntityType<? extends Mob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
    }

    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0f)
                .add(Attributes.MAX_HEALTH, 10.00)
                .build();
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.noggin.idle", ILoopType.EDefaultLoopTypes.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller",
                0, this::predicate));
    }

    public boolean removeWhenFarAway(double pDistanceToClosestPlayer) {
        return false;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    protected SoundEvent getAmbientSound() { return null; }

    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return null;
    }

    @Nullable
    protected SoundEvent getDeathSound() {
        return null;
    }

    public static List<String> getNames() {
        List list = new ArrayList();
        list.add("Argus");
        list.add("Boreas");
        list.add("Cephas");
        list.add("Chuck");
        list.add("Chunk");
        list.add("Eben");
        list.add("Granok");
        list.add("Handy");
        list.add("Headrum");
        list.add("Narms");
        list.add("Numbskull");
        list.add("Roark");
        list.add("Rockmaninoff");
        list.add("Rubble");
        list.add("Shlapp");
        list.add("Thwak");
        list.add("Tongot");
        list.add("Wilma");
        return list;
    }

    /**
     * Get number of ticks, at least during which the living entity will be silent.
     */
    public int getAmbientSoundInterval() {
        return 120;
    }
}
