package net.truepestilence.mysingingmod.entity.client;

import net.minecraft.resources.ResourceLocation;
import net.truepestilence.mysingingmod.MySingingMod;
import net.truepestilence.mysingingmod.entity.custom.ToeJammerEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ToeJammerModel extends AnimatedGeoModel<ToeJammerEntity> {
    @Override
    public ResourceLocation getModelResource(ToeJammerEntity object) {
        return new ResourceLocation(MySingingMod.MOD_ID, "geo/toejammer.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ToeJammerEntity object) {
        return new ResourceLocation(MySingingMod.MOD_ID, "textures/entity/toejammer.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ToeJammerEntity animatable) {
        return new ResourceLocation(MySingingMod.MOD_ID, "animations/toejammer.animation.json");
    }
}
