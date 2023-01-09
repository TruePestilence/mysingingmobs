package net.truepestilence.mysingingmod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.truepestilence.mysingingmod.MySingingMod;

import java.awt.*;

import static net.minecraft.util.Mth.floor;

public class CastleCoreScreen extends AbstractContainerScreen<CastleCoreMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(MySingingMod.MOD_ID, "textures/gui/nursery_gui.png");

    public int beds;
    public int bedsUsed;

    public CastleCoreScreen(CastleCoreMenu menu, Inventory inv, Component component) {
        super(menu, inv, component);
        this.beds = menu.blockEntity.beds;
        this.bedsUsed = menu.blockEntity.bedsUsed;
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void renderBg(PoseStack stack, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        this.blit(stack, x, y, 0, 0, imageWidth, imageHeight + 2);
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float delta) {
        renderBackground(stack);
        super.render(stack, mouseX, mouseY, delta);
        renderTooltip(stack, mouseX, mouseY);
        int x = (width) / 2;
        int y = ((height - imageHeight) / 2) + floor(imageHeight / 2.75);
        String text = "Beds: ".concat(Integer.toString(this.bedsUsed)).concat("/").concat(Integer.toString(this.beds));
        this.font.drawWordWrap(FormattedText.of(text),(x - font.width(text) / 2),y,2048,0xFF202020);
    }
}
