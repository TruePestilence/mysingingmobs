package net.truepestilence.mysingingmod.entity.client;

import net.minecraft.resources.ResourceLocation;
import net.truepestilence.mysingingmod.MySingingMod;
import net.truepestilence.mysingingmod.entity.custom.NogginEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class NogginModel extends AnimatedGeoModel<NogginEntity> {
    @Override
    public ResourceLocation getModelResource(NogginEntity object) {
        return new ResourceLocation(MySingingMod.MOD_ID, "geo/noggin.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(NogginEntity object) {
        return new ResourceLocation(MySingingMod.MOD_ID, "textures/entity/noggin.png");
    }

    @Override
    public ResourceLocation getAnimationResource(NogginEntity animatable) {
        return new ResourceLocation(MySingingMod.MOD_ID, "animations/noggin.animation.json");
    }
}
