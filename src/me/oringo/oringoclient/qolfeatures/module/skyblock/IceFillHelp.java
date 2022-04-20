// 
// Decompiled by Procyon v0.5.36
// 

package me.oringo.oringoclient.qolfeatures.module.skyblock;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.events.MoveEvent;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.qolfeatures.module.Module;

public class IceFillHelp extends Module
{
    public NumberSetting slowdown;
    public BooleanSetting noIceSlip;
    public BooleanSetting autoStop;
    
    public IceFillHelp() {
        super("Ice Fill Helper", Category.SKYBLOCK);
        this.slowdown = new NumberSetting("Ice slowdown", 0.15, 0.05, 1.0, 0.05);
        this.noIceSlip = new BooleanSetting("No ice slip", true);
        this.autoStop = new BooleanSetting("Auto stop", true);
        this.addSettings(this.autoStop, this.slowdown, this.noIceSlip);
    }
    
    @SubscribeEvent
    public void onMove(final MoveEvent event) {
        if (!this.isToggled() || !OringoClient.mc.field_71439_g.field_70122_E) {
            return;
        }
        final BlockPos currentPos = new BlockPos(OringoClient.mc.field_71439_g.field_70165_t, OringoClient.mc.field_71439_g.field_70163_u - 0.4, OringoClient.mc.field_71439_g.field_70161_v);
        if (OringoClient.mc.field_71441_e.func_180495_p(currentPos).func_177230_c() == Blocks.field_150432_aD) {
            event.z *= this.slowdown.getValue();
            event.x *= this.slowdown.getValue();
            final BlockPos nextPos = new BlockPos(OringoClient.mc.field_71439_g.field_70165_t + event.x, OringoClient.mc.field_71439_g.field_70163_u - 0.4, OringoClient.mc.field_71439_g.field_70161_v + event.z);
            if (this.autoStop.isEnabled() && !currentPos.equals((Object)nextPos) && OringoClient.mc.field_71441_e.func_180495_p(nextPos).func_177230_c() == Blocks.field_150432_aD) {
                event.x = 0.0;
                event.z = 0.0;
            }
        }
        if (this.noIceSlip.isEnabled()) {
            Blocks.field_150403_cj.field_149765_K = 0.6f;
            Blocks.field_150432_aD.field_149765_K = 0.6f;
        }
    }
    
    @Override
    public void onDisable() {
        Blocks.field_150403_cj.field_149765_K = 0.98f;
        Blocks.field_150432_aD.field_149765_K = 0.98f;
    }
}
