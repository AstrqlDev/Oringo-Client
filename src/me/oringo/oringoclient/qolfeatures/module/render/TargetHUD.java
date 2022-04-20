// 
// Decompiled by Procyon v0.5.36
// 

package me.oringo.oringoclient.qolfeatures.module.render;

import java.util.Iterator;
import net.minecraft.entity.Entity;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.gui.Gui;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.oringo.oringoclient.utils.font.Fonts;
import java.awt.Color;
import me.oringo.oringoclient.utils.shader.BlurUtils;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;
import net.minecraft.entity.item.EntityArmorStand;
import me.oringo.oringoclient.qolfeatures.module.combat.KillAura;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraft.world.World;
import net.minecraft.network.play.client.C02PacketUseEntity;
import me.oringo.oringoclient.events.PacketSentEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import me.oringo.oringoclient.utils.RenderUtils;
import me.oringo.oringoclient.OringoClient;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.ModeSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import me.oringo.oringoclient.utils.MilliTimer;
import me.oringo.oringoclient.qolfeatures.module.Module;

public class TargetHUD extends Module
{
    public static TargetHUD instance;
    private MilliTimer timer;
    private EntityLivingBase entity;
    protected static final ResourceLocation inventoryBackground;
    private static Minecraft mc;
    private float lastHp;
    public BooleanSetting onlyAura;
    public ModeSetting blurStrength;
    public NumberSetting x;
    public NumberSetting y;
    public BooleanSetting targetESP;
    
    public static TargetHUD getInstance() {
        return TargetHUD.instance;
    }
    
    public TargetHUD() {
        super("Target HUD", Category.RENDER);
        this.timer = new MilliTimer();
        this.lastHp = 0.8f;
        this.onlyAura = new BooleanSetting("Only kill aura", false);
        this.blurStrength = new ModeSetting("Blur Strength", "Low", new String[] { "None", "Low", "High" });
        this.x = new NumberSetting("X", 25.0, 0.0, 100.0, 1.0);
        this.y = new NumberSetting("Y", 25.0, 0.0, 100.0, 1.0);
        this.targetESP = new BooleanSetting("Target ESP", true);
        this.setToggled(true);
        this.addSettings(this.targetESP, this.onlyAura, this.x, this.y, this.blurStrength);
    }
    
    @SubscribeEvent
    public void onRender(final RenderWorldLastEvent event) {
        if (this.entity != null && this.entity.func_110143_aJ() > 0.0f && this.targetESP.isEnabled() && this.isToggled()) {
            RenderUtils.drawTargetESP(this.entity, OringoClient.clickGui.getColor(), event.partialTicks);
        }
    }
    
    @SubscribeEvent
    public void onPacket(final PacketSentEvent e) {
        if (!this.onlyAura.isEnabled() && e.packet instanceof C02PacketUseEntity && ((C02PacketUseEntity)e.packet).func_149565_c() == C02PacketUseEntity.Action.ATTACK && ((C02PacketUseEntity)e.packet).func_149564_a((World)TargetHUD.mc.field_71441_e) != null && ((C02PacketUseEntity)e.packet).func_149564_a((World)TargetHUD.mc.field_71441_e) instanceof EntityLivingBase) {
            this.timer.updateTime();
            this.entity = (EntityLivingBase)((C02PacketUseEntity)e.packet).func_149564_a((World)TargetHUD.mc.field_71441_e);
        }
    }
    
