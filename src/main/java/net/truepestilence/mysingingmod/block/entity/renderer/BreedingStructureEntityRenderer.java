package net.truepestilence.mysingingmod.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.truepestilence.mysingingmod.block.custom.BreedingStructure;
import net.truepestilence.mysingingmod.block.entity.BreedingStructureEntity;

import java.util.List;

public class BreedingStructureEntityRenderer implements BlockEntityRenderer<BreedingStructureEntity> {
    public BreedingStructureEntityRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(BreedingStructureEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();

        List<ItemStack> stacks = pBlockEntity.getRenderStacks();
        for(int i = 0; i < 3; i++) {
            pPoseStack.pushPose();
            switch(i) {
                case 0 -> {
                    pPoseStack.translate(-0.125f, 2.425f, 0.75f);
                    switch (pBlockEntity.getBlockState().getValue(BreedingStructure.FACING)) {
                        case EAST -> {
                            pPoseStack.mulPose(Vector3f.YP.rotationDegrees(270));
                            pPoseStack.translate(0.375f, 0.0f, -0.375f);
                        }
                        case SOUTH -> {
                            pPoseStack.mulPose(Vector3f.YP.rotationDegrees(180));
                            pPoseStack.translate(0.0f, 0.0f, 0.5f);
                        }
                        case WEST -> {
                            pPoseStack.mulPose(Vector3f.YP.rotationDegrees(90));
                            pPoseStack.translate(-0.375f, 0.0f, 0.875f);
                        }
                    }
                }
                case 1 -> {
                    pPoseStack.translate(1.125f, 2.425f, 0.75f);
                    switch (pBlockEntity.getBlockState().getValue(BreedingStructure.FACING)) {
                        case EAST -> {
                            pPoseStack.mulPose(Vector3f.YP.rotationDegrees(270));
                            pPoseStack.translate(0.375f, 0.0f, 0.875f);
                        }
                        case SOUTH -> {
                            pPoseStack.mulPose(Vector3f.YP.rotationDegrees(180));
                            pPoseStack.translate(0.0f, 0.0f, 0.5f);
                        }
                        case WEST -> {
                            pPoseStack.mulPose(Vector3f.YP.rotationDegrees(90));
                            pPoseStack.translate(-0.375f, 0.0f, -0.375f);
                        }
                    }
                }
                case 2 -> {
                    pPoseStack.translate(0.5f, 0.85f, 0.625f);
                    switch (pBlockEntity.getBlockState().getValue(BreedingStructure.FACING)) {
                        case EAST -> {
                            pPoseStack.mulPose(Vector3f.YP.rotationDegrees(270));
                            pPoseStack.translate(0.125f, 0.0f, 0.125f);
                        }
                        case NORTH -> {
                            pPoseStack.mulPose(Vector3f.YP.rotationDegrees(180));
                            pPoseStack.translate(0.0f, 0.0f, -0.25f);
                        }
                        case WEST -> {
                            pPoseStack.mulPose(Vector3f.YP.rotationDegrees(90));
                            pPoseStack.translate(-0.125f, 0.0f, 0.125f);
                        }
                    }
                }
            }
            pPoseStack.scale(1f, 1f, 1f);
            pPoseStack.mulPose(Vector3f.YP.rotationDegrees(180));

            renderer.renderStatic(stacks.get(i), ItemTransforms.TransformType.GUI, getLightLevel(pBlockEntity.getLevel(),
                    pBlockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, pPoseStack, pBufferSource, 1);
            pPoseStack.popPose();
        }
    }

    public int getLightLevel(Level pLevel, BlockPos pos) {
        int bLight = pLevel.getBrightness(LightLayer.BLOCK, pos);
        int sLight = pLevel.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }
}
