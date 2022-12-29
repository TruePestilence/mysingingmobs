package net.truepestilence.mysingingmod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.truepestilence.mysingingmod.MySingingMod;
import net.truepestilence.mysingingmod.entity.custom.NogginEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class NogginRenderer extends GeoEntityRenderer<NogginEntity> {
    public NogginRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new NogginModel());
        this.shadowRadius = 0.75f;
    }

    @Override
    public ResourceLocation getTextureLocation(NogginEntity animatable) {
        return new ResourceLocation(MySingingMod.MOD_ID, "textures/entity/noggin.png");
    }

    @Override
    public RenderType getRenderType(NogginEntity animatable, float partialTick, PoseStack poseStack, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, int packedLight, ResourceLocation texture) {
        poseStack.scale(1.4f,1.4f,1.4f);
        return super.getRenderType(animatable, partialTick, poseStack, bufferSource, buffer, packedLight, texture);
    }
}
