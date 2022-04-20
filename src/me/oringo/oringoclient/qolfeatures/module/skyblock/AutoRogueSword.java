// 
// Decompiled by Procyon v0.5.36
// 

package me.oringo.oringoclient.qolfeatures.module.skyblock;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import me.oringo.oringoclient.utils.SkyblockUtils;
import me.oringo.oringoclient.OringoClient;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.utils.MilliTimer;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.qolfeatures.module.Module;

public class AutoRogueSword extends Module
{
    public NumberSetting clicks;
    private MilliTimer time;
    
    public AutoRogueSword() {
        super("Auto Rogue", 0, Category.SKYBLOCK);
        this.clicks = new NumberSetting("Clicks", 50.0, 1.0, 200.0, 1.0);
        this.time = new MilliTimer();
        this.addSettings(this.clicks);
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (OringoClient.mc.field_71439_g == null || !SkyblockUtils.inDungeon || !this.isToggled()) {
            return;
        }
        if (this.time.hasTimePassed(30000L)) {
            for (int i = 0; i < 9; ++i) {
                if (OringoClient.mc.field_71439_g.field_71071_by.func_70301_a(i) != null && OringoClient.mc.field_71439_g.field_71071_by.func_70301_a(i).func_82833_r().toLowerCase().contains("rogue sword")) {
                    final int held = OringoClient.mc.field_71439_g.field_71071_by.field_70461_c;
                    OringoClient.mc.field_71439_g.field_71071_by.field_70461_c = i;
                    SkyblockUtils.updateItemNoEvent();
                    for (int x = 0; x < this.clicks.getValue(); ++x) {
                        OringoClient.mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C08PacketPlayerBlockPlacement(OringoClient.mc.field_71439_g.func_70694_bm()));
                    }
                    OringoClient.mc.field_71439_g.field_71071_by.field_70461_c = held;
                    SkyblockUtils.updateItemNoEvent();
                    this.time.updateTime();
                    break;
                }
            }
        }
    }
}
