// 
// Decompiled by Procyon v0.5.36
// 

package me.oringo.oringoclient.mixins;

import net.minecraft.util.BlockPos;
import net.minecraft.block.material.Material;
import me.oringo.oringoclient.events.LeftClickEvent;
import me.oringo.oringoclient.qolfeatures.module.player.FastPlace;
import net.minecraftforge.fml.common.eventhandler.Event;
import me.oringo.oringoclient.events.RightClickEvent;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import me.oringo.oringoclient.mixins.entity.PlayerSPAccessor;
import net.minecraft.entity.EntityLivingBase;
import me.oringo.oringoclient.qolfeatures.module.combat.KillAura;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.qolfeatures.module.render.ServerRotations;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.mojang.authlib.properties.PropertyMap;
import net.minecraft.util.Timer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.MovingObjectPosition;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ Minecraft.class })
public abstract class MinecraftMixin
{
    @Shadow
    private int field_71429_W;
    @Shadow
    public MovingObjectPosition field_71476_x;
    @Shadow
    public WorldClient field_71441_e;
    @Shadow
    public PlayerControllerMP field_71442_b;
    @Shadow
    private Entity field_175622_Z;
    @Shadow
    public EntityPlayerSP field_71439_g;
    @Shadow
    public GuiScreen field_71462_r;
    @Shadow
    public GameSettings field_71474_y;
    @Shadow
    public boolean field_71415_G;
    @Shadow
    public boolean field_175612_E;
    @Shadow
    private Timer field_71428_T;
    @Shadow
    private int field_71467_ac;
    
    @Shadow
    public abstract PropertyMap func_181037_M();
    
    @Inject(method = { "getRenderViewEntity" }, at = { @At("HEAD") })
    public void getRenderViewEntity(final CallbackInfoReturnable<Entity> cir) {
        if (!ServerRotations.getInstance().isToggled() || this.field_175622_Z == null || this.field_175622_Z != OringoClient.mc.field_71439_g) {
            return;
        }
        if (!ServerRotations.getInstance().onlyKillAura.isEnabled() || KillAura.target != null) {
            ((EntityLivingBase)this.field_175622_Z).field_70759_as = ((PlayerSPAccessor)this.field_175622_Z).getLastReportedYaw();
            ((EntityLivingBase)this.field_175622_Z).field_70761_aq = ((PlayerSPAccessor)this.field_175622_Z).getLastReportedYaw();
        }
    }
    
    @Inject(method = { "runTick" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;dispatchKeypresses()V") })
    public void keyPresses(final CallbackInfo ci) {
        final int k = (Keyboard.getEventKey() == 0) ? (Keyboard.getEventCharacter() + '\u0100') : Keyboard.getEventKey();
        if (OringoClient.mc.field_71462_r == null && Keyboard.getEventKeyState()) {
            OringoClient.handleKeypress(k);
        }
    }
    
    @Inject(method = { "rightClickMouse" }, at = { @At("HEAD") }, cancellable = true)
    public void onRightClick(final CallbackInfo callbackInfo) {
        if (MinecraftForge.EVENT_BUS.post((Event)new RightClickEvent())) {
            callbackInfo.cancel();
        }
    }
    
    @Inject(method = { "rightClickMouse" }, at = { @At("RETURN") }, cancellable = true)
    public void onRightClickPost(final CallbackInfo callbackInfo) {
        if (FastPlace.getInstance().isToggled()) {
            this.field_71467_ac = (int)FastPlace.getInstance().placeDelay.getValue();
        }
    }
    
    @Inject(method = { "clickMouse" }, at = { @At("HEAD") }, cancellable = true)
    public void onClick(final CallbackInfo callbackInfo) {
        if (MinecraftForge.EVENT_BUS.post((Event)new LeftClickEvent())) {
            callbackInfo.cancel();
        }
        if (OringoClient.noHitDelay.isToggled() || OringoClient.mithrilMacro.isToggled()) {
            this.field_71429_W = 0;
        }
    }
    
    @Inject(method = { "sendClickBlockToController" }, at = { @At("RETURN") })
    public void sendClickBlock(final CallbackInfo callbackInfo) {
        final boolean click = this.field_71462_r == null && this.field_71474_y.field_74312_F.func_151470_d() && this.field_71415_G;
        if (OringoClient.fastBreak.isToggled() && click && this.field_71476_x != null && this.field_71476_x.field_72313_a == MovingObjectPosition.MovingObjectType.BLOCK) {
            for (int i = 0; i < OringoClient.fastBreak.maxBlocks.getValue(); ++i) {
                final BlockPos prevBlockPos = this.field_71476_x.func_178782_a();
                this.field_71476_x = this.field_175622_Z.func_174822_a((double)this.field_71442_b.func_78757_d(), 1.0f);
                final BlockPos blockpos = this.field_71476_x.func_178782_a();
                if (this.field_71476_x.field_72313_a != MovingObjectPosition.MovingObjectType.BLOCK || blockpos == prevBlockPos || this.field_71441_e.func_180495_p(blockpos).func_177230_c().func_149688_o() == Material.field_151579_a) {
                    break;
                }
                this.field_71439_g.func_71038_i();
                this.field_71442_b.func_180511_b(blockpos, this.field_71476_x.field_178784_b);
            }
        }
    }
}
