// 
// Decompiled by Procyon v0.5.36
// 

package me.oringo.oringoclient.qolfeatures.module.render;

import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.item.ItemStack;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.GlStateManager;
import me.oringo.oringoclient.utils.RenderUtils;
import java.awt.Color;
import me.oringo.oringoclient.utils.shader.BlurUtils;
import me.oringo.oringoclient.utils.font.Fonts;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;
import me.oringo.oringoclient.OringoClient;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.ModeSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.qolfeatures.module.Module;

public class InventoryDisplay extends Module
{
    public NumberSetting x;
    public NumberSetting y;
    public ModeSetting blurStrength;
    
    public InventoryDisplay() {
        super("Inventory HUD", 0, Category.RENDER);
        this.x = new NumberSetting("X", 25.0, 0.0, 100.0, 1.0);
        this.y = new NumberSetting("Y", 25.0, 0.0, 100.0, 1.0);
        this.blurStrength = new ModeSetting("Blur Strength", "Low", new String[] { "None", "Low", "High" });
        this.addSettings(this.x, this.y, this.blurStrength);
    }
    
    @SubscribeEvent
    public void onRender(final RenderGameOverlayEvent.Post event) {
        if (this.isToggled() && event.type.equals((Object)RenderGameOverlayEvent.ElementType.HOTBAR) && OringoClient.mc.field_71439_g != null) {
            GL11.glPushMatrix();
            final ScaledResolution scaledResolution = new ScaledResolution(OringoClient.mc);
            final int x = (int)(scaledResolution.func_78326_a() * (this.x.getValue() / 100.0));
            int y = (int)(scaledResolution.func_78328_b() * (this.y.getValue() / 100.0));
            int blur = 0;
            final String selected = this.blurStrength.getSelected();
            switch (selected) {
                case "Low": {
                    blur = 7;
                    break;
                }
                case "High": {
                    blur = 25;
                    break;
                }
            }
            final ScaledResolution resolution = new ScaledResolution(OringoClient.mc);
            BlurUtils.renderBlurredBackground((float)blur, resolution.func_78326_a(), resolution.func_78328_b(), (float)(x - 2), (float)(y + Fonts.fontMedium.getHeight() - 4), 182.0f, (float)(80 - (Fonts.fontMedium.getHeight() - 4)));
            RenderUtils.drawBorderedRoundedRect((float)(x - 2), (float)(y + Fonts.fontMedium.getHeight() - 4), 182.5f, (float)(80 - (Fonts.fontMedium.getHeight() - 4)), 3.0f, 2.5f, new Color(19, 19, 19, 50).getRGB(), OringoClient.clickGui.getColor().getRGB());
            RenderUtils.drawRect((float)(x - 2), y + Fonts.fontMedium.getHeight() * 2.0f + 2.0f, x + 180 + 0.5f, y + Fonts.fontMedium.getHeight() * 2.0f + 3.5f, OringoClient.clickGui.getColor().getRGB());
            Fonts.fontMediumBold.drawSmoothCenteredString("Inventory", x + 90.0f, (float)(y + Fonts.fontMedium.getHeight()), Color.white.getRGB());
            GlStateManager.func_179091_B();
            GlStateManager.func_179147_l();
            GlStateManager.func_179120_a(770, 771, 1, 0);
            RenderHelper.func_74520_c();
            for (int i = 9; i < 36; ++i) {
                if (i % 9 == 0) {
                    y += 20;
                }
                final ItemStack stack = OringoClient.mc.field_71439_g.field_71071_by.func_70301_a(i);
                if (stack != null) {
                    OringoClient.mc.func_175599_af().func_180450_b(stack, x + 20 * (i % 9), y);
                    this.renderItemOverlayIntoGUI(stack, x + 20 * (i % 9), y);
                }
            }
            RenderHelper.func_74518_a();
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.func_179101_C();
            GlStateManager.func_179084_k();
            GL11.glPopMatrix();
        }
    }
    
    public void renderItemOverlayIntoGUI(final ItemStack itemStack, final int x, final int y) {
        if (itemStack != null) {
            if (itemStack.field_77994_a != 1) {
                String s = String.valueOf(itemStack.field_77994_a);
                if (itemStack.field_77994_a < 1) {
                    s = EnumChatFormatting.RED + String.valueOf(itemStack.field_77994_a);
                }
                GlStateManager.func_179140_f();
                GlStateManager.func_179097_i();
                GlStateManager.func_179084_k();
                Fonts.fontMediumBold.drawSmoothStringWithShadow(s, (int)(x + 19 - 2 - Fonts.fontMediumBold.getStringWidth(s)), y + 6 + 3, 16777215);
                GlStateManager.func_179145_e();
                GlStateManager.func_179126_j();
            }
            if (itemStack.func_77973_b().showDurabilityBar(itemStack)) {
                final double health = itemStack.func_77973_b().getDurabilityForDisplay(itemStack);
                final int j = (int)Math.round(13.0 - health * 13.0);
                final int i = (int)Math.round(255.0 - health * 255.0);
                GlStateManager.func_179140_f();
                GlStateManager.func_179097_i();
                GlStateManager.func_179090_x();
                GlStateManager.func_179118_c();
                GlStateManager.func_179084_k();
                final Tessellator tessellator = Tessellator.func_178181_a();
                final WorldRenderer worldrenderer = tessellator.func_178180_c();
                this.draw(worldrenderer, x + 2, y + 13, 13, 2, 0, 0, 0, 255);
                this.draw(worldrenderer, x + 2, y + 13, 12, 1, (255 - i) / 4, 64, 0, 255);
                this.draw(worldrenderer, x + 2, y + 13, j, 1, 255 - i, i, 0, 255);
                GlStateManager.func_179141_d();
                GlStateManager.func_179098_w();
                GlStateManager.func_179145_e();
                GlStateManager.func_179126_j();
            }
        }
    }
    
    private void draw(final WorldRenderer p_draw_1_, final int p_draw_2_, final int p_draw_3_, final int p_draw_4_, final int p_draw_5_, final int p_draw_6_, final int p_draw_7_, final int p_draw_8_, final int p_draw_9_) {
        p_draw_1_.func_181668_a(7, DefaultVertexFormats.field_181706_f);
        p_draw_1_.func_181662_b((double)(p_draw_2_ + 0), (double)(p_draw_3_ + 0), 0.0).func_181669_b(p_draw_6_, p_draw_7_, p_draw_8_, p_draw_9_).func_181675_d();
        p_draw_1_.func_181662_b((double)(p_draw_2_ + 0), (double)(p_draw_3_ + p_draw_5_), 0.0).func_181669_b(p_draw_6_, p_draw_7_, p_draw_8_, p_draw_9_).func_181675_d();
        p_draw_1_.func_181662_b((double)(p_draw_2_ + p_draw_4_), (double)(p_draw_3_ + p_draw_5_), 0.0).func_181669_b(p_draw_6_, p_draw_7_, p_draw_8_, p_draw_9_).func_181675_d();
        p_draw_1_.func_181662_b((double)(p_draw_2_ + p_draw_4_), (double)(p_draw_3_ + 0), 0.0).func_181669_b(p_draw_6_, p_draw_7_, p_draw_8_, p_draw_9_).func_181675_d();
        Tessellator.func_178181_a().func_78381_a();
    }
}
