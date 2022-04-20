// 
// Decompiled by Procyon v0.5.36
// 

package me.oringo.oringoclient.qolfeatures.module.other;

import java.util.Iterator;
import me.oringo.oringoclient.utils.PacketUtils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import me.oringo.oringoclient.events.PacketSentEvent;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.utils.MilliTimer;
import net.minecraft.network.Packet;
import java.util.ArrayList;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.qolfeatures.module.Module;

public class Blink extends Module
{
    private NumberSetting autoDisable;
    private ArrayList<Packet<?>> packets;
    private MilliTimer timer;
    
    public Blink() {
        super("Blink", Category.OTHER);
        this.autoDisable = new NumberSetting("Auto disable", 20.0, 0.0, 250.0, 1.0);
        this.packets = new ArrayList<Packet<?>>();
        this.timer = new MilliTimer();
        this.addSettings(this.autoDisable);
    }
    
    @Override
    public void onEnable() {
        this.timer.updateTime();
    }
    
    @SubscribeEvent
    public void onPacket(final PacketSentEvent event) {
        if (!this.isToggled()) {
            return;
        }
        event.setCanceled(true);
        this.packets.add(event.packet);
        if (this.autoDisable.getValue() != 0.0 && this.timer.hasTimePassed((long)(this.autoDisable.getValue() * 50.0))) {
            this.toggle();
        }
    }
    
    @Override
    public void onDisable() {
        for (final Packet<?> packet : this.packets) {
            PacketUtils.sendPacketNoEvent(packet);
        }
        this.packets.clear();
    }
}
