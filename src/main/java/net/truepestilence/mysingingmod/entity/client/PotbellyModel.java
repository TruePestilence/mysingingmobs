package net.truepestilence.mysingingmod.entity.client;

import net.minecraft.resources.ResourceLocation;
import net.truepestilence.mysingingmod.MySingingMod;
import net.truepestilence.mysingingmod.entity.custom.PotbellyEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PotbellyModel extends AnimatedGeoModel<PotbellyEntity> {
    @Override
    public ResourceLocation getModelResource(PotbellyEntity object) {
        return new ResourceLocation(MySingingMod.MOD_ID, "geo/potbelly.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(PotbellyEntity object) {
        return new ResourceLocation(MySingingMod.MOD_ID, "textures/entity/potbelly.png");
    }

    @Override
    public ResourceLocation getAnimationResource(PotbellyEntity animatable) {
        return new ResourceLocation(MySingingMod.MOD_ID, "animations/potbelly.animation.json");
    }
}
