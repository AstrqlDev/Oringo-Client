// 
// Decompiled by Procyon v0.5.36
// 

package me.oringo.oringoclient.qolfeatures.module.other;

import net.minecraft.client.entity.EntityPlayerSP;
import me.oringo.oringoclient.utils.MovementUtils;
import me.oringo.oringoclient.events.MotionUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.world.WorldEvent;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.utils.MilliTimer;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.qolfeatures.module.Module;

public class TaraFly extends Module
{
    public NumberSetting setting;
    public NumberSetting time;
    private BooleanSetting dev;
    public MilliTimer disablerTimer;
    private boolean isFlying;
    
    public TaraFly() {
        super("Slime fly", 0, Category.OTHER);
        this.setting = new NumberSetting("Speed", 1.0, 0.1, 3.0, 0.01);
        this.time = new NumberSetting("Disabler timer", 1200.0, 250.0, 2500.0, 1.0);
        this.dev = new BooleanSetting("Dev", false) {
            @Override
            public boolean isHidden() {
                return !OringoClient.devMode;
            }
        };
        this.disablerTimer = new MilliTimer();
        this.isFlying = false;
        this.addSettings(this.setting, this.time, this.dev);
    }
    
    @Override
    public void onDisable() {
        OringoClient.mc.field_71439_g.func_70016_h(0.0, 0.0, 0.0);
        this.isFlying = false;
    }
    
    @SubscribeEvent
    public void onWorldLoad(final WorldEvent.Load event) {
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChat(final ClientChatReceivedEvent event) {
        if (ChatFormatting.stripFormatting(event.message.func_150254_d()).contains("Double-jump boots are temporarily disabled!")) {
            event.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public void onUpdate(final MotionUpdateEvent.Pre pre) {
        if (this.isFlying && this.disablerTimer.hasTimePassed((long)this.time.getValue())) {
            OringoClient.mc.field_71439_g.func_70016_h(0.0, 0.0, 0.0);
            this.isFlying = false;
        }
        if (!this.isToggled() || (!OringoClient.mc.field_71439_g.field_71075_bZ.field_75101_c && this.disablerTimer.hasTimePassed((long)this.time.getValue()) && !this.dev.isEnabled())) {
            return;
        }
        OringoClient.mc.field_71439_g.field_70143_R = 0.0f;
        OringoClient.mc.field_71439_g.field_70122_E = false;
        OringoClient.mc.field_71439_g.field_71075_bZ.field_75100_b = false;
        OringoClient.mc.field_71439_g.field_70181_x = 0.0;
        if (OringoClient.mc.field_71439_g.field_71075_bZ.field_75101_c && (OringoClient.mc.field_71439_g.field_70173_aa % 6 == 0 || !this.isFlying)) {
            OringoClient.mc.field_71439_g.field_71075_bZ.field_75100_b = true;
            OringoClient.mc.field_71439_g.func_71016_p();
            OringoClient.mc.field_71439_g.field_71075_bZ.field_75100_b = false;
            OringoClient.mc.field_71439_g.func_71016_p();
            this.isFlying = true;
            this.disablerTimer.updateTime();
        }
        if (!MovementUtils.isMoving()) {
            OringoClient.mc.field_71439_g.field_70179_y = 0.0;
            OringoClient.mc.field_71439_g.field_70159_w = 0.0;
        }
        final double speed = this.setting.getValue();
        OringoClient.mc.field_71439_g.field_70747_aH = (float)speed;
        if (OringoClient.mc.field_71474_y.field_74314_A.func_151470_d()) {
            final EntityPlayerSP field_71439_g = OringoClient.mc.field_71439_g;
            field_71439_g.field_70181_x += speed * 3.0;
        }
        if (OringoClient.mc.field_71474_y.field_74311_E.func_151470_d()) {
            final EntityPlayerSP field_71439_g2 = OringoClient.mc.field_71439_g;
            field_71439_g2.field_70181_x -= speed * 3.0;
        }
    }
    
    private boolean hasBoots() {
        return OringoClient.mc.field_71439_g.func_82169_q(0) != null && (OringoClient.mc.field_71439_g.func_82169_q(0).func_82833_r().contains("Tarantula Boots") || OringoClient.mc.field_71439_g.func_82169_q(0).func_82833_r().contains("Spider's Boots"));
    }
}
