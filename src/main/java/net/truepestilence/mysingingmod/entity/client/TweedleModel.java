package net.truepestilence.mysingingmod.entity.client;

import net.minecraft.resources.ResourceLocation;
import net.truepestilence.mysingingmod.MySingingMod;
import net.truepestilence.mysingingmod.entity.custom.MammottEntity;
import net.truepestilence.mysingingmod.entity.custom.TweedleEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TweedleModel extends AnimatedGeoModel<TweedleEntity> {
    @Override
    public ResourceLocation getModelResource(TweedleEntity object) {
        return new ResourceLocation(MySingingMod.MOD_ID, "geo/tweedle.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TweedleEntity object) {
        return new ResourceLocation(MySingingMod.MOD_ID, "textures/entity/tweedle.png");
    }

    @Override
    public ResourceLocation getAnimationResource(TweedleEntity animatable) {
        return new ResourceLocation(MySingingMod.MOD_ID, "animations/tweedle.animation.json");
    }
}
