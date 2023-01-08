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
import net.truepestilence.mysingingmod.block.custom.Nursery;
import net.truepestilence.mysingingmod.block.entity.BreedingStructureEntity;
import net.truepestilence.mysingingmod.block.entity.NurseryEntity;

import java.util.List;

public class NurseryEntityRenderer implements BlockEntityRenderer<NurseryEntity> {
    public NurseryEntityRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(NurseryEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();

        ItemStack stack = pBlockEntity.getRenderStack();
        pPoseStack.pushPose();
        pPoseStack.translate(0.5f, 1f, 0.5f);
        pPoseStack.scale(1f, 1f, 1f);

        switch (pBlockEntity.getBlockState().getValue(Nursery.FACING)) {
            case EAST, WEST -> pPoseStack.mulPose(Vector3f.YP.rotationDegrees(90));
        }

        renderer.renderStatic(stack, ItemTransforms.TransformType.GUI, getLightLevel(pBlockEntity.getLevel(),
                pBlockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, pPoseStack, pBufferSource, 1);
        pPoseStack.popPose();
    }

    public int getLightLevel(Level pLevel, BlockPos pos) {
        int bLight = pLevel.getBrightness(LightLayer.BLOCK, pos);
        int sLight = pLevel.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }
}
