// 
// Decompiled by Procyon v0.5.36
// 

package me.oringo.oringoclient.qolfeatures.module.skyblock;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import me.oringo.oringoclient.utils.SkyblockUtils;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemSnowball;
import me.oringo.oringoclient.OringoClient;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.Module;

public class Snowballs extends Module
{
    private boolean wasPressed;
    public BooleanSetting pickupstash;
    
    public Snowballs() {
        super("Snowballs", Category.SKYBLOCK);
        this.pickupstash = new BooleanSetting("Pick up stash", true);
        this.addSettings(this.pickupstash);
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (OringoClient.mc.field_71462_r != null) {
            return;
        }
        if (this.isPressed() && !this.wasPressed) {
            final int holding = OringoClient.mc.field_71439_g.field_71071_by.field_70461_c;
            for (int x = 0; x < 9; ++x) {
                if (OringoClient.mc.field_71439_g.field_71071_by.func_70301_a(x) != null && (OringoClient.mc.field_71439_g.field_71071_by.func_70301_a(x).func_77973_b() instanceof ItemSnowball || OringoClient.mc.field_71439_g.field_71071_by.func_70301_a(x).func_77973_b() instanceof ItemEgg || OringoClient.mc.field_71439_g.field_71071_by.func_70301_a(x).func_77973_b() instanceof ItemEnderPearl || OringoClient.mc.field_71439_g.field_71071_by.func_70301_a(x).func_82833_r().contains("Bonemerang"))) {
                    OringoClient.mc.field_71439_g.field_71071_by.field_70461_c = x;
                    SkyblockUtils.updateItem();
                    for (int e = 0; e < 16; ++e) {
                        OringoClient.mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C08PacketPlayerBlockPlacement(OringoClient.mc.field_71439_g.func_70694_bm()));
                    }
                }
            }
            OringoClient.mc.field_71439_g.field_71071_by.field_70461_c = holding;
            SkyblockUtils.updateItem();
            if (this.pickupstash.isEnabled()) {
                OringoClient.mc.field_71439_g.func_71165_d("/pickupstash");
            }
        }
        this.wasPressed = this.isPressed();
    }
    
    @Override
    public boolean isKeybind() {
        return true;
    }
}