    @SubscribeEvent
    public void onRender(final RenderGameOverlayEvent.Post event) {
        if (this.timer.hasTimePassed(1500L)) {
            this.entity = null;
        }
        if (this.onlyAura.isEnabled() || KillAura.target != null) {
            this.entity = KillAura.target;
        }
        if (event.type == RenderGameOverlayEvent.ElementType.ALL && this.entity != null && this.entity.func_110143_aJ() > 0.0f && this.isToggled() && !(this.entity instanceof EntityArmorStand)) {
            final int scale = TargetHUD.mc.field_71474_y.field_74335_Z;
            GL11.glPushMatrix();
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
            final ScaledResolution resolution = new ScaledResolution(TargetHUD.mc);
            final int x = (int)(-resolution.func_78326_a() * (this.x.getValue() / 100.0));
            final int y = (int)(-resolution.func_78328_b() * (this.y.getValue() / 100.0));
            BlurUtils.renderBlurredBackground((float)blur, resolution.func_78326_a(), resolution.func_78328_b(), (float)(resolution.func_78326_a() - 170 - resolution.func_78326_a() / 4), (float)(resolution.func_78328_b() - 70 - resolution.func_78328_b() / 4), 150.0f, 50.0f);
            GL11.glTranslatef((float)x, (float)y, 0.0f);
            RenderUtils.drawRoundRect2((float)(resolution.func_78326_a() - 170), (float)(resolution.func_78328_b() - 70), 150.0f, 50.0f, 3.0f, new Color(21, 21, 21, 52).getRGB());
            Fonts.fontBig.drawSmoothString(ChatFormatting.stripFormatting(this.entity.func_70005_c_()), resolution.func_78326_a() - 165 + 0.4f, resolution.func_78328_b() - 64 + 0.5f, new Color(20, 20, 20).getRGB());
            Fonts.fontBig.drawSmoothString(ChatFormatting.stripFormatting(this.entity.func_70005_c_()), resolution.func_78326_a() - 165, (float)(resolution.func_78328_b() - 64), OringoClient.clickGui.getColor().brighter().getRGB());
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            int i = 0;
            TargetHUD.mc.func_110434_K().func_110577_a(TargetHUD.inventoryBackground);
            GL11.glTranslatef((float)(resolution.func_78326_a() - 167), (float)(resolution.func_78328_b() - 63 + Fonts.fontMediumBold.getHeight()), 0.0f);
            GL11.glScalef(0.5f, 0.5f, 0.5f);
            for (final PotionEffect effect : this.entity.func_70651_bq()) {
                final Potion potion = Potion.field_76425_a[effect.func_76456_a()];
                if (potion.func_76400_d()) {
                    final int i2 = potion.func_76392_e();
                    new Gui().func_73729_b(i++ * 20, 0, i2 % 8 * 18, 198 + i2 / 8 * 18, 18, 18);
                }
            }
            GL11.glScalef(2.0f, 2.0f, 2.0f);
            GL11.glTranslatef((float)(-resolution.func_78326_a() + 167), (float)(-resolution.func_78328_b() + 63 - Fonts.fontMediumBold.getHeight()), 0.0f);
            try {
                final EntityLivingBase entity = this.entity;
                entity.field_70163_u += 1000.0;
                GuiInventory.func_147046_a(resolution.func_78326_a() - 40, resolution.func_78328_b() - 33, (int)(35.0 / Math.max(this.entity.field_70131_O, 1.5)), 20.0f, 10.0f, this.entity);
                final EntityLivingBase entity2 = this.entity;
                entity2.field_70163_u -= 1000.0;
            }
            catch (Exception ex) {}
            Fonts.fontMediumBold.drawSmoothString((int)(this.entity.func_70032_d((Entity)TargetHUD.mc.field_71439_g) * 10.0f) / 10.0 + "m", resolution.func_78326_a() - 165 + 0.4f, resolution.func_78328_b() - 59 + Fonts.fontMediumBold.getHeight() * 2 + 0.5f, new Color(20, 20, 20).getRGB());
            Fonts.fontMediumBold.drawSmoothString((int)(this.entity.func_70032_d((Entity)TargetHUD.mc.field_71439_g) * 10.0f) / 10.0 + "m", resolution.func_78326_a() - 165, (float)(resolution.func_78328_b() - 59 + Fonts.fontMediumBold.getHeight() * 2), new Color(231, 231, 231).getRGB());
            final float hp = (Math.abs(this.entity.func_110143_aJ() / this.entity.func_110138_aP() - this.lastHp) < 0.01) ? (this.entity.func_110143_aJ() / this.entity.func_110138_aP()) : ((float)Math.min(this.lastHp + ((this.entity.func_110143_aJ() / this.entity.func_110138_aP() > this.lastHp) ? 0.01 : -0.01), 1.0));
            final String text = String.format("%s", (int)(Math.min(this.entity.func_110143_aJ() / this.entity.func_110138_aP(), 1.0f) * 100.0f) + "%");
            RenderUtils.drawRoundRect((float)(resolution.func_78326_a() - 160 + 130), (float)(resolution.func_78328_b() - 30), (float)(resolution.func_78326_a() - 160), (float)(resolution.func_78328_b() - 26), 1.0f, Color.HSBtoRGB(0.0f, 0.0f, 0.1f));
            RenderUtils.drawRoundRect(resolution.func_78326_a() - 160 + 130.0f * hp, (float)(resolution.func_78328_b() - 30), (float)(resolution.func_78326_a() - 160), (float)(resolution.func_78328_b() - 26), 1.0f, OringoClient.clickGui.getColor().getRGB());
            Fonts.fontMediumBold.drawSmoothString(text, (int)(resolution.func_78326_a() - 170 + 75.0) - Fonts.fontMediumBold.getStringWidth(text) / 2.0 + 0.4000000059604645, resolution.func_78328_b() - 31 - Fonts.fontMediumBold.getHeight() + 0.5f, new Color(20, 20, 20).getRGB());
            Fonts.fontMediumBold.drawSmoothString(text, (int)(resolution.func_78326_a() - 170 + 75.0) - Fonts.fontMediumBold.getStringWidth(text) / 2.0, (float)(resolution.func_78328_b() - 31 - Fonts.fontMediumBold.getHeight()), new Color(231, 231, 231).getRGB());
            this.lastHp = hp;
            TargetHUD.mc.field_71474_y.field_74335_Z = scale;
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glPopMatrix();
        }
    }
    
    static {
        TargetHUD.instance = new TargetHUD();
        inventoryBackground = new ResourceLocation("textures/gui/container/inventory.png");
        TargetHUD.mc = Minecraft.func_71410_x();
    }
}
