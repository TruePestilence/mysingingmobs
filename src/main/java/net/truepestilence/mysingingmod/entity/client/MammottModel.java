package net.truepestilence.mysingingmod.entity.client;

import net.minecraft.resources.ResourceLocation;
import net.truepestilence.mysingingmod.MySingingMod;
import net.truepestilence.mysingingmod.entity.custom.MammottEntity;
import net.truepestilence.mysingingmod.entity.custom.PotbellyEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MammottModel extends AnimatedGeoModel<MammottEntity> {
    @Override
    public ResourceLocation getModelResource(MammottEntity object) {
        return new ResourceLocation(MySingingMod.MOD_ID, "geo/mammott.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MammottEntity object) {
        return new ResourceLocation(MySingingMod.MOD_ID, "textures/entity/mammott.png");
    }

    @Override
    public ResourceLocation getAnimationResource(MammottEntity animatable) {
        return new ResourceLocation(MySingingMod.MOD_ID, "animations/mammott.animation.json");
    }
}
