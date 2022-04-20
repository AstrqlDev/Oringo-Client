// 
// Decompiled by Procyon v0.5.36
// 

package me.oringo.oringoclient.qolfeatures.module.player;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.BlockAir;
import net.minecraft.util.BlockPos;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.events.MotionUpdateEvent;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.NumberSetting;
import me.oringo.oringoclient.qolfeatures.module.Module;

public class AntiVoid extends Module
{
    public NumberSetting fallDistance;
    private boolean tp;
    private boolean canTp;
    private double x;
    private double y;
    private double z;
    
    public AntiVoid() {
        super("Anti Void", 0, Category.PLAYER);
        this.fallDistance = new NumberSetting("Fall distance", 1.0, 0.5, 5.0, 0.1);
        this.canTp = true;
        this.addSettings(this.fallDistance);
    }
    
    @SubscribeEvent
    public void onMovePre(final MotionUpdateEvent.Pre event) {
        if (!this.isToggled() || !this.canTp || OringoClient.mc.field_71439_g.field_70143_R < this.fallDistance.getValue() || OringoClient.mc.field_71439_g.field_71075_bZ.field_75101_c) {
            return;
        }
        BlockPos block = new BlockPos(OringoClient.mc.field_71439_g.field_70165_t, OringoClient.mc.field_71439_g.field_70163_u, OringoClient.mc.field_71439_g.field_70161_v);
        for (int i = (int)OringoClient.mc.field_71439_g.field_70163_u; i > 0; --i) {
            if (!(OringoClient.mc.field_71441_e.func_180495_p(block).func_177230_c() instanceof BlockAir)) {
                return;
            }
            block = block.func_177982_a(0, -1, 0);
        }
        this.tp = true;
        this.canTp = false;
        this.x = OringoClient.mc.field_71439_g.field_70165_t;
        this.y = OringoClient.mc.field_71439_g.field_70163_u;
        this.z = OringoClient.mc.field_71439_g.field_70161_v;
        OringoClient.mc.field_71439_g.func_70107_b(this.x + 1000.0, this.y, this.z + 1000.0);
    }
    
    @SubscribeEvent
    public void onMovePost(final MotionUpdateEvent.Post event) {
        if (this.tp) {
            OringoClient.mc.field_71439_g.func_70107_b(this.x, this.y, this.z);
            OringoClient.mc.field_71439_g.func_70016_h(0.0, 0.0, 0.0);
            this.tp = false;
            new Thread(() -> {
                try {
                    Thread.sleep(750L);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.canTp = true;
            }).start();
        }
    }
}
