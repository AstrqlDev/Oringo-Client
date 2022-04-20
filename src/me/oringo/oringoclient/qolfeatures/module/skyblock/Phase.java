// 
// Decompiled by Procyon v0.5.36
// 

package me.oringo.oringoclient.qolfeatures.module.skyblock;

import net.minecraft.block.BlockSkull;
import net.minecraft.init.Blocks;
import net.minecraft.block.BlockWall;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockStairs;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.block.BlockAir;
import net.minecraft.util.MathHelper;
import java.awt.Color;
import me.oringo.oringoclient.utils.font.Fonts;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import me.oringo.oringoclient.events.BlockBoundsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import me.oringo.oringoclient.mixins.MinecraftAccessor;
import net.minecraft.util.BlockPos;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.events.MotionUpdateEvent;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.ModeSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.qolfeatures.module.Module;

public class Phase extends Module
{
    private int ticks;
    public NumberSetting timer;
    public ModeSetting activate;
    public BooleanSetting clip;
    public boolean isPhasing;
    public boolean wasPressed;
    public boolean canPhase;
    
    public Phase() {
        super("Stair Phase", Category.SKYBLOCK);
        this.timer = new NumberSetting("Timer", 1.0, 0.1, 1.0, 0.1);
        this.activate = new ModeSetting("Activate", "on Key", new String[] { "Auto", "on Key" });
        this.clip = new BooleanSetting("Autoclip", true);
        this.addSettings(this.timer, this.clip, this.activate);
    }
    
    @Override
    public void onDisable() {
        this.isPhasing = false;
    }
    
    @SubscribeEvent
    public void onUpdate(final MotionUpdateEvent.Pre event) {
        if (OringoClient.mc.field_71439_g == null || OringoClient.mc.field_71441_e == null) {
            return;
        }
        --this.ticks;
        if (this.isToggled()) {
            this.canPhase = (OringoClient.mc.field_71439_g.field_70122_E && OringoClient.mc.field_71439_g.field_70124_G && isValidBlock(OringoClient.mc.field_71441_e.func_180495_p(new BlockPos(OringoClient.mc.field_71439_g.field_70165_t, OringoClient.mc.field_71439_g.field_70163_u, OringoClient.mc.field_71439_g.field_70161_v)).func_177230_c()));
            if (!this.isPhasing && (!this.isKeybind() || (this.isPressed() && !this.wasPressed)) && OringoClient.mc.field_71439_g.field_70122_E && OringoClient.mc.field_71439_g.field_70124_G && isValidBlock(OringoClient.mc.field_71441_e.func_180495_p(new BlockPos(OringoClient.mc.field_71439_g.field_70165_t, OringoClient.mc.field_71439_g.field_70163_u, OringoClient.mc.field_71439_g.field_70161_v)).func_177230_c())) {
                this.isPhasing = true;
                this.ticks = 8;
            }
            else if (this.isPhasing && ((!isInsideBlock() && this.ticks < 0) || (this.isPressed() && !this.wasPressed && this.isKeybind()))) {
                OringoClient.mc.field_71439_g.func_70016_h(0.0, 0.0, 0.0);
                this.isPhasing = false;
            }
            if (this.isPhasing && isInsideBlock()) {
                ((MinecraftAccessor)OringoClient.mc).getTimer().field_74278_d = (float)this.timer.getValue();
            }
            else {
                ((MinecraftAccessor)OringoClient.mc).getTimer().field_74278_d = 1.0f;
            }
        }
        this.wasPressed = this.isPressed();
        if (!this.isToggled() || !this.isPhasing) {
            ((MinecraftAccessor)OringoClient.mc).getTimer().field_74278_d = 1.0f;
        }
    }
    
    @SubscribeEvent
    public void onBlockBounds(final BlockBoundsEvent event) {
        if (!this.isPhasing || OringoClient.mc.field_71439_g == null || !this.isToggled()) {
            return;
        }
        if (event.collidingEntity == OringoClient.mc.field_71439_g && ((event.aabb != null && event.aabb.field_72337_e > OringoClient.mc.field_71439_g.func_174813_aQ().field_72338_b) || OringoClient.mc.field_71474_y.field_74311_E.func_151470_d() || (this.ticks == 7 && this.clip.isEnabled()))) {
            event.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public void onRender(final RenderGameOverlayEvent.Post event) {
        if (OringoClient.mc.field_71441_e == null || OringoClient.mc.field_71439_g == null || !this.isToggled()) {
            return;
        }
        if (this.canPhase && this.activate.is("on Key") && event.type == RenderGameOverlayEvent.ElementType.HOTBAR) {
            final ScaledResolution resolution = new ScaledResolution(OringoClient.mc);
            Fonts.fontMediumBold.drawSmoothCenteredString("Phase usage detected", resolution.func_78326_a() / 2.0f + 0.4f, resolution.func_78328_b() - resolution.func_78328_b() / 4.5f + 0.5f, new Color(20, 20, 20).getRGB());
            Fonts.fontMediumBold.drawSmoothCenteredString("Phase usage detected", resolution.func_78326_a() / 2.0f, resolution.func_78328_b() - resolution.func_78328_b() / 4.5f, OringoClient.clickGui.getColor().getRGB());
        }
    }
    
    public static boolean isInsideBlock() {
        for (int x = MathHelper.func_76128_c(OringoClient.mc.field_71439_g.func_174813_aQ().field_72340_a); x < MathHelper.func_76128_c(OringoClient.mc.field_71439_g.func_174813_aQ().field_72336_d) + 1; ++x) {
            for (int y = MathHelper.func_76128_c(OringoClient.mc.field_71439_g.func_174813_aQ().field_72338_b); y < MathHelper.func_76128_c(OringoClient.mc.field_71439_g.func_174813_aQ().field_72337_e) + 1; ++y) {
                for (int z = MathHelper.func_76128_c(OringoClient.mc.field_71439_g.func_174813_aQ().field_72339_c); z < MathHelper.func_76128_c(OringoClient.mc.field_71439_g.func_174813_aQ().field_72334_f) + 1; ++z) {
                    final Block block = OringoClient.mc.field_71441_e.func_180495_p(new BlockPos(x, y, z)).func_177230_c();
                    if (block != null && !(block instanceof BlockAir)) {
                        final AxisAlignedBB boundingBox = block.func_180640_a((World)OringoClient.mc.field_71441_e, new BlockPos(x, y, z), OringoClient.mc.field_71441_e.func_180495_p(new BlockPos(x, y, z)));
                        if (boundingBox != null && OringoClient.mc.field_71439_g.func_174813_aQ().func_72326_a(boundingBox)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    @Override
    public boolean isKeybind() {
        return this.activate.is("on Key");
    }
    
    private static boolean isValidBlock(final Block block) {
        return block instanceof BlockStairs || block instanceof BlockFence || block instanceof BlockFenceGate || block instanceof BlockWall || block == Blocks.field_150438_bZ || block instanceof BlockSkull;
    }
}
