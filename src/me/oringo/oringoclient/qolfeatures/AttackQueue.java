// 
// Decompiled by Procyon v0.5.36
// 

package me.oringo.oringoclient.qolfeatures;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.util.BlockPos;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.settings.GameSettings;
import me.oringo.oringoclient.gui.ClickGUI;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AttackQueue
{
    public static boolean attack;
    private static int ticks;
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (AttackQueue.ticks != 0) {
            --AttackQueue.ticks;
        }
        final Minecraft mc = Minecraft.func_71410_x();
        if (mc.field_71439_g != null && AttackQueue.attack && (AttackQueue.ticks == 0 || (mc.field_71476_x != null && mc.field_71476_x.field_72313_a.equals((Object)MovingObjectPosition.MovingObjectType.ENTITY)))) {
            mc.field_71439_g.func_71038_i();
            Label_0201: {
                if (mc.field_71476_x != null) {
                    switch (mc.field_71476_x.field_72313_a) {
                        case ENTITY: {
                            mc.field_71442_b.func_78764_a((EntityPlayer)mc.field_71439_g, mc.field_71476_x.field_72308_g);
                            break Label_0201;
                        }
                        case BLOCK: {
                            final BlockPos blockpos = mc.field_71476_x.func_178782_a();
                            if (mc.field_71441_e.func_180495_p(blockpos).func_177230_c().func_149688_o() != Material.field_151579_a) {
                                mc.field_71442_b.func_180511_b(blockpos, mc.field_71476_x.field_178784_b);
                                break Label_0201;
                            }
                            break;
                        }
                    }
                    if (mc.field_71442_b.func_78762_g()) {
                        AttackQueue.ticks = 10;
                    }
                }
            }
            AttackQueue.attack = false;
        }
        if (mc.field_71462_r instanceof ClickGUI) {
            KeyBinding.func_74510_a(mc.field_71474_y.field_74351_w.func_151463_i(), GameSettings.func_100015_a(mc.field_71474_y.field_74351_w));
            KeyBinding.func_74510_a(mc.field_71474_y.field_74368_y.func_151463_i(), GameSettings.func_100015_a(mc.field_71474_y.field_74368_y));
            KeyBinding.func_74510_a(mc.field_71474_y.field_74370_x.func_151463_i(), GameSettings.func_100015_a(mc.field_71474_y.field_74370_x));
            KeyBinding.func_74510_a(mc.field_71474_y.field_74366_z.func_151463_i(), GameSettings.func_100015_a(mc.field_71474_y.field_74366_z));
            KeyBinding.func_74510_a(mc.field_71474_y.field_74314_A.func_151463_i(), GameSettings.func_100015_a(mc.field_71474_y.field_74314_A));
            KeyBinding.func_74510_a(mc.field_71474_y.field_151444_V.func_151463_i(), GameSettings.func_100015_a(mc.field_71474_y.field_151444_V));
            KeyBinding.func_74510_a(mc.field_71474_y.field_74311_E.func_151463_i(), GameSettings.func_100015_a(mc.field_71474_y.field_74311_E));
        }
    }
    
    static {
        AttackQueue.attack = false;
        AttackQueue.ticks = 0;
    }
}
