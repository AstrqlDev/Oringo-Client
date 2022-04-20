// 
// Decompiled by Procyon v0.5.36
// 

package me.oringo.oringoclient.qolfeatures.module.player;

import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.network.Packet;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.events.MotionUpdateEvent;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.Module;

public class Sprint extends Module
{
    public BooleanSetting omni;
    public BooleanSetting keep;
    
    public Sprint() {
        super("Sprint", 0, Category.COMBAT);
        this.omni = new BooleanSetting("OmniSprint", false);
        this.keep = new BooleanSetting("KeepSprint", true);
        this.addSettings(this.keep, this.omni);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void niceOmniSprintCheck(final MotionUpdateEvent.Pre event) {
        if (((this.omni.isEnabled() && this.isToggled()) || OringoClient.derp.isToggled()) && !OringoClient.mc.field_71439_g.func_70093_af()) {
            OringoClient.mc.func_147114_u().func_147297_a((Packet)new C0BPacketEntityAction((Entity)OringoClient.mc.field_71439_g, C0BPacketEntityAction.Action.START_SNEAKING));
            OringoClient.mc.func_147114_u().func_147297_a((Packet)new C0BPacketEntityAction((Entity)OringoClient.mc.field_71439_g, C0BPacketEntityAction.Action.STOP_SNEAKING));
        }
    }
}
