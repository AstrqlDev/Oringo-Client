// 
// Decompiled by Procyon v0.5.36
// 

package me.oringo.oringoclient.qolfeatures.module.skyblock;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import me.oringo.oringoclient.utils.SkyblockUtils;
import me.oringo.oringoclient.OringoClient;
import me.oringo.oringoclient.events.PlayerUpdateEvent;
import me.oringo.oringoclient.qolfeatures.module.settings.Setting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.BooleanSetting;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.ModeSetting;
import me.oringo.oringoclient.qolfeatures.module.Module;

public class BoneThrower extends Module
{
    public ModeSetting mode;
    public BooleanSetting autoDisable;
    private int ticks;
    
    public BoneThrower() {
        super("BoneThrower", Category.SKYBLOCK);
        this.mode = new ModeSetting("Mode", "Hotbar", new String[] { "Hotbar" });
        this.autoDisable = new BooleanSetting("Disable", true);
        this.addSettings(this.mode, this.autoDisable);
    }
    
    @Override
    public void onEnable() {
        this.ticks = 6;
    }
    
    @SubscribeEvent
    public void onUpdate(final PlayerUpdateEvent event) {
        --this.ticks;
        if (this.isToggled()) {
            final String selected = this.mode.getSelected();
            switch (selected) {
                case "Hotbar": {
                    final int last = OringoClient.mc.field_71439_g.field_71071_by.field_70461_c;
                    for (int i = 0; i < 9; ++i) {
                        final ItemStack stack = OringoClient.mc.field_71439_g.field_71071_by.func_70301_a(i);
                        if (stack != null && stack.func_82833_r().contains("Bonemerang")) {
                            OringoClient.mc.field_71439_g.field_71071_by.field_70461_c = i;
                            SkyblockUtils.updateItem();
                            OringoClient.mc.func_147114_u().func_147298_b().func_179290_a((Packet)new C08PacketPlayerBlockPlacement(OringoClient.mc.field_71439_g.func_70694_bm()));
                        }
                    }
                    OringoClient.mc.field_71439_g.field_71071_by.field_70461_c = last;
                    SkyblockUtils.updateItem();
                    if (this.autoDisable.isEnabled()) {
                        this.toggle();
                        break;
                    }
                    break;
                }
            }
        }
    }
}
