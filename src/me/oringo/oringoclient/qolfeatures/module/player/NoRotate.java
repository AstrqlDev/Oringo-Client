// 
// Decompiled by Procyon v0.5.36
// 

package me.oringo.oringoclient.qolfeatures.module.player;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import me.oringo.oringoclient.OringoClient;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import me.oringo.oringoclient.events.PacketReceivedEvent;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.Module;

public class NoRotate extends Module
{
    public BooleanSetting keepMotion;
    public BooleanSetting pitch;
    private boolean doneLoadingTerrain;
    
    public NoRotate() {
        super("No Rotate", 0, Category.PLAYER);
        this.keepMotion = new BooleanSetting("Keep motion", true);
        this.pitch = new BooleanSetting("0 pitch", false);
        this.addSettings(this.keepMotion, this.pitch);
    }
    
    @SubscribeEvent
    public void onPacket(final PacketReceivedEvent event) {
        if (event.packet instanceof S08PacketPlayerPosLook) {
            if (this.isToggled() && OringoClient.mc.field_71439_g != null && (((S08PacketPlayerPosLook)event.packet).func_148930_g() != 0.0 || this.pitch.isEnabled())) {
                event.setCanceled(true);
                final EntityPlayer entityplayer = (EntityPlayer)OringoClient.mc.field_71439_g;
                double d0 = ((S08PacketPlayerPosLook)event.packet).func_148932_c();
                double d2 = ((S08PacketPlayerPosLook)event.packet).func_148928_d();
                double d3 = ((S08PacketPlayerPosLook)event.packet).func_148933_e();
                float f = ((S08PacketPlayerPosLook)event.packet).func_148931_f();
                float f2 = ((S08PacketPlayerPosLook)event.packet).func_148930_g();
                if (((S08PacketPlayerPosLook)event.packet).func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.X)) {
                    d0 += entityplayer.field_70165_t;
                }
                else if (!this.keepMotion.isEnabled()) {
                    entityplayer.field_70159_w = 0.0;
                }
                if (((S08PacketPlayerPosLook)event.packet).func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.Y)) {
                    d2 += entityplayer.field_70163_u;
                }
                else {
                    entityplayer.field_70181_x = 0.0;
                }
                if (((S08PacketPlayerPosLook)event.packet).func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.Z)) {
                    d3 += entityplayer.field_70161_v;
                }
                else if (!this.keepMotion.isEnabled()) {
                    entityplayer.field_70179_y = 0.0;
                }
                if (((S08PacketPlayerPosLook)event.packet).func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.X_ROT)) {
                    f2 += entityplayer.field_70125_A;
                }
                if (((S08PacketPlayerPosLook)event.packet).func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.Y_ROT)) {
                    f += entityplayer.field_70177_z;
                }
                entityplayer.func_70080_a(d0, d2, d3, entityplayer.field_70177_z, entityplayer.field_70125_A);
                OringoClient.mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C03PacketPlayer.C06PacketPlayerPosLook(entityplayer.field_70165_t, entityplayer.func_174813_aQ().field_72338_b, entityplayer.field_70161_v, f, f2, false));
                if (!this.doneLoadingTerrain) {
                    OringoClient.mc.field_71439_g.field_70169_q = OringoClient.mc.field_71439_g.field_70165_t;
                    OringoClient.mc.field_71439_g.field_70167_r = OringoClient.mc.field_71439_g.field_70163_u;
                    OringoClient.mc.field_71439_g.field_70166_s = OringoClient.mc.field_71439_g.field_70161_v;
                    OringoClient.mc.func_147108_a((GuiScreen)null);
                }
            }
            this.doneLoadingTerrain = true;
        }
        if (event.packet instanceof S07PacketRespawn) {
            this.doneLoadingTerrain = false;
        }
    }
}
